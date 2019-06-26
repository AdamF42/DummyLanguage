package models.statements;

import models.*;
import models.stentry.FunSTentry;
import models.stentry.STentry;
import models.types.Parameter;
import models.types.Type;
import models.types.TypeFunction;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
import java.util.ArrayList;
import java.util.List;
import static util.Strings.*;


public class StmtFunDeclaration extends Stmt {

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

        List<SemanticError> result = new ArrayList<>();
        if ((e.containsFunction(funId) && !e.getFunctionValue(funId).isDeleted()) || (e.containsVariable(funId) && !e.getVariableValue(funId).isDeleted())) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + funId));
            return result;
        }
        TypeFunction type = new TypeFunction(new ArrayList<>(), body);
        String label = GetFreshLabel();
        this.f_label=label;
        e.addFunction(funId, new FunSTentry(e.getNestingLevel(), type, funId, label));
        return result;

    }

    private List<SemanticError> checkParamsSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        STentry funEntry = e.getFunctionValue(funId);
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
