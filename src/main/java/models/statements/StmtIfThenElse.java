package models.statements;

import models.*;
import models.expressions.Exp;
import models.types.Type;
import models.types.TypeBool;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;

import java.util.ArrayList;
import java.util.List;

public class StmtIfThenElse extends Stmt {
    private Exp condition;
    private StmtBlock thenBranch;
    private StmtBlock elseBranch;


    public StmtIfThenElse(Exp condition, StmtBlock ifBranch, StmtBlock thenBranch) {
        this.condition = condition;
        this.thenBranch = ifBranch;
        this.elseBranch = thenBranch;
    }


    @Override
    public Type typeCheck() throws TypeCheckError {

        // check the condType
        Type conditionType = condition.typeCheck();
        if (!(conditionType instanceof TypeBool))
            throw new TypeCheckError("Not boolean condition, got " + conditionType);

        // check if the two Branches have the same behavioural type
        thenBranch.typeCheck();

        elseBranch.typeCheck();
        if (!thenBranch.getDeletions().isEmpty() || !elseBranch.getDeletions().isEmpty()) {
            if (!thenBranch.getRwAccesses().containsAll(elseBranch.getRwAccesses()) || !thenBranch.getDeletions().containsAll(elseBranch.getDeletions()))
                throw new TypeCheckError(Strings.ERROR_BEHAVIOR_MISMATCH);
        }
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<SemanticError>();

        if (condition != null){
            result.addAll(condition.checkSemantics(e));
            this.addAllrwAccesses(condition.getRwAccesses());
        }

        if (thenBranch != null){
            result.addAll(thenBranch.checkSemantics(e));
            this.addAllDeletions(thenBranch.getDeletions());
            this.addAllrwAccesses(thenBranch.getRwAccesses());
        }

        if (elseBranch != null){
            result.addAll(elseBranch.checkSemantics(e));
            this.addAllDeletions(elseBranch.getDeletions());
            this.addAllrwAccesses(elseBranch.getRwAccesses());
        }
        return result;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}