package models;

import util.Strings;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public class StmtFunctionCall extends Stmt {

    private String id;
    private List<ElementBase> params;
    private TypeFunction envFunType;


    public StmtFunctionCall(String funId, List<ElementBase> params) {
        this.id = funId;
        this.params = params;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {

        if (envFunType != null) {

            if (envFunType.getParamTypes().size() != params.size()) {
                throw new TypeCheckError(Strings.ERROR_PARAMETER_MISMATCH +
                        envFunType.getParamTypes().size() + " got " + params.size());
            }
            for (int i = 0 ; i<envFunType.getParamTypes().size(); i++) {
                Type expectedType = envFunType.getParamTypes().get(i);
                ElementBase actualType = params.get(i);
                TypeUtils.functionParamTypeCheck(expectedType, actualType);
            }
        }
        return this.envFunType;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<SemanticError>();

        if (!e.containsVariable(id)) {
            result.add(new SemanticError(Strings.ERROR_FUNCTION_DOESNT_EXIST + id));
        } else {
            Type actualFunctionType = e.getVariableValue(id).getType();
            if (actualFunctionType instanceof TypeFunction){
                this.envFunType = (TypeFunction) e.getVariableValue(id).getType();
            }else{
                result.add(new SemanticError(Strings.ERROR_NOT_CALLABLE + id +
                        " " + actualFunctionType.getClass().getSimpleName()));
            }
        }

        if (params != null) {
            for (ElementBase param : params)
                result.addAll(param.checkSemantics(e));
        }

        return result;
    }
}
