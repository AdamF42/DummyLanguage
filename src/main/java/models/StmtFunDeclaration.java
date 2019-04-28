package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class StmtFunDeclaration extends Stmt {

    private String funId;
    private List<Parameter> params;
    private StmtBlock body;


    public StmtFunDeclaration(String funId, List<Parameter> params, ElementBase body){
        this.funId = funId;
        this.params = params;
        this.body = (StmtBlock) body;
    }


    @Override
    public Type typeCheck() throws TypeCheckError {
        return body.typeCheck();
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<>();

        if (e.containsVariable(funId)) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + funId));
        } else {
            List<Type> paramTypes = new ArrayList<>();
            for (Parameter param: params) {
                if (param.getId().equals(funId)){
                    result.add(new SemanticError(Strings.ERROR_PARAMETER_CALLDED_AS_FUNCTION + funId));
                }
                paramTypes.add(param.getType());
            }
            TypeFunction type = new TypeFunction(paramTypes);
            e.addVariable(funId, new STentry(e.getNestingLevel(), type));
        }

        e.openScope();

        if(params!=null)
            for(Parameter el:params)
                result.addAll(el.checkSemantics(e));


        //check body semantics
        if(body !=null) {
            result.addAll(body.checkSemanticsWithNoOpenScope(e));
        }

        e.closeScope();

        return result;
    }
}
