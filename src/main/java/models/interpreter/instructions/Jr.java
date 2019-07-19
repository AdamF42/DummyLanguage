package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

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
