package util;

import org.apache.commons.lang3.RandomStringUtils;
import java.io.*;
import static java.lang.System.exit;


public class Strings {

	public static final String ERROR_IDENTIFIER_DOESNT_EXIST = "Identifier doesn't exist. Identifier name: ";
	public static final String ERROR_VARIABLE_DOESNT_EXIST = "Variable doesn't exist. Variable name: ";
	public static final String ERROR_VARIABLE_NOT_INITIALIZED = "Variable might not be initialized: ";
	public static final String ERROR_VARIABLE_HAS_BEEN_DELETED = "Variable has been deleted. Variable name: ";
	public static final String ERROR_FUNCTION_HAS_BEEN_DELETED = "Function has been deleted. Function name: ";
	public static final String ERROR_ALREADY_DECLARED_IDENTIFIER = "Identifier already declared. Identifier name: ";
	public static final String ERROR_PARAMETER_MISMATCH = "Parameters count doesn't match. Expected ";
	public static final String ERROR_DANGEROUS_USE_OF_PARAMETER = "Potentially deleted parameter inside function. Name: ";
	public static final String ERROR_GLOBAL_VAR_AS_PARAMETER = "Global variable used as reference parameter. Name: ";
	public static final String ERROR_BEHAVIOR_MISMATCH = "Mismatching behavioural types between If-Then-Else branches";
	public static final String ERROR_OUT_OF_MEMORY = "Error: Out of memory";

	public static final String LEXICAL_CHECK = "Check Lexical Errors";
	public static final String SYNTAX_CHECK = "Check Syntax Errors";
	public static final String SEMANTIC_CHECK = "Check Semantic Errors";
	public static final String TYPE_CHECK = "Check Type Errors";

	public static final String ACC = "$a0";
	public static final String TMP = "$t1";
	public static final String SP = "$sp";
	public static final String FP = "$fp";
	public static final String AL = "$al";
	public static final String RA = "$ra";
	public static final String IP = "$ip";

	public static final String EMPTY = "";
	public static void printCheckingStatus(String status) {
		System.out.println("#");
		System.out.println("## " + status);
	}

	public static String loadW(String register, String offset, String memPtr){
		return "lw "+register+" "+offset+"("+memPtr+")\n";
	}

	public static String b(String label){
		return "b "+label+"\n";
	}

	public static String storeW(String register, String offset, String memPtr){
		return "sw "+register+" "+offset+"("+memPtr+")\n";
	}

	public static String loadI(String register, String i){
		return "li "+register+" "+i+"\n";
	}

	public static String push(String register){
		return "push "+register+"\n";
	}

	public static String move(String dest, String src) {
		return	"move " + dest + " " + src +"\n";
	}

	public static String add(String storeRegister, String op1, String op2){
		return "add "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String addi(String storeRegister, String register, String integer){
		return "addi "+storeRegister+" "+register+" "+integer+"\n";
	}

	public static String mult(String storeRegister, String op1, String op2){
		return "mult "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String sub(String storeRegister, String op1, String op2){
		return "sub "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String subi(String storeRegister, String op1, String op2){
		return "subi "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String jr(String register){
		return "jr "+register+"\n";
	}

	public static String jal(String label){
		return "jal "+label+"\n";
	}

	public static String beq(String r1, String r2, String label){
		return "beq "+r1+" "+r2+" "+label+"\n";
	}

	public static String bgr(String r1, String r2, String label){
		return "bgr "+r1+" "+r2+" "+label+"\n";
	}

	public static String bgre(String r1, String r2, String label){
		return "bgre "+r1+" "+r2+" "+label+"\n";
	}

	public static String blr(String r1, String r2, String label){
		return "blr "+r1+" "+r2+" "+label+"\n";
	}

	public static String blre(String r1, String r2, String label){
		return "blre "+r1+" "+r2+" "+label+"\n";
	}

	public static String div(String storeRegister, String op1, String op2){
		return "div "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String assignTop(String register){
		return register+" <- top\n";
	}

	public static String pop(){
		return "pop\n";
	}

	public static String print() {
		return "print\n";
	}

	public static String getFreshLabel(){
		return RandomStringUtils.randomAlphabetic(10);
	}

	public static String getVariableForCgen(int nl, int varNl){
		StringBuilder result = new StringBuilder();
		for (int i = 1; i < nl-varNl; i++){
			result.append(loadW(AL, "0", AL));
		}
		return result.toString();
	}

	public static void saveCgenToFile(String fileName, String fileContent)
	{
		File tmp = new File(fileName);
		String objFile = tmp.getAbsolutePath().split("\\.")[0]+".o";
		tmp = new File(objFile);

		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(tmp.getAbsoluteFile(), false));
			out.write(fileContent);
			out.close();
		}
		catch (IOException e) {
			System.err.println("Exception while saving file" + e);
			exit(-1);
		}
	}

}

