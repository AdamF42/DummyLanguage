package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

public class Print extends ElementBase {

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.PRINT;
    }
}
