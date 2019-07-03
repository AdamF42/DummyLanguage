package models.compiler.statements;

import com.google.common.collect.Lists;
import models.compiler.*;
import models.compiler.stentry.FunStEntry;
import models.compiler.stentry.StEntry;
import models.compiler.stentry.VarStEntry;
import models.compiler.types.Parameter;
import models.compiler.types.Type;
import models.compiler.types.TypeFunction;
import util.SemanticError;
import util.TypeCheckError;
import java.util.*;
import java.util.function.Function;
import static util.SemanticErrorChecker.*;
import static util.Strings.*;


public class StmtFunDeclaration extends Stmt {

    private static List<Function<StEntry, Boolean>> CHECKS = Collections.singletonList(ALREADY_DECLARED);
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
        this.nl=e.getNestingLevel();
        e.closeScope();

        return result;
    }

    @Override
    public String codeGeneration() {
        String skipFunDec = GetFreshLabel();
        return  b(skipFunDec) +
                this.f_label + ":\n" +
                //// TESTING //// TODO: ragionaci
                loadW(AL,"0",FP) +
                loadW(AL,"0",AL) +
                getVariableForCgen(nl,funEntry) +
                // END TESTING //
                push(AL) +
                move(FP, SP) +
                push(RA) +
                body.codeGenerationForFunDec() +
                assignTop(RA) +
                pop() +
                pop() +
                move(TMP,SP) +
                addi(SP, SP, String.valueOf(params.size() * 4)) +
                assignTop(FP) +
                pop() +
                jr(RA) +
                skipFunDec+":\n";
    }

    private List<SemanticError> checkFunIdSemantics(Environment e) {

        StEntry idEntry = Optional.ofNullable((StEntry)e.getVariableValue(funId)).orElse(e.getFunctionValue(funId));
        for (Function<StEntry, Boolean> check: CHECKS) {
            if (check.apply(idEntry))
                return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(funId));
        }

        TypeFunction type = new TypeFunction(new ArrayList<>(), body);
        String label = GetFreshLabel();
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
