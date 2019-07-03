package util;

import parser.CVMParser;
import parser.ExecuteVM;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import static util.Strings.*;

public class RegisterUtils {

    private static final Function<ExecuteVM, Integer> ACC_REGISTER_VALUE = ExecuteVM::getA0;
    private static final Function<ExecuteVM, Integer> TMP_REGISTER_VALUE = ExecuteVM::getT1;
    private static final Function<ExecuteVM, Integer> SP_REGISTER_VALUE = ExecuteVM::getSp;
    private static final Function<ExecuteVM, Integer> FP_REGISTER_VALUE = ExecuteVM::getFp;
    private static final Function<ExecuteVM, Integer> AL_REGISTER_VALUE = ExecuteVM::getAl;
    private static final Function<ExecuteVM, Integer> RA_REGISTER_VALUE = ExecuteVM::getRa;

    private static final BiFunction<ExecuteVM, Integer, Integer> ACC_REGISTER_VALUE_SET = ExecuteVM::setA0;
    private static final BiFunction<ExecuteVM, Integer, Integer> TMP_REGISTER_VALUE_SET = ExecuteVM::setT1;
    private static final BiFunction<ExecuteVM, Integer, Integer> SP_REGISTER_VALUE_SET = ExecuteVM::setSp;
    private static final BiFunction<ExecuteVM, Integer, Integer> FP_REGISTER_VALUE_SET = ExecuteVM::setFp;
    private static final BiFunction<ExecuteVM, Integer, Integer> AL_REGISTER_VALUE_SET = ExecuteVM::setAl;
    private static final BiFunction<ExecuteVM, Integer, Integer> RA_REGISTER_VALUE_SET = ExecuteVM::setRa;

    private static final BiFunction<Integer, Integer, Integer> ADD_FUNCTION = Integer::sum;
    private static final BiFunction<Integer, Integer, Integer> SUB_FUNCTION = (int1, int2) -> int1 - int2;
    private static final BiFunction<Integer, Integer, Integer> MULT_FUNCTION = (int1, int2) -> int1 * int2;
    private static final BiFunction<Integer, Integer, Integer> DIV_FUNCTION = (int1, int2) -> int1 / int2;

    public static final Map<String, Function<ExecuteVM, Integer>> GET_REGISTER_VALUE;
    public static final Map<String, BiFunction<ExecuteVM, Integer, Integer>> SET_REGISTER_VALUE;
    public static final HashMap<Integer, BiFunction<Integer, Integer, Integer>> DO_OPERATION;
    public static final HashMap<String,Integer> REGISTER_TO_INT;
    public static final HashMap<Integer,String> INT_TO_REGISTER;

    static {
        GET_REGISTER_VALUE = new HashMap<>();
        SET_REGISTER_VALUE = new HashMap<>();
        DO_OPERATION = new HashMap<>();
        REGISTER_TO_INT = new HashMap<>();
        INT_TO_REGISTER = new HashMap<>();

        GET_REGISTER_VALUE.put(ACC,ACC_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(TMP,TMP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(SP,SP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(FP,FP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(AL,AL_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(RA,RA_REGISTER_VALUE);

        SET_REGISTER_VALUE.put(ACC, ACC_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(TMP, TMP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(SP, SP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(FP, FP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(AL, AL_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(RA, RA_REGISTER_VALUE_SET);

        DO_OPERATION.put(CVMParser.ADD, ADD_FUNCTION);
        DO_OPERATION.put(CVMParser.SUB, SUB_FUNCTION);
        DO_OPERATION.put(CVMParser.MULT, MULT_FUNCTION);
        DO_OPERATION.put(CVMParser.DIV, DIV_FUNCTION);

        REGISTER_TO_INT.put(ACC,0);
        REGISTER_TO_INT.put(TMP,1);
        REGISTER_TO_INT.put(SP, 2);
        REGISTER_TO_INT.put(FP, 3);
        REGISTER_TO_INT.put(AL, 4);
        REGISTER_TO_INT.put(RA, 5);

        INT_TO_REGISTER.put(0,ACC);
        INT_TO_REGISTER.put(1,TMP);
        INT_TO_REGISTER.put(2, SP);
        INT_TO_REGISTER.put(3, FP);
        INT_TO_REGISTER.put(4, AL);
        INT_TO_REGISTER.put(5, RA);
    }
}
