package models.interpreter.instructions;


import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;

public class Label extends ElementBase {
    private final String label;

    public Label(String label) {
        this.label = label;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.getLabelAdd().put(label,env.i);
    }
}
