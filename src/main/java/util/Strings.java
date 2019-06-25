package util;

import models.stentry.STentry;

import java.util.UUID;


public class Strings {

	public static final String ERROR_VARIABLE_DOESNT_EXIST = "No variable found. Variable either doesn't exist or has been deleted. Variable name: ";
	public static final String ERROR_VARIABLE_HAS_BEEN_DELETED = "Variable has been deleted. Variable name: ";
	public static final String ERROR_FUNCTION_DOESNT_EXIST = "No function found. Function either doesn't exist or has been deleted. Function name: ";
	public static final String ERROR_ALREADY_DECLARED_IDENTIFIER = "Identifier already declared. Identifier name: ";
	public static final String ERROR_PARAMETER_MISMATCH = "Parameters count doesn't match. Expected ";
	public static final String ERROR_DANGEROUS_USE_OF_PARAMETER = "Potentially deleted parameter inside function. Name: ";
	public static final String ERROR_BEHAVIOR_MISMATCH = "Mismatching behavioural types between If-Then-Else statement branches";


	public static final String LEXICAL_CHECK = "Check Lexical Errors";
	public static final String SEMANTIC_CHECK = "Check Semantic Errors";
	public static final String TYPE_CHECK = "Check Type Errors";


	public static final String ACC = "$a0";
	public static final String TMP = "$t1";
	public static final String SP = "$sp";
	public static final String FP = "$fp";
	public static final String AL = "$al";
	public static final String RA = "$ra";


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
		return "add "+storeRegister+" "+register+" "+integer+"\n";
	}

	public static String mult(String storeRegister, String op1, String op2){
		return "mult "+storeRegister+" "+op1+" "+op2+"\n";
	}

	public static String sub(String storeRegister, String op1, String op2){
		return "sub "+storeRegister+" "+op1+" "+op2+"\n";
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

	public static String GetFreshLabel(){
		return UUID.randomUUID().toString();
	}

	public static String getVariableForCgen(int nl, STentry idEntry){
		StringBuilder result = new StringBuilder();
		for (int i = 0; i<nl-idEntry.getNestinglevel();i++){
			result.append(loadW(AL, "0", AL));
		}
		return result.toString();
	}
}

