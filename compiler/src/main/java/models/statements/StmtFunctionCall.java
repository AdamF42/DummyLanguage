package models.statements;

import com.google.common.collect.Lists;
import models.ElementBase;
import models.Environment;
import models.expressions.Exp;
import models.stentry.FunStEntry;
import models.stentry.StEntry;
import models.stentry.VarStEntry;
import models.types.Type;
import models.types.TypeFunction;
import models.values.ValueId;
import util.SemanticError;
import util.Strings;
import exeptions.TypeCheckException;
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

    private final String id;
    private final List<Exp> actualParams;
    private List<VarStEntry> formalParams;
    private TypeFunction envFunType;
    private String label;
    private int nl;
    private static final List<Function<StEntry, Boolean>> CHECKS = Arrays.asList(IS_NULL, FUN_IS_DELETED);


    public StmtFunctionCall(String funId, List<Exp> actualParams) {
        this.id = funId;
        this.actualParams = actualParams;
    }

    @Override
    public Type typeCheck() throws TypeCheckException {

        if (envFunType.getParam().size() != actualParams.size()) {
            throw new TypeCheckException(Strings.ERROR_PARAMETER_MISMATCH +
                    envFunType.getParam().size() + " got " + actualParams.size());
        }
        for (int i = 0; i < envFunType.getParam().size(); i++) {
            Type expectedType = envFunType.getParam().get(i).getType();
            ElementBase actualType = actualParams.get(i);
            TypeUtils.functionParamTypeCheck(expectedType, actualType);
        }

        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        FunStEntry FunStEntry = e.getFunctionValue(id);
        for (Function<StEntry, Boolean> check: CHECKS) {
            if (check.apply(FunStEntry))
                return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(id));
        }
        this.nl=e.getNestingLevel();
        this.label = FunStEntry.GetLabel();
        setFunctionWithParams(e);
        result.addAll(checkParametersSemantics(e));
        result.addAll(checkFunDeletionsSemantics());

        return result;
    }

    @Override
    public String codeGeneration() {

        StringBuilder result = new StringBuilder();
        result.append(push(FP));

        result.append( envFunType.getBody().handleVariablesAllocation(true));

        for(int i = actualParams.size()-1; i>=0; i--) {
            result.append(actualParams.get(i).codeGeneration());
            result.append(push(ACC));
        }

        result.append(jal(label));

        // update reference variables value
        for(int i = actualParams.size()-1; i>=0; i--) {
            if(formalParams.get(i).isReference()){
                VarStEntry entry = ((ValueId)actualParams.get(i)).getEntry();
                result.append(loadW(ACC,String.valueOf(envFunType.getParam().get(i).getOffset()-4),TMP));
                if (nl==entry.getNestinglevel()) {
                    result.append(storeW(ACC, Integer.toString(entry.getOffset()), FP));
                } else {
                    result.append(loadW(AL, "0", FP));
                    result.append(getVariableForCgen(nl, entry.getNestinglevel()));
                    result.append(storeW(ACC, Integer.toString(entry.getOffset()), AL));
                }
            }
        }

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
        } else result.add(new SemanticError(Strings.ERROR_PARAMETER_MISMATCH + formalParams.size()));

        return result;
    }

    private void setFunctionWithParams(Environment e) {

        Type actualFunctionType = e.getFunctionValue(id).getType();
        this.envFunType = (TypeFunction) actualFunctionType;
        this.formalParams = envFunType.getParam();

    }

    private List<SemanticError> checkFunDeletionsSemantics() {

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
        if (formalParam.isReference() && e.containsVariable(actualParamId) && isExpValueId(actualParam) && (formalParam.isDeleted() || formalParam.isToBeDeletedOnFunCall())) {
            e.getVariableValue(actualParamId).setDeleted(true);
        }
        // avoid using global params by reference
        if (formalParam.isReference() && isExpValueId(actualParam)){
            StEntry actuamParamVariable = e.getVariableValue(((ValueId)actualParam).getId());
            FunStEntry funEntry = e.getFunctionValue(id);
            if (actuamParamVariable.getNestinglevel() <= funEntry.getNestinglevel() &&
                    funEntry.getRwAccesses().contains(actuamParamVariable) ||
                    funEntry.getDeletions().contains(actuamParamVariable)){
                result.add(new SemanticError(Strings.ERROR_GLOBAL_VAR_AS_PARAMETER + actualParamId));
            }
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
