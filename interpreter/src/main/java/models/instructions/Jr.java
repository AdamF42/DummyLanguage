package models.instructions;

import models.ElementBase;
import models.CodeMemory;
import interpreter.parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class Jr extends ElementBase {

    private final String register;

    public Jr(String register) {
        this.register= register;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.JR;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
    }
}
