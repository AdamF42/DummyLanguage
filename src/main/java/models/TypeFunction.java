package models;

import java.util.*;

public class TypeFunction extends Type {
    private final List<STentry> param;
    private final StmtBlock body;

    public TypeFunction(List<STentry> param, StmtBlock body) {
        this.param = param;
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

    public List<STentry> getParam() {
        return param;
    }

    public Set<STentry> getDeletions() {
        return body.getDeletions();
    }

    public Set<STentry> getRwAccesses() {
        return body.getRwAccesses();
    }

    public void addParam(STentry paramEntry){
        this.param.add(paramEntry);
    }
}
