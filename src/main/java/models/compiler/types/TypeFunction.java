package models.compiler.types;

import models.compiler.Environment;
import models.compiler.stentry.StEntry;
import models.compiler.stentry.VarStEntry;
import util.SemanticError;
import models.compiler.statements.StmtBlock;

import java.util.*;

public class TypeFunction extends Type {
    private final List<VarStEntry> param;

    public StmtBlock getBody() {
        return body;
    }

    private final StmtBlock body;

    public TypeFunction(List<VarStEntry> param, StmtBlock body) {
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

    public List<VarStEntry> getParam() {
        return param;
    }

    public Set<StEntry> getDeletions() {
        return body.getDeletions();
    }

    public Set<StEntry> getRwAccesses() {
        return body.getRwAccesses();
    }

    public void addParam(VarStEntry paramEntry){
        this.param.add(paramEntry);
    }
}
