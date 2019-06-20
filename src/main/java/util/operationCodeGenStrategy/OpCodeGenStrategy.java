package util.operationCodeGenStrategy;

import models.expressions.Exp;

public interface OpCodeGenStrategy {

    String GetCodeForOperator(Exp right);

}
