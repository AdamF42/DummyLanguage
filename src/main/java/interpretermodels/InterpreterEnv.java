package interpretermodels;

import parser.ExecuteVM;

import java.util.HashMap;


public class InterpreterEnv {
    public int[] code = new int[ExecuteVM.CODESIZE];
    public int i = 0;
    private HashMap<String,Integer> labelAdd = new HashMap<>();
    private HashMap<Integer,String> labelRef = new HashMap<>();

    public HashMap<String, Integer> getLabelAdd() {
        return labelAdd;
    }

    public HashMap<Integer, String> getLabelRef() {
        return labelRef;
    }
}
