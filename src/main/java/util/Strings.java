package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Strings {

	private static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

	public static final String ERROR_VARIABLE_DOESNT_EXIST = "No variable found. Variable either doesn't exist or has been deleted. Variable name: ";

	public static final String ERROR_FUNCTION_DOESNT_EXIST = "Function doesn't exist or is not been already declared. Function name: ";

	public static final String ERROR_ALREADY_DECLARED_IDENTIFIER = "Identifier already declared. Identifier name: ";
	public static final String ERROR_PARAMETER_CALLDED_AS_FUNCTION = "Parameter called as its function. Parameter name: ";
	public static final String ERROR_NOT_CALLABLE = "Identifier not callable. Parameter name: ";
	public static final String ERROR_PARAMETER_MISMATCH = "Paramenters count doesn't match. Expected ";

	public static void saveStrToFile(String fileName, String fileContent)
	{
		Date date = new Date(System.currentTimeMillis());
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(fileName, true));
			out.write(formatter.format(date)+"\n"+fileContent+"\n");
			out.close();
		}
		catch (IOException e) {
			System.err.println("Exception occoured during writing" + e);
		}
	}
}
