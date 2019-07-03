package parser;


import java.util.ArrayList;
import java.util.List;

import static util.RegisterUtils.*;

public class ExecuteVM {

    public static final int CODESIZE = 1000;
    public static final int MEMSIZE = 50;

    private int[] code;
    private int[] memory = new int[MEMSIZE];

    private int ip = 0;
    private int sp = MEMSIZE-1;
    private int fp = MEMSIZE-1;
    private int a0 = 0;
    private int al = 0;
    private int t1 = 0;
    private int ra = 0;

    private List<Integer> printedResults = new ArrayList<>();

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

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            if(sp<=0) {
                System.out.println("\nError: Out of memory");
                return;
            }
            int bytecode = code[ip++]; 
            int v1, v2, offset;
            String r1, r2;
            switch (bytecode) {
                case CVMParser.PUSH:
                    push(GET_REGISTER_VALUE.get(INT_TO_REGISTER.get(code[ip++])).apply(this));
                    break;
                case CVMParser.POP:
                    pop();
                    break;
                case CVMParser.ADD:
                    ExecuteOperationForOperator(CVMParser.ADD);
                    break;
                case CVMParser.SUB:
                    ExecuteOperationForOperator(CVMParser.SUB);
                    break;
                case CVMParser.MULT:
                    ExecuteOperationForOperator(CVMParser.MULT);
                    break;
                case CVMParser.DIV:
                    ExecuteOperationForOperator(CVMParser.DIV);
                    break;
                case CVMParser.BRANCHEQ:
                    v1 = GET_REGISTER_VALUE.get(INT_TO_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_REGISTER.get(code[ip++])).apply(this);
                    if (v1 == v2) ip = code[ip];
                    else ip++;
                    break;
                case CVMParser.STOREW:
                    r1 = INT_TO_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    memory[v1 + offset] = GET_REGISTER_VALUE.get(r1).apply(this);
                    break;
                case CVMParser.LOADW:
                    r1 = INT_TO_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this,memory[v1 + offset]);
                    break;
                case CVMParser.PRINT:
                    //System.out.println(a0);
                    printedResults.add(a0);
                    break;
                case CVMParser.MOVE:
                    r1 = INT_TO_REGISTER.get(code[ip++]);
                    r2 = INT_TO_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this,v1);
                    break;
                case CVMParser.LOADI:
                    r1 = INT_TO_REGISTER.get(code[ip++]);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this,v1);
                    break;
                case CVMParser.TOP:
                    r1 = INT_TO_REGISTER.get(code[ip++]);
                    SET_REGISTER_VALUE.get(r1).apply(this,memory[sp]);
                    break;
                case CVMParser.LABEL:
                    break;
                case CVMParser.HALT:
                    return;
            }
        }
    }

    private void ExecuteOperationForOperator(int operator) {
       String r1 = INT_TO_REGISTER.get(code[ip++]);
       int v1 = GET_REGISTER_VALUE.get(INT_TO_REGISTER.get(code[ip++])).apply(this);
       int v2 = GET_REGISTER_VALUE.get(INT_TO_REGISTER.get(code[ip++])).apply(this);
       int result = DO_OPERATION.get(operator).apply(v1,v2);
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
}

