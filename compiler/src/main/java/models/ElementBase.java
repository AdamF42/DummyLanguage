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
}
