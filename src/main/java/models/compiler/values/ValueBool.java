package models.compiler.values;

import models.compiler.Environment;
import util.SemanticError;
import models.compiler.types.Type;
import models.compiler.types.Bool;

import java.util.*;

import static util.Strings.ACC;
import static util.Strings.loadI;

public class ValueBool extends Value {


    public ValueBool(String val) {
        super(val);
    }

    private static final Map<String, String> valueToInteger;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("true", "1");
        aMap.put("false", "0");
        valueToInteger = Collections.unmodifiableMap(aMap);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Type typeCheck() {
        return new Bool();
    }

    @Override
    public String codeGeneration() {
        return loadI(ACC,valueToInteger.get(getVal()));
    }

}