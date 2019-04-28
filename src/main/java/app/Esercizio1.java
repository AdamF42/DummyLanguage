package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.*;


import org.antlr.v4.runtime.*;

import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;


public class Esercizio1 {

    public static void main(String[] args) {

        String fileName = "test.spl";

        try{
            //get string file
            CharStream is = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);

            System.out.println("###### " + fileName + " ######");

//            List<? extends Token> tks = lexer.getAllTokens();
//
//            ArrayList<String> lexicalErrors = new ArrayList<String>();
//
//            for (Token token: tks) {
//                if(token.getType()==ComplexStaticAnalysisLexer.ERR)
//                {
//                    String error = String.format("%s:%d:%d: Error: unrecognized symbol type '%s'",
//                            token.getTokenSource().getSourceName(),
//                            token.getLine(),
//                            token.getCharPositionInLine(),
//                            token.getText());
//                    lexicalErrors.add(error);
//                }
//            }
//            if (!lexicalErrors.isEmpty()) {
//                System.out.println("Lexical Error found. Exiting process\n");
//                for (String error : lexicalErrors) {
//                    System.out.println(error);
//                }
//            }

            //create parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

            //build custom visitor
            VisitorImpl visitor = new VisitorImpl();

            //visit the root, this will recursively visit the whole tree
            StmtBlock mainBlock = (StmtBlock) visitor.visitBlock(parser.block());

            if (parser.getNumberOfSyntaxErrors() == 0) {

                Environment env = new Environment();
                List<SemanticError> err = mainBlock.checkSemantics(env);
                if (err.size() > 0) {
                    System.out.println("You had: " + err.size() + " errors:");
                    for (SemanticError e : err)
                        System.out.println("\t" + e);
                } else {
                    ElementBase type = mainBlock.typeCheck(); //type-checking bottom-up

//                        if(testPrintTypeCheck) {
//                            System.out.println(type.toPrint("Type checking ok! Type of the program is: "));
//                        }


                }
            }

//            List<SemanticError> errors = mainBlock.checkSemantics(new Environment());
//
//            //this means the semantic checker found some errors
//            if(errors.size() > 0){
//                System.out.println("Check semantics FAILED");
//                for(SemanticError err: errors)
//                    System.out.println(err);
//            }else{
//                System.out.println("Check semantics succeded");
//                System.out.println("Calculating behavioral type");
//            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TypeCheckError typeCheckError) {
            typeCheckError.printStackTrace();
        }

    }

}
