package models.types;

import models.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class Bool extends TypeReferenceable {


    public Bool() {
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
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

    public String getType(){
        return "bool";
    }
}  