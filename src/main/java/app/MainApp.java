package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.compiler.*;
import models.compiler.statements.StmtBlock;
import models.compiler.VisitorImpl;
import models.interpreter.CodeMemory;
import models.interpreter.ExecuteVM;
import models.interpreter.instructions.Assembly;
import models.interpreter.instructions.CVMVisitorImpl;
import org.antlr.v4.runtime.*;
import parser.CVMLexer;
import parser.CVMParser;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import util.ExecutionException;
import util.SemanticError;
import util.Strings;
import util.TypeCheckException;

import static java.lang.System.err;
import static java.lang.System.exit;
import static util.Strings.saveCgenToFile;


public class MainApp {

    public static void main(String[] args) {

        String fileName = Strings.EMPTY;

        if(args.length==0){
            System.out.println("Input file expected!");
            exit(-1);
        }

        if(args.length==1){
            fileName = args[0];
            System.out.println("# Compiling and executing file "+ fileName);
        }

        try{

            CharStream is = CharStreams.fromFileName(fileName);


            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);

            Strings.printCheckingStatus(Strings.LEXICAL_CHECK);

            List<String> lexicalErrors = new ArrayList<>();
            for (Token token:  lexer.getAllTokens()) {
                if (token.getType() == ComplexStaticAnalysisLexer.ERR) {
                    String error = String.format("%s:%d:%d: Error: unrecognized symbol type '%s'\n",
                            token.getTokenSource().getSourceName(),
                            token.getLine(),
                            token.getCharPositionInLine(),
                            token.getText());
                    System.err.print(error);
                    lexicalErrors.add(error);
                }
            }
            if(!lexicalErrors.isEmpty()){
                System.err.println("Lexical errors found. Exiting process.");
                exit(-1);
            }


            lexer.reset();
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);


            VisitorImpl visitor = new VisitorImpl();

            Strings.printCheckingStatus(Strings.SYNTAX_CHECK);

            StmtBlock mainBlock = visitor.visitBlock(parser.block());

            if(parser.getNumberOfSyntaxErrors()!=0) {
                System.err.println("Syntax errors found. Exiting process.");
                exit(-1);
            }

            Environment env = new Environment();
            Strings.printCheckingStatus(Strings.SEMANTIC_CHECK);
            List<SemanticError> err = mainBlock.checkSemantics(env);
            if (!err.isEmpty()) {
                System.err.println("You had: " + err.size() + " errors:");
                for (SemanticError e : err)
                    System.err.println("\t" + e);
                exit(-1);
            } else {
                Strings.printCheckingStatus(Strings.TYPE_CHECK);
                mainBlock.typeCheck();
            }

            Strings.printCheckingStatus("Saving object file...");
            saveCgenToFile(fileName, mainBlock.codeGeneration());

            File tmp = new File(fileName);
            String objFile = tmp.getAbsolutePath().split("\\.")[0]+".o";
            tmp = new File(objFile);

            if(!tmp.exists())
                throw new IOException("File " + tmp.getName()+" doesn't exist");


            CharStream interpreterIs = CharStreams.fromFileName(tmp.getName());
            CVMLexer interpreterLexer = new CVMLexer(interpreterIs);
            CommonTokenStream interpreterTokens = new CommonTokenStream(interpreterLexer );
            CVMParser interpreterParser = new CVMParser(interpreterTokens);
            CVMVisitorImpl interpreterVisitor = new CVMVisitorImpl();
            CodeMemory e = new CodeMemory();
            Assembly assembly = interpreterVisitor.visitAssembly(interpreterParser.assembly());
            assembly.loadCode(e);
            ExecuteVM vm = new ExecuteVM(e.code);
            Strings.printCheckingStatus("Executing object file...");
            vm.cpu();


        } catch (IOException | TypeCheckException | ExecutionException e) {
            e.printStackTrace();
        }

    }



}
