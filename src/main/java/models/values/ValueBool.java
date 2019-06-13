package models.values;

import models.Environment;
import util.SemanticError;
import models.types.Type;
import models.types.TypeBool;

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

    /**
     * Checks ValueBool's semantic.
     *
     * @param env -> Environment that holds previously parsed information
     * @return Empty ArrayList of semantic errors
     */
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    /**
     * Type check is empty because it's a terminal node.
     *
     * @return instance of TypeBool()
     */
    @Override
    public Type typeCheck() {
        return new TypeBool();
    }

    @Override
    public String codeGeneration() {

        return loadI(ACC,valueToInteger.get(getVal()));
    }

}