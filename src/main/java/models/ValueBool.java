package models;

import java.util.ArrayList;

public class ValueBool extends Value {

    private String val;

    /**
     * Constructor for ValueBool.
     *
     * @param val -> boolean value
     */
    public ValueBool(String val) {
        this.val=val;
    }

    /**
     * Checks ValueBool's semantic.
     *
     * @param env -> Environment that holds previously parsed information
     * @return Empty ArrayList of semantic errors
     */
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    /**
     * Type check is empty because it's a terminal node.
     *
     * @return instance of TypeBool()
     */
    public Type typeCheck() {
        return new TypeBool();
    }

}