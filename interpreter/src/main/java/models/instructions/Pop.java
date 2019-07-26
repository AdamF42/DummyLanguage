package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

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
