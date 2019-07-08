package compilermodels.values;

import compilermodels.Environment;
import util.SemanticError;
import compilermodels.types.Type;
import compilermodels.types.TypeInt;

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