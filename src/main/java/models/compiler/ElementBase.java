package models.compiler;

import models.compiler.types.Type;
import util.SemanticError;
import util.TypeCheckException;

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
