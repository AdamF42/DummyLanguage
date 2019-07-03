package compilermodels.statements;

import com.google.common.collect.Lists;
import compilermodels.*;
import compilermodels.expressions.Exp;
import compilermodels.stentry.FunStEntry;
import compilermodels.stentry.StEntry;
import compilermodels.types.Type;
import compilermodels.types.TypeFunction;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
import util.TypeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import static util.SemanticErrorChecker.*;
import static util.Strings.*;
import static util.TypeUtils.getIdFromExp;
import static util.TypeUtils.isExpValueId;

public class StmtFunctionCall extends Stmt {

    private String id;
    private List<Exp> actualParams;
    private List<StEntry> formalParams;
    private TypeFunction envFunType;
    private String label;
    private static List<Function<StEntry, Boolean>> CHECKS = Arrays.asList(IS_NULL, FUN_IS_DELETED);


    public StmtFunctionCall(String funId, List<Exp> actualParams) {
        this.id = funId;
        this.actualParams = actualParams;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {

        if (envFunType.getParam().size() != actualParams.size()) {
            throw new TypeCheckError(Strings.ERROR_PARAMETER_MISMATCH +
                    envFunType.getParam().size() + " got " + actualParams.size());
        }
        for (int i = 0; i < envFunType.getParam().size(); i++) {
            Type expectedType = envFunType.getParam().get(i).getType();
            ElementBase actualType = actualParams.get(i);
            TypeUtils.functionParamTypeCheck(expectedType, actualType);
        }

        return this.envFunType;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        FunStEntry FunStEntry = e.getFunctionValue(id);
        for (Function<StEntry, Boolean> check: CHECKS) {
            if (check.apply(FunStEntry))
                return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(id));
        }

        this.label = FunStEntry.GetLabel();
        result.addAll(setFunctionWithParams(e));
        result.addAll(checkParametersSemantics(e));
        result.addAll(checkFunDeletionsSemantics(e));

        return result;
    }

    @Override
    public String codeGeneration() {

        StringBuilder result = new StringBuilder();
        result.append(push(FP));
        for(Exp child:actualParams) {
            result.append(child.codeGeneration());
            result.append(push(ACC));
        }
        result.append(jal(label));

        return result.toString();
    }

    private List<SemanticError> checkParametersSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        if (actualParams != null && actualParams.size() == formalParams.size()) {
            for (int i = 0; i < formalParams.size(); i++) {
                Exp actualParam = actualParams.get(i);
                StEntry formalParam = formalParams.get(i);
                result.addAll(checkParamSemantics(e, actualParam, formalParam));
            }
        } else result.add(new SemanticError(Strings.ERROR_PARAMETER_MISMATCH + id));
        return result;
    }

    private List<SemanticError> setFunctionWithParams(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        Type actualFunctionType = e.getFunctionValue(id).getType();
        this.envFunType = (TypeFunction) actualFunctionType;
        this.formalParams = envFunType.getParam();
        return result;
    }

    private List<SemanticError> checkFunDeletionsSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        for (StEntry entry : envFunType.getDeletions()) {
            if (entry.isToBeDeletedOnFunCall() && !entry.isReference()) {
                if(entry.isDeleted()) {
                    result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + entry.getId()));
                } else {
                    entry.setDeleted(true);
                }
            }
        }
        return result;
    }

    private List<SemanticError> checkParamSemantics(Environment e, Exp actualParam, StEntry formalParam) {
        String actualParamId = getIdFromExp(actualParam);
        List<SemanticError> result = new ArrayList<>(actualParam.checkSemantics(e));
        if (formalParam == null) return result;
        // Handle EXAMPLE 1
        if (formalParam.isReference() &&
                e.containsVariable(actualParamId) &&
                isExpValueId(actualParam) &&
                (formalParam.isDeleted() || formalParam.isToBeDeletedOnFunCall())) {
            e.getVariableValue(actualParamId).setDeleted(true);
        }

        if (!this.envFunType.getDeletions().isEmpty()) {
            for (StEntry accessedVariable : this.envFunType.getRwAccesses()) {
                if (accessedVariable.getNestinglevel() <= e.getFunctionValue(id).getNestinglevel()) {
                    if (e.getVariableValue(actualParamId).equals(accessedVariable)) {
                        result.add(new SemanticError(Strings.ERROR_DANGEROUS_USE_OF_PARAMETER + actualParamId));
                    }
                }
            }
        }
        return result;
    }
}
