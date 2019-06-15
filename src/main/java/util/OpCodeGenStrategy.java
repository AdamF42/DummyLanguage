package util;

import models.expressions.Exp;

public interface OpCodeGenStrategy {

    public String GetCodeForOperator(Exp right);

}
