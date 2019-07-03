package models.compiler.values;

import models.compiler.Environment;
import util.SemanticError;
import models.compiler.types.Type;
import models.compiler.types.TypeInt;

import java.util.ArrayList;

import static util.Strings.ACC;
import static util.Strings.loadI;

public class ValueInt extends Value {


    public ValueInt(String val) {
        super(val);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Type typeCheck() {
        return new TypeInt();
    }

    @Override
    public String codeGeneration() {
        return loadI(ACC,getVal());
    }

}