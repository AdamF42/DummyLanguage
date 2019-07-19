package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

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
