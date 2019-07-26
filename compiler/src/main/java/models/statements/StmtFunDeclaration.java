package models.statements;

import com.google.common.collect.Lists;
import models.Environment;
import models.stentry.FunStEntry;
import models.stentry.StEntry;
import models.stentry.VarStEntry;
import models.types.Parameter;
import models.types.Type;
import models.types.TypeFunction;
import exeptions.TypeCheckException;
import util.SemanticError;

import java.util.*;

import static util.SemanticErrorChecker.*;
import static util.Strings.*;


public class StmtFunDeclaration extends Stmt {

    private final String funId;
    private final List<Parameter> params;
    private final StmtBlock body;
    private String f_label;
    private int nl;
    private FunStEntry funEntry;

    public StmtFunDeclaration(String funId, List<Parameter> params, StmtBlock body){
        this.funId = funId;
        this.params = params;
        this.body = body;
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        body.typeCheck();
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>(checkFunIdSemantics(e));
        if(result.isEmpty()) {
            e.openScope();
            e.setInsideFunctionDeclaration(true);
            result.addAll(checkParamsSemantics(e));
            result.addAll(checkBodySemantics(e));
            e.setInsideFunctionDeclaration(false);
            this.nl = e.getNestingLevel();
            this.funEntry.addAllrwAccesses(this.getRwAccesses());
            this.funEntry.addAllDeletions(this.getDeletions());
            e.closeScope();
        }
        return result;
    }

    @Override
    public String codeGeneration() {
        String skipFunDec = getFreshLabel();
        return  b(skipFunDec) +
                this.f_label + ":\n" +
                loadW(AL,"0",FP) +
                loadW(AL,"0",AL) +
                getVariableForCgen(nl,funEntry.getNestinglevel()) +
                push(AL) +
                move(FP, SP) +
                push(RA) +
                body.codeGenerationForFunDec() +
                assignTop(RA) +
                pop() +
                pop() +
                move(TMP,SP) +
                addi(SP, SP, String.valueOf(params.size() * 4)) +
                body.handleVariablesAllocation(false) +
                assignTop(FP) +
                pop() +
                jr(RA) +
                skipFunDec+":\n";
    }

    private List<SemanticError> checkFunIdSemantics(Environment e) {

        StEntry idEntry = Optional.ofNullable((StEntry)e.getVariableValue(funId)).orElse(e.getFunctionValue(funId));
        if (ALREADY_DECLARED.apply(idEntry))
            return Lists.newArrayList(VALIDATION_ERRORS.get(ALREADY_DECLARED).apply(funId));

        TypeFunction type = new TypeFunction(new ArrayList<>(), body);
        String label = getFreshLabel();
        this.f_label=label;
        e.addFunction(funId, new FunStEntry(e.getNestingLevel(), type, funId, label));
        this.funEntry = e.getFunctionValue(funId);
        return new ArrayList<>();

    }

    private List<SemanticError> checkParamsSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>();
        StEntry funEntry = e.getFunctionValue(funId);
        TypeFunction funType = (TypeFunction) funEntry.getType();
        for (Parameter param : params) {
            result.addAll(param.checkSemantics(e));
            funType.addParam((VarStEntry)e.getVariableValueLocal(param.getId()));
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
