package models.types;

import models.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeBool extends TypeReferenceable {


    /**
     * Constructor for TypeBool.
     */
    public TypeBool() {
    }

    /**
     * Checks TypeBool's semantic
     *
     * @param env -> Environment that holds previously parsed information
     * @return Empty ArrayList of semantic errors
     */
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    /**
     * Type check is empty because it's a terminal node.
     *
     * @return null
     */
    public Type typeCheck() {
        return null;
    }

    /**
     *
     * @return bool type
     */
    public String getType(){
        return "bool";
    }
}  