package models;

import util.Strings;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Simple Expression
 * Some child classes of this one will be SimpleExpSum, SimpleExpDiff,
 * SimpleExpDiv, SimpleExpMult and SimpleExpNeg
 *
 * @author Abel
 */
public class Exp extends ElementBase {

    private Exp left;
    private Exp right;

    private final Set<STentry> rwAccesses = new HashSet<>();

    public Exp(Exp left, Exp right) {
        this.left = left;
        this.right = right;    }

    public boolean isValueId(){

        Exp term = (Exp) getLeft();
        Factor factor = (Factor) term.getLeft();

        if(getRight() == null && term.getRight()==null &&
                factor.getRight()==null && factor.getLeft() instanceof ValueId)
            return true;

        return false;
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
}
