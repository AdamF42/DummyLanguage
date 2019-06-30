package interpretermodels;

import parser.CVMParser;

public class Pop extends ElementBase{
    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.POP;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
