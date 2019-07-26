package models.instructions;


import models.CodeMemory;
import models.ElementBase;

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
