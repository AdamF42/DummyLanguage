package models;

import util.Strings;
import util.TypeUtils;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class StmtFunctionCall extends Stmt {

    private String id;
    private List<Exp> actualParams;
    private List<TypeReferenceable> formalParams;
    private TypeFunction envFunType;


    public StmtFunctionCall(String funId, List<Exp> actualParams) {
        this.id = funId;
        this.actualParams = actualParams;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {

        if (envFunType != null) {

            if (envFunType.getParamTypes().size() != actualParams.size()) {
                throw new TypeCheckError(Strings.ERROR_PARAMETER_MISMATCH +
                        envFunType.getParamTypes().size() + " got " + actualParams.size());
            }
            for (int i = 0; i < envFunType.getParamTypes().size(); i++) {
                Type expectedType = envFunType.getParamTypes().get(i);
                ElementBase actualType = actualParams.get(i);
                TypeUtils.functionParamTypeCheck(expectedType, actualType);

            }
        }
        return this.envFunType;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<>();
        STentry sTentry = e.getVariableValue(id);
        if (sTentry == null || sTentry.getType().isDeleted() || !(sTentry.getType() instanceof TypeFunction)) {
            result.add(new SemanticError(Strings.ERROR_FUNCTION_DOESNT_EXIST + id));
        } else {
            // retrieve function and parameters type
            result.addAll(setFunctionWithParams(e));

            result.addAll(checkParametersSemantics(e));

            // check for deletes of external variables
            result.addAll(checkFunDeletionsSemantics(e));
        }
        return result;
    }

    private List<SemanticError> checkParametersSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        if (actualParams != null && actualParams.size() == formalParams.size()) {

            for (int i = 0; i < formalParams.size(); i++) {
                Exp actualParam = actualParams.get(i);
                TypeReferenceable formalParam = formalParams.get(i);
                result.addAll(checkParamSemantics(e, actualParam, formalParam));
            }

        } else result.add(new SemanticError(Strings.ERROR_PARAMETER_MISMATCH + id));
        return result;
    }

    private List<SemanticError> setFunctionWithParams(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        Type actualFunctionType = e.getVariableValue(id).getType();
        if (actualFunctionType instanceof TypeFunction) {
            this.envFunType = (TypeFunction) actualFunctionType;
            this.formalParams = envFunType.getParamTypes();

        } else {
            result.add(new SemanticError(Strings.ERROR_NOT_CALLABLE + id +
                    " " + actualFunctionType.getClass().getSimpleName()));
        }
        return result;
    }

    private List<SemanticError> checkFunDeletionsSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();
        for (STentry entry : envFunType.getDeletions()) {
            if (entry.getType().isToBeDeletedOnFunCall() && !(entry.getType() instanceof TypeReferenceable &&
                    ((TypeReferenceable) entry.getType()).isReference())) {
                if(entry.getType().isDeleted()) {
                    result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + entry.getId()));
                } else {
                    entry.getType().setDeleted(true);
                }
            }
        }
        return result;
    }

    private List<SemanticError> checkParamSemantics(Environment e, Exp actualParam, TypeReferenceable formalParam) {
        String actualParamId = actualParam.getIdFromExp();
        List<SemanticError> result = new ArrayList<>(actualParam.checkSemantics(e));
        // Handle EXAMPLE 1
        if (formalParam.isReference() &&
                actualParam.isValueId() &&
                e.containsVariable(actualParamId) &&
                (formalParam.isDeleted() || formalParam.isToBeDeletedOnFunCall())) {
            e.getVariableValue(actualParamId).getType().setDeleted(true);
        }

        if (!this.envFunType.getDeletions().isEmpty()) {
            for (STentry accessedVariable : this.envFunType.getRwAccesses()) {
                if (accessedVariable.getNestinglevel() <= e.getVariableValue(id).getNestinglevel()) {
                    if (e.getVariableValue(actualParamId).equals(accessedVariable)) {
                        result.add(new SemanticError(Strings.ERROR_DANGEROUS_USE_OF_PARAMETER + actualParamId));
                    }
                }
            }
        }


        return result;
    }
}
