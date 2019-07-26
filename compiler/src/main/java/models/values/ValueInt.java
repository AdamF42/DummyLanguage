package models.values;

import models.Environment;
import models.types.Type;
import models.types.Int;
import util.SemanticError;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.ACC;
import static util.Strings.loadI;

public class ValueInt extends Value {


    public ValueInt(String val) {
        super(val);
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Type typeCheck() {
        return new Int();
    }

    @Override
    public String codeGeneration() {
        return loadI(ACC,getVal());
    }

}