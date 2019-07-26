package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

public class Print extends ElementBase {

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.PRINT;
    }
}
