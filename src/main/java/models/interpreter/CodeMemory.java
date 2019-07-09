package models.interpreter;

import java.util.HashMap;


public class CodeMemory {
    public final int[] code = new int[ExecuteVM.CODE_SIZE];
    public int i = 0;
    private final HashMap<String,Integer> labelAdd = new HashMap<>();
    private final HashMap<Integer,String> labelRef = new HashMap<>();

    public HashMap<String, Integer> getLabelAdd() {
        return labelAdd;
    }

    public HashMap<Integer, String> getLabelRef() {
        return labelRef;
    }
}
