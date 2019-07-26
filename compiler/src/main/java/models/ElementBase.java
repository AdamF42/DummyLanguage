package models;

import models.types.Type;
import exeptions.TypeCheckException;
import util.SemanticError;

import java.util.List;

public abstract class ElementBase {


    public abstract Type typeCheck() throws TypeCheckException;

    public abstract List<SemanticError> checkSemantics(Environment e);

    public abstract String codeGeneration();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void typeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckException {
        if (!expectedType.getClass().equals(actualElement.typeCheck().getClass())) {
            throw new TypeCheckException("ExpectedType " + expectedType + ", got " + actualElement.typeCheck());
        }
    }

}
