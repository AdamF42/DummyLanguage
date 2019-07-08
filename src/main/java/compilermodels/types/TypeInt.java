package compilermodels.types;

import compilermodels.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeInt extends TypeReferenceable {

    public TypeInt() {
    }


    @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
	}

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

}