package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Strings {

	private static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

	public static final String ERROR_VARIABLE_DOESNT_EXIST = "No variable found. Variable either doesn't exist or has been deleted. Variable name: ";
	public static final String ERROR_VARIABLE_HAS_BEEN_DELETED = "Variable has been deleted. Variable name: ";
	public static final String ERROR_FUNCTION_DOESNT_EXIST = "No function found. Function either doesn't exist or has been deleted. Function name: ";
	public static final String ERROR_ALREADY_DECLARED_IDENTIFIER = "Identifier already declared. Identifier name: ";
	public static final String ERROR_PARAMETER_CALLDED_AS_FUNCTION = "Parameter can't have same name as its function. Parameter name: ";
	public static final String ERROR_NOT_CALLABLE = "Identifier not callable. Parameter name: ";
	public static final String ERROR_PARAMETER_MISMATCH = "Paramenters count doesn't match. Expected ";
	public static final String ERROR_DANGEROUS_USE_OF_PARAMETER = "Potentially deleted parameter inside function. Name: ";
	public static final String ERROR_BEHAVIUOR_MISMATCH = "Mismatching behavioural types between If-Then-Else statement branches";


	public static final String LEXICAL_CHECK = "Check Lexical Errors";
	public static final String SEMANTIC_CHECK = "Check Semantic Errors";
	public static final String TYPE_CHECK = "Check Type Errors";

	public static void printCheckingStatus(String status) {
		System.out.println("#");
		System.out.println("## " + status);
	}
}
