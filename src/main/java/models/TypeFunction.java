package models;

import java.util.*;

public class TypeFunction extends Type {
    private final List<TypeReferenceable> paramTypes;
    private final StmtBlock body;

    public TypeFunction(List<TypeReferenceable> paramTypes, StmtBlock body) {
        this.paramTypes = paramTypes;
        this.body = body;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        return null;
    }

    public List<TypeReferenceable> getParamTypes() {
        return paramTypes;
    }

    public Set<STentry> getDeletions() {
        return body.getDeletions();
    }

    public Set<STentry> getRwAccesses() {
        return body.getRwAccesses();
    }
}
