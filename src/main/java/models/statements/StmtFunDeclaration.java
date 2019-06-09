package models.statements;

import models.*;
import models.types.Type;
import models.types.TypeFunction;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;

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
    public List<SemanticError> checkSemantics(Environment e) {
        //check function id semantics
        List<SemanticError> result = new ArrayList<>(checkFunIdSemantics(e));
        if(result.isEmpty()) {
            e.openScope();
            e.setInsideFunctionDeclaration(true);

            //check params semantics
            result.addAll(checkParamsSemantics(e));

            //check body semantics
            result.addAll(checkBodySemantics(e));

            e.setInsideFunctionDeclaration(false);
            e.closeScope();
        }

        return result;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    private List<SemanticError> checkFunIdSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        if ((e.containsFunction(funId) && !e.getFunctionValue(funId).isDeleted()) || (e.containsVariable(funId) && !e.getVariableValue(funId).isDeleted())) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + funId));
            return result;
        }
        TypeFunction type = new TypeFunction(new ArrayList<>(), body);
        e.addFunction(funId, new STentry(e.getNestingLevel(), type, funId));
        return result;

    }

    private List<SemanticError> checkParamsSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        STentry funEntry = e.getFunctionValue(funId);
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
