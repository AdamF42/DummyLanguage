package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

public class Pop extends ElementBase {
    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.POP;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
