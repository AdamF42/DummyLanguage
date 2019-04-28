package models;

import java.util.List;

public class TypeFunction extends Type {

    private final List<Type> paramTypes;

    public TypeFunction(List<Type> paramTypes) {
        this.paramTypes = paramTypes;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        return null;
    }

    public List<Type> getParamTypes() {
        return paramTypes;
    }
}
