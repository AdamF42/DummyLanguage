package compilermodels;

import compilermodels.types.Type;
import util.SemanticError;
import util.TypeCheckError;

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
    public abstract List<SemanticError> checkSemantics(Environment e);

    public abstract String codeGeneration();


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
