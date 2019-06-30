package compilermodels.statements;

import com.google.common.collect.Lists;
import compilermodels.*;
import compilermodels.stentry.FunStEntry;
import compilermodels.stentry.StEntry;
import compilermodels.types.Parameter;
import compilermodels.types.Type;
import compilermodels.types.TypeFunction;
import util.SemanticError;
import util.TypeCheckError;
import java.util.*;
import java.util.function.Function;
import static util.SemanticErrorChecker.*;
import static util.Strings.*;


public class StmtFunDeclaration extends Stmt {

    private static List<Function<StEntry, Boolean>> CHECKS = Collections.singletonList(ALREADY_DECLARED);
    private final String funId;
    private final List<Parameter> params;
    private final StmtBlock body;
    private String f_label;

    public StmtFunDeclaration(String funId, List<Parameter> params, StmtBlock body){
        this.funId = funId;
        this.params = params;
        this.body = body;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        return body.typeCheck();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>(checkFunIdSemantics(e));
        e.openScope();
        e.setInsideFunctionDeclaration(true);
        result.addAll(checkParamsSemantics(e));
        result.addAll(checkBodySemantics(e));
        e.setInsideFunctionDeclaration(false);
        e.closeScope();

        return result;
    }

    @Override
    public String codeGeneration() {

        return this.f_label + ":\n" +
                move(FP, SP) +
                push(RA) +
                body.codeGenerationForFunDec() +
                assignTop(RA) +
                addi(SP, SP, String.valueOf(params.size() * 4)) +
                assignTop(FP) +
                pop() +
                jr(RA);
    }

    private List<SemanticError> checkFunIdSemantics(Environment e) {

        StEntry idEntry = Optional.ofNullable((StEntry)e.getVariableValue(funId)).orElse(e.getFunctionValue(funId));
        for (Function<StEntry, Boolean> check: CHECKS) {
            if (check.apply(idEntry))
                return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(funId));
        }

        TypeFunction type = new TypeFunction(new ArrayList<>(), body);
        String label = GetFreshLabel();
        this.f_label=label;
        e.addFunction(funId, new FunStEntry(e.getNestingLevel(), type, funId, label));
        return new ArrayList<>();

    }

    private List<SemanticError> checkParamsSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        StEntry funEntry = e.getFunctionValue(funId);
        TypeFunction funType = (TypeFunction) funEntry.getType();
        for (Parameter param : params) {
            result.addAll(param.checkSemantics(e));
            funType.addParam(e.getVariableValueLocal(param.getId()));
        }

        return result;
    }

    private List<SemanticError> checkBodySemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>(body.checkSemanticsWithNoOpenScope(e));
        this.addAllDeletions(body.getDeletions());
        this.addAllrwAccesses(body.getRwAccesses());

        return result;
    }



}
