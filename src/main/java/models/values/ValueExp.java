package models.values;

import models.Environment;
import models.expressions.Exp;
import models.types.Type;
import util.SemanticError;
import util.TypeCheckError;
import java.util.List;


public class ValueExp extends Value {

    private final Exp exp;

    public ValueExp(Exp exp) {
        super(null);
        this.exp=exp;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration();
    }




}
