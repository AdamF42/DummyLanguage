package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

import java.util.List;

public class Assembly extends ElementBase {

    private final List<ElementBase> children;

    public Assembly(List<ElementBase> children) {
        this.children=children;
    }

    @Override
    public void loadCode(CodeMemory env) {

        for (ElementBase child: this.children) {
            child.loadCode(env);
        }

        for (Integer refAdd: env.getLabelRef().keySet()) {
            env.code[refAdd] = env.getLabelAdd().get(env.getLabelRef().get(refAdd));
        }

        env.code[env.i++] = CVMParser.HALT;

    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
