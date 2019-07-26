package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class BranchGreater extends ElementBase {

    private final String register1;
    private final String register2;
    private final String label;

    public BranchGreater(String register1, String register2, String label) {
        this.register1 = register1;
        this.register2 = register2;
        this.label = label;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.BRANCHGREATER;
        env.code[env.i++] = REGISTER_TO_INT.get(register1);
        env.code[env.i++] = REGISTER_TO_INT.get(register2);
        env.getLabelRef().put(env.i++, label);
    }
}
