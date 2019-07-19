package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class BranchGreaterEq extends ElementBase {

    private final String register1;
    private final String register2;
    private final String label;

    public BranchGreaterEq(String register1, String register2, String label) {
        this.register1 = register1;
        this.register2 = register2;
        this.label = label;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.BRANCHGREATEREQ;
        env.code[env.i++] = REGISTER_TO_INT.get(register1);
        env.code[env.i++] = REGISTER_TO_INT.get(register2);
        env.getLabelRef().put(env.i++, label);
    }
}
