package models.values;

import models.Environment;
import util.SemanticError;
import models.types.Type;
import models.types.TypeInt;

import java.util.ArrayList;

public class ValueInt extends Value {


    public ValueInt(String val) {
        super(val);
    }

    /**
     * Checks ValueInt's semantic.
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
    public Type typeCheck() {
        return new TypeInt();
    }


}