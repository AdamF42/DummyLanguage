package models;

import java.util.ArrayList;

public class ValueBool extends Value {


    public ValueBool(String val) {
        super(val);
    }

    /**
     * Checks ValueBool's semantic.
     *
     * @param env -> Environment that holds previously parsed information
     * @return Empty ArrayList of semantic errors
     */
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    /**
     * Type check is empty because it's a terminal node.
     *
     * @return instance of TypeBool()
     */
    @Override
    public Type typeCheck() {
        return new TypeBool();
    }

}