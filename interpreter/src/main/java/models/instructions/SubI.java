package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class SubI extends ElementBase {

    private final String storeRegister;
    private final String register;
    private final String number;

    public SubI(String storeRegister, String register, String number) {
        this.storeRegister = storeRegister;
        this.register = register;
        this.number = number;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.SUBI;
        env.code[env.i++] = REGISTER_TO_INT.get(storeRegister);
        env.code[env.i++] = REGISTER_TO_INT.get(register);
        env.code[env.i++] = Integer.parseInt(number);
    }
}
