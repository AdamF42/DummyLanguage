package models;

import java.util.ArrayList;

public class ValueInt extends Value {

    private Integer val;

    /**
     * Constructor for ValueInt.
     *
     * @param val -> integer value
     */
    public ValueInt(Integer val) {
        this.val=val;
    }

    /**
     * Checks ValueInt's semantic.
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
        return new TypeInt();
    }


}