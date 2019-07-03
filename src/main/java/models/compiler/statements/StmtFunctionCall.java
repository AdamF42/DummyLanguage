package models.compiler.statements;

import com.google.common.collect.Lists;
import models.compiler.*;
import models.compiler.expressions.Exp;
import models.compiler.stentry.FunStEntry;
import models.compiler.stentry.StEntry;
import models.compiler.stentry.VarStEntry;
import models.compiler.types.Type;
import models.compiler.types.TypeFunction;
import models.compiler.values.ValueId;
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
    private List<VarStEntry> formalParams;
    private TypeFunction envFunType;
    private String label;
    private int nl;
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
        this.nl=e.getNestingLevel();
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

        // TODO: rifattorizza
//        for (Exp param: actualParams) {
//            result.append(param.codeGeneration());
//            result.append(push(ACC));
//        }

        //push all params in inverted order
        for(int i = actualParams.size()-1; i>=0; i--) {
            result.append(actualParams.get(i).codeGeneration());
            result.append(push(ACC));
        }

        result.append(jal(label));

        // TODO: rifattorizza
        // get updated value for reference
        for(int i = actualParams.size()-1; i>=0; i--) {
            if(formalParams.get(i).isReference()){
                int offset = ((ValueId)actualParams.get(i)).getEntry().getOffset();
                result.append(loadW(ACC,String.valueOf(envFunType.getParam().get(i).getOffset()-4),TMP));
                result.append(loadW(AL, "0", FP));
                result.append(getVariableForCgen(nl, envFunType.getParam().get(i)));
                result.append(storeW(ACC, Integer.toString(offset), AL));
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
