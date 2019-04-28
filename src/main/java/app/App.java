package app;

import models.*;
import org.antlr.v4.runtime.*;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        String fileName = "test.spl";

        try {
            //get string file
            CharStream is = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);

            System.out.println("----" + fileName + "----");

            List<? extends Token> tks = lexer.getAllTokens();

            ArrayList<String> lexicalErrors = new ArrayList<String>();

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
                for (String error: lexicalErrors) {
                    System.out.println(error);
                }
            } else {
                //create parser
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

                //build custom visitor
                VisitorImpl visitor = new VisitorImpl();
                StmtBlock block = (StmtBlock) visitor.visitBlock(parser.block());

                if (parser.getNumberOfSyntaxErrors() == 0) {

                    Environment env = new Environment();
                    ArrayList<SemanticError> err = block.checkSemantics(env);
                    if (err.size() > 0) {
                        System.out.println("You had: " + err.size() + " errors:");
                        for (SemanticError e : err)
                            System.out.println("\t" + e);
                    } else {
                        ElementBase type = block.typeCheck(); //type-checking bottom-up

//                        if(testPrintTypeCheck) {
//                            System.out.println(type.toPrint("Type checking ok! Type of the program is: "));
//                        }


                    }
                }
            }
        } catch (Exception ex) {
            String error = "\u001B[31m"+ ex.toString();
            System.out.println(error);
            StackTraceElement [] stackTrace = ex.getStackTrace();
            for(StackTraceElement st: stackTrace) {
                System.out.println(st);
            }
            System.out.println("\u001B[0m");
        }
    }
}