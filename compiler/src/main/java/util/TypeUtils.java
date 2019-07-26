package util;

import exeptions.TypeCheckException;
import models.ElementBase;
import models.expressions.Exp;
import models.types.Type;
import models.types.TypeReferenceable;
import models.values.Value;
import models.values.ValueId;

public class TypeUtils {

    public static String getIdFromExp(Exp exp){
        if(exp instanceof ValueId) return ((ValueId) exp).getId();
        return null;
    }

    public static boolean isExpValueId(Exp exp){
        return exp instanceof ValueId;
    }


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
            throw new TypeCheckException("ExpectedType " + expectedType + ", got " + actualElement.typeCheck());
        }
    }

}
