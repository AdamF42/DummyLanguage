package models;

import java.util.List;

public abstract class ElementBase {

    /**
     * Check type of Node.
     *
     * @return type of Node
     */
    public abstract Type typeCheck() throws TypeCheckError;


    /**
     * Checks Node's semantic.
     *
     * @param e -> Environment that holds previously parsed information
     * @return updated ArrayList of semantic errors
     */
    abstract List<SemanticError> checkSemantics(Environment e);


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
