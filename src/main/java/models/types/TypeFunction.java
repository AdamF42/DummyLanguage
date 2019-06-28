package models.types;

import models.Environment;
import models.stentry.StEntry;
import util.SemanticError;
import models.statements.StmtBlock;

import java.util.*;

public class TypeFunction extends Type {
    private final List<StEntry> param;
    private final StmtBlock body;

    public TypeFunction(List<StEntry> param, StmtBlock body) {
        this.param = param;
        this.body = body;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    public List<StEntry> getParam() {
        return param;
    }

    public Set<StEntry> getDeletions() {
        return body.getDeletions();
    }

    public Set<StEntry> getRwAccesses() {
        return body.getRwAccesses();
    }

    public void addParam(StEntry paramEntry){
        this.param.add(paramEntry);
    }
}
