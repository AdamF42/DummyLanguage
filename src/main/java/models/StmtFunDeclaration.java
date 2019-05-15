package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class StmtFunDeclaration extends Stmt {

    private String funId;
    private List<Parameter> params;
    private StmtBlock body;


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
    List<SemanticError> checkSemantics(Environment e) {
        //check function id semantics
        List<SemanticError> result = new ArrayList<>(checkFunIdSemantics(e));
        if(result.isEmpty()) {
            e.openScope();
            e.setInsideFunctionDeclaration(true);
            e.setFunctionNestingLevel(e.getNestingLevel());

            //check params semantics
            result.addAll(checkParamsSemantics(e));

            //check body semantics
            result.addAll(checkBodySemantics(e));

            e.setInsideFunctionDeclaration(false);
            e.closeScope();
        }

        return result;
    }

    private List<SemanticError> checkFunIdSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        if (e.containsVariable(funId) && !e.getVariableValue(funId).isDeleted()) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + funId));
            return result;
        } else {

            for (Parameter param: params) {
                if (param.getId().equals(funId)){
                    result.add(new SemanticError(Strings.ERROR_PARAMETER_CALLDED_AS_FUNCTION + funId));
                }
            }
            TypeFunction type = new TypeFunction(new ArrayList<>(), body);
            e.addVariable(funId, new STentry(e.getNestingLevel(), type, funId));
        }
        return result;
    }

    private List<SemanticError> checkParamsSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        STentry funEntry = e.getVariableValue(funId);
        TypeFunction funType = (TypeFunction) funEntry.getType();
        if(params!=null) {
            for (Parameter param : params) {
                result.addAll(param.checkSemantics(e));
                funType.addParam(e.getVariableValueLocal(param.getId()));
            }
        }

        return result;
    }

    private List<SemanticError> checkBodySemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();

        if(body !=null) {
            result.addAll(body.checkSemanticsWithNoOpenScope(e));
            this.addAllDeletions(body.getDeletions());
            this.addAllrwAccesses(body.getRwAccesses());
        }

        return result;
    }



}
