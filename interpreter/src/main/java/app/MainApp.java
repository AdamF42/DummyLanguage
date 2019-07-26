package app;


import models.CodeMemory;
import models.ExecuteVM;
import models.instructions.Assembly;
import models.instructions.CVMVisitorImpl;
import interpreter.parser.CVMLexer;
import interpreter.parser.CVMParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import exeptions.ExecutionException;
import util.Strings;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;


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


        } catch (IOException | ExecutionException e) {
            e.printStackTrace();
        }

    }



}
