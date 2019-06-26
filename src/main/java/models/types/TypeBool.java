package models.types;

import models.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeBool extends TypeReferenceable {


    public TypeBool() {
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
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