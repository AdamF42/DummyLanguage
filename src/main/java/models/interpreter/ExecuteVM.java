package models.interpreter;


import parser.CVMParser;
import util.ExecutionException;

import java.util.ArrayList;
import java.util.List;

import static util.RegisterUtils.*;
import static util.Strings.ERROR_OUT_OF_MEMORY;
import static util.Strings.IP;

public class ExecuteVM {

    static final int CODE_SIZE = 1000;
    private static final int MEM_SIZE = 10000;

    private final int[] code;
    private final int[] memory = new int[MEM_SIZE];
    private int ip = 0;
    private int sp = MEM_SIZE;
    private int fp = MEM_SIZE;
    private int a0 = 0;
    private int al = 0;
    private int t1 = 0;
    private int ra = 0;

    private final List<Integer> printedResults = new ArrayList<>();

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() throws ExecutionException {
        while (true) {
            if(sp<=0) {
                throw new ExecutionException(ERROR_OUT_OF_MEMORY);
            }
            int bytecode = code[ip++]; 
            int v1, v2, offset;
            String r1, r2;
            switch (bytecode) {
                case CVMParser.PUSH:
                    push(GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this));
                    break;
                case CVMParser.POP:
                    pop();
                    break;
                case CVMParser.ADD:
                    executeOperationForOperator(CVMParser.ADD);
                    break;
                case CVMParser.SUB:
                    executeOperationForOperator(CVMParser.SUB);
                    break;
                case CVMParser.MULT:
                    executeOperationForOperator(CVMParser.MULT);
                    break;
                case CVMParser.DIV:
                    executeOperationForOperator(CVMParser.DIV);
                    break;
                case CVMParser.BRANCH:
                    ip = code[ip];
                    break;
                case CVMParser.BRANCHEQ:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v1 == v2) ip = code[ip++];
                    else ip++;
                    break;
                case CVMParser.BRANCHGREATER:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 > v1) ip = code[ip++];
                    else ip++;
                    break;
                case CVMParser.BRANCHGREATEREQ:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 >= v1) ip = code[ip++];
                    else ip++;
                    break;
                case CVMParser.BRANCHLESS:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v1 < v2) ip = code[ip++];
                    else ip++;
                    break;
                case CVMParser.BRANCHLESSEQ:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 <= v1) ip = code[ip++];
                    else ip++;
                    break;
                case CVMParser.STOREW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    memory[v1 + offset] = GET_REGISTER_VALUE.get(r1).apply(this);
                    break;
                case CVMParser.LOADW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this,memory[v1 + offset]);
                    break;
                case CVMParser.PRINT:
//                    System.out.println(a0);
                    printedResults.add(a0);
                    break;
                case CVMParser.MOVE:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this,v1);
                    break;
                case CVMParser.LOADI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this,v1);
                    break;
                case CVMParser.TOP:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    SET_REGISTER_VALUE.get(r1).apply(this,memory[sp]);
                    break;
                case CVMParser.JAL:
                    ra = ip+1;
                    SET_REGISTER_VALUE.get(IP).apply(this,code[ip]);
                    break;
                case CVMParser.JR:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r1).apply(this);
                    SET_REGISTER_VALUE.get(IP).apply(this,v1);
                    break;
                case CVMParser.ADDI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++]/4;
                    SET_REGISTER_VALUE.get(r1).apply(this,v2+v1);
                    break;
                case CVMParser.SUBI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++]/4;
                    SET_REGISTER_VALUE.get(r1).apply(this,v2-v1);
                    break;
                case CVMParser.HALT:
                    return;
            }
        }
    }

    private void executeOperationForOperator(int operator) {
       String r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
       int v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
       int v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
       int result = DO_OPERATION.get(operator).apply(v2,v1);
       SET_REGISTER_VALUE.get(r1).apply(this,result);
    }

    private void pop() {
        sp++;
    }

    private void push(int v) {
        memory[--sp] = v;
    }

    public List<Integer> getPrintedResults() {
        return printedResults;
    }

    public int getSp() {
        return sp;
    }

    public int setSp(int sp) {
        this.sp = sp;
        return sp;
    }

    public int getFp() {
        return fp;
    }

    public int setFp(int fp) {
        this.fp = fp;
        return fp;
    }

    public int getA0() {
        return a0;
    }

    public int setA0(int a0) {
        this.a0 = a0;
        return a0;
    }

    public int getAl() {
        return al;
    }

    public int setAl(int al) {
        this.al = al;
        return al;
    }

    public int getT1() {
        return t1;
    }

    public int setT1(int t1) {
        this.t1 = t1;
        return t1;
    }

    public int getRa() {
        return ra;
    }

    public int setRa(int ra) {
        this.ra = ra;
        return ra;
    }

    public int getIp() {
        return ip;
    }

    public int setIp(int ip) {
        this.ip = ip;
        return ip;
    }

}

