package util;

import compilermodels.*;
import compilermodels.expressions.Exp;
import compilermodels.types.Type;
import compilermodels.types.TypeReferenceable;
import compilermodels.values.Value;
import compilermodels.values.ValueId;

public class TypeUtils {

    public static String getIdFromExp(Exp exp){
        if(exp instanceof ValueId) return ((ValueId) exp).getId();
        return null;
    }

    public static boolean isExpValueId(Exp exp){
        return exp instanceof ValueId;
    }

    //TODO: refactor
    public static void functionParamTypeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckError {

        if (expectedType instanceof TypeReferenceable && ((TypeReferenceable) expectedType).isReference()) {
            ElementBase temp = actualElement;
            ElementBase tmp = null;
            while (temp instanceof Exp) {
                if (((Exp) temp).getRight() != null) {
                    throw new TypeCheckError("ExpectedType: var " + expectedType + ", got: right term " + actualElement.typeCheck());
                } else {
                    tmp = temp;
                    temp = ((Exp) temp).getLeft();
                }
            }
            if (tmp instanceof Value && !(tmp instanceof ValueId)) {
                throw new TypeCheckError("ExpectedType: var " + expectedType + ", got value " + ((Value) tmp).getVal());
            }
        }
        typeCheck(expectedType, actualElement);
    }

    public static void typeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckError {
        if (!expectedType.getClass().equals(actualElement.typeCheck().getClass())) {
            throw new TypeCheckError("ExpectedType " + (expectedType instanceof TypeReferenceable ? "var " : "") + expectedType + ", got " + actualElement.typeCheck());
        }
    }

}
