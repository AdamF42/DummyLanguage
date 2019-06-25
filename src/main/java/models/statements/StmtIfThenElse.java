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

import static util.Strings.*;


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

        Type conditionType = condition.typeCheck();
        if (!(conditionType instanceof TypeBool))
            throw new TypeCheckError("Not boolean condition, got " + conditionType);
        thenBranch.typeCheck();
        elseBranch.typeCheck();
        if (!thenBranch.getDeletions().isEmpty() || !elseBranch.getDeletions().isEmpty()) {
            if (!thenBranch.getRwAccesses().containsAll(elseBranch.getRwAccesses()) || !thenBranch.getDeletions().containsAll(elseBranch.getDeletions()))
                throw new TypeCheckError(ERROR_BEHAVIOR_MISMATCH);
        }
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result =
                new ArrayList<>(condition.checkSemantics(e));
        this.addAllrwAccesses(condition.getRwAccesses());

        result.addAll(thenBranch.checkSemantics(e));
        this.addAllDeletions(thenBranch.getDeletions());
        this.addAllrwAccesses(thenBranch.getRwAccesses());

        result.addAll(elseBranch.checkSemantics(e));
        this.addAllDeletions(elseBranch.getDeletions());
        this.addAllrwAccesses(elseBranch.getRwAccesses());

        return result;
    }

    @Override
    public String codeGeneration() {
        String exit = Strings.GetFreshLabel();
        String elseBranchLabel = Strings.GetFreshLabel();
        return
                condition.codeGeneration() +
                loadI(TMP,"0") +
                assignTop(TMP) +
                Strings.beq(ACC,TMP,elseBranchLabel) +
                thenBranch.codeGeneration() +
                b(exit) +
                elseBranchLabel+":\n" +
                elseBranch.codeGeneration() +
                exit+":\n";
    }
}
