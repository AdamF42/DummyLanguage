package interpretermodels;

import parser.CVMParser;

public class Print extends ElementBase {

    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.PRINT;
    }
}
