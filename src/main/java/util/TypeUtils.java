package util;

import models.compiler.*;
import models.compiler.expressions.Exp;
import models.compiler.types.Type;
import models.compiler.types.TypeReferenceable;
import models.compiler.values.Value;
import models.compiler.values.ValueId;

public class TypeUtils {

    public static String getIdFromExp(Exp exp){
        if(exp instanceof ValueId) return ((ValueId) exp).getId();
        return null;
    }

    public static boolean isExpValueId(Exp exp){
        return exp instanceof ValueId;
    }

    //TODO: refactor
    public static void functionParamTypeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckException {

        if (expectedType instanceof TypeReferenceable && ((TypeReferenceable) expectedType).isReference()) {
            ElementBase temp = actualElement;
            ElementBase tmp = null;
            while (temp instanceof Exp) {
                if (((Exp) temp).getRight() != null) {
                    throw new TypeCheckException("ExpectedType: var " + expectedType + ", got: right term " + actualElement.typeCheck());
                } else {
                    tmp = temp;
                    temp = ((Exp) temp).getLeft();
                }
            }
            if (tmp instanceof Value && !(tmp instanceof ValueId)) {
                throw new TypeCheckException("ExpectedType: var " + expectedType + ", got value " + ((Value) tmp).getVal());
            }
        }
        typeCheck(expectedType, actualElement);
    }

    public static void typeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckException {
        if (!expectedType.getClass().equals(actualElement.typeCheck().getClass())) {
            throw new TypeCheckException("ExpectedType " + (expectedType instanceof TypeReferenceable ? "var " : "") + expectedType + ", got " + actualElement.typeCheck());
        }
    }

}
