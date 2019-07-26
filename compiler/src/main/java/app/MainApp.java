package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import exeptions.TypeCheckException;
import models.Environment;
import models.statements.StmtBlock;
import models.VisitorImpl;
import org.antlr.v4.runtime.*;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import util.SemanticError;
import util.Strings;
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

        } catch (IOException | TypeCheckException e) {
            e.printStackTrace();
        }
    }
}
