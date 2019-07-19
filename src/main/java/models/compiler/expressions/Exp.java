package models.compiler.expressions;

import models.compiler.*;
import models.compiler.stentry.StEntry;
import models.compiler.types.Type;
import util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class Exp extends ElementBase {

    private final Exp left;
    private final Exp right;

    private final Set<StEntry> rwAccesses = new HashSet<>();

    public Exp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        if (right != null){
            TypeUtils.typeCheck(left.typeCheck(),right);
        }
        return this.left.typeCheck();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(left.checkSemantics(env));
        this.addAllrwAccesses(left.getRwAccesses());
        if (right != null) {
            res.addAll(right.checkSemantics(env));
            this.addAllrwAccesses(right.getRwAccesses());
        }
        return res;
    }

    public Exp getLeft() {
        return left;
    }

    public Exp getRight() {
        return right;
    }

    public void addAllrwAccesses(Set<StEntry> rwAccesses) {
        this.rwAccesses.addAll(rwAccesses);
    }

    public void addrwAccess(StEntry entry) {
        this.rwAccesses.add(entry);
    }

    public Set<StEntry> getRwAccesses() {
        return rwAccesses;
    }
}
