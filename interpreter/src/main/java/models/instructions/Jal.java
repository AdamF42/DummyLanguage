package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

public class Jal extends ElementBase {

    private final String label;

    public Jal(String label) {
        this.label = label;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.JAL;
        env.getLabelRef().put(env.i++, label);
    }
}
