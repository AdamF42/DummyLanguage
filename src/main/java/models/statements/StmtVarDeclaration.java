package models.statements;

import com.google.common.collect.Lists;
import models.*;
import models.expressions.Exp;
import models.stentry.StEntry;
import models.stentry.VarStEntry;
import models.types.Type;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
import util.TypeUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static util.SemanticErrorChecker.ALREADY_DECLARED;
import static util.SemanticErrorChecker.VALIDATION_ERRORS;
import static util.Strings.ACC;
import static util.Strings.FP;

public class StmtVarDeclaration extends Stmt {

    private static List<Function<StEntry, Boolean>> CHECKS = Collections.singletonList(ALREADY_DECLARED);
    private final String id;
    private final Type type;
    private final Exp exp;
    private int offset;


    public StmtVarDeclaration(Type type, String id, Exp exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        if (exp != null){
            TypeUtils.typeCheck(type, exp);
        }
        return this.type.typeCheck();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        StEntry idEntry = Optional.ofNullable(e.getVariableValueLocal(id)).orElse(e.getFunctionValue(id));
        for (Function<StEntry, Boolean> check: CHECKS) {
            if (check.apply(idEntry))
                return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(id));
        }

        this.offset = e.getOffset();
        e.addVariable(id, new VarStEntry(e.getNestingLevel(), e.getOffset(), type, id));
        List<SemanticError> result = new ArrayList<>(exp.checkSemantics(e));
        this.addrwAccess(e.getVariableValueLocal(id));
        this.addAllrwAccesses(exp.getRwAccesses());

        return result;
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +
                Strings.storeW(ACC, Integer.toString(offset), FP);
    }
}
