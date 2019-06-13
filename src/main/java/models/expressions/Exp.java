package models.expressions;

import models.*;
import models.types.Type;
import models.values.ValueId;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
import util.TypeUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static util.Strings.*;


public class Exp extends ElementBase {

    private final Exp left;
    private final Exp right;
    private final String op;

    private final Set<STentry> rwAccesses = new HashSet<>();

    public Exp(Exp left, Exp right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    // TODO: better to create two separates classes for Exp, Term and Factor
    public boolean isValueId(){
        Exp term = getLeft(); // TODO: Maybe it is bugged
        Factor factor = (Factor) term.getLeft();
        return getRight() == null && term.getRight() == null &&
                factor.getRight() == null && factor.getLeft() instanceof ValueId;
    }

    public String getIdFromExp(){
        Term term = (Term) this.getLeft();
        Factor factor = (Factor) term.getLeft();
        if(this.getRight() == null && term.getRight()==null &&
                factor.getRight()==null && factor.getLeft() instanceof ValueId)
            return ((ValueId) factor.getLeft()).getId();

        return null;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
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

    @Override
    public String codeGeneration() {
        String result = left.codeGeneration();
        if (op!=null && right != null) {
            result += move(TMP,ACC) + right.codeGeneration() + Strings.GetCodeForOperator(op, right);
        }
        return result;
    }

    public Exp getLeft() {
        return left;
    }

    public Exp getRight() {
        return right;
    }

    public void addAllrwAccesses(Set<STentry> rwAccesses) {
        this.rwAccesses.addAll(rwAccesses);
    }

    public void addrwAccess(STentry entry) {
        this.rwAccesses.add(entry);
    }

    public Set<STentry> getRwAccesses() {
        return rwAccesses;
    }

    public String getOp() {
        return op;
    }
}
