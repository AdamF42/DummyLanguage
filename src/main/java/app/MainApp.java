package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.compiler.*;


import models.compiler.statements.StmtBlock;
import models.compiler.VisitorImpl;
import org.antlr.v4.runtime.*;

import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;


public class MainApp {

    public static void main(String[] args) {

        String fileName = "test.spl";

        try{
            //get string file
            CharStream is = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);

            System.out.println("###### " + fileName + " ######");
            Strings.printCheckingStatus(Strings.LEXICAL_CHECK);

            List<? extends Token> tks = lexer.getAllTokens();

            ArrayList<String> lexicalErrors = new ArrayList<>();

            for (Token token: tks) {
                if(token.getType()==ComplexStaticAnalysisLexer.ERR)
                {
                    String error = String.format("%s:%d:%d: Error: unrecognized symbol type '%s'",
                            token.getTokenSource().getSourceName(),
                            token.getLine(),
                            token.getCharPositionInLine(),
                            token.getText());
                    lexicalErrors.add(error);
                }
            }
            if (!lexicalErrors.isEmpty()) {
                System.out.println("Lexical Error found. Exiting process\n");
                for (String error : lexicalErrors) {
                    System.out.println(error);
                }
            }

            // for some reason it is necessary to close and reopen the old is
            is = CharStreams.fromFileName(fileName);
            lexer = new ComplexStaticAnalysisLexer(is);

            //create parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

            //build custom visitor
            VisitorImpl visitor = new VisitorImpl();

            //visit the root, this will recursively visit the whole tree
            StmtBlock mainBlock = visitor.visitBlock(parser.block());

            if (parser.getNumberOfSyntaxErrors() == 0) {

                Environment env = new Environment();
                Strings.printCheckingStatus(Strings.SEMANTIC_CHECK);
                List<SemanticError> err = mainBlock.checkSemantics(env);
                if (err.size() > 0) {
                    System.out.println("You had: " + err.size() + " errors:");
                    for (SemanticError e : err)
                        System.out.println("\t" + e);
                } else {
                    Strings.printCheckingStatus(Strings.TYPE_CHECK);
                    mainBlock.typeCheck();
                }
            }

            String test = mainBlock.codeGeneration();
            System.out.println(test);

        } catch (IOException | TypeCheckError e) {
            e.printStackTrace();
        }

    }



}
