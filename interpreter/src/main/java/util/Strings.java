package util;


import org.apache.commons.lang3.RandomStringUtils;

public class Strings {

	public static final String ERROR_OUT_OF_MEMORY = "Error: Out of memory";

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

	public static String getFreshLabel(){
		return RandomStringUtils.randomAlphabetic(10);
	}

}

