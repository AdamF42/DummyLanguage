package models.expressions;

import models.*;
import models.stentry.STentry;
import models.types.Type;
import util.*;
import util.operationCodeGenStrategy.OpCodeGenStrategy;
import util.operationCodeGenStrategy.OpCodeGenStrategyFactory;
import util.operationCodeGenStrategy.OpCodeGenStrategyFactoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
            OpCodeGenStrategyFactory opCodeGenStrategyFactory = new OpCodeGenStrategyFactoryImpl();
            OpCodeGenStrategy operationStrategy = opCodeGenStrategyFactory.GetOperationStrategy(op);
            result +=operationStrategy.GetCodeForOperator(right);
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
