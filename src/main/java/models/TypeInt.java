package models;

import java.util.ArrayList;

public class TypeInt extends TypeReferenceable {
    /**
     * Constructor for TypeInt.
     */
    public TypeInt() {
    }

    /**
     * Checks TypeInt's semantic.
     *
     * @param env -> Environment that holds previously parsed information
     * @return Empty ArrayList of semantic errors
     */
    @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
	}


    /**
     * Type check is empty because it's a terminal node.
     *
     * @return null
     */
    public Type typeCheck() {
        return null;
    }

}