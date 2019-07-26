package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

public class Branch extends ElementBase {

    private final String label;

    public Branch(String label) {
        this.label = label;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.BRANCH;
        env.getLabelRef().put(env.i++, label);
    }
}
