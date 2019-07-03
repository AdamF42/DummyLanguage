package interpretermodels;

import parser.CVMParser;
import static util.RegisterUtils.REGISTER_TO_INT;

public class Push extends ElementBase {

    private final String register;

    public Push(String register) {
        this.register = register;
    }

    public String getRegister() {
        return register;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.PUSH;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+" "+ register + "\n";
    }
}
