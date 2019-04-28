package util;

import models.*;

public class TypeUtils {

    public static void functionParamTypeCheck(Type expectedType, ElementBase actualElement) throws TypeCheckError {

        if (expectedType instanceof TypeReferenceable) {
            ElementBase temp = actualElement;
            while (temp instanceof BinaryOp) {
                BinaryOp binaryOp = (BinaryOp) temp;
                if (binaryOp.getRight() != null) {
                    throw new TypeCheckError("ExpectedType: var " + expectedType + ", got: right term " + actualElement);
                } else {
                    temp = binaryOp.getLeft();
                }
            }
            if (!(temp instanceof ValueId)) {
                throw new TypeCheckError("ExpectedType: var " + expectedType + ", got value " + temp);
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
