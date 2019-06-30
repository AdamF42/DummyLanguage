package interpretermodels;


import parser.CVMParser;
import static util.RegisterUtils.REGISTER_TO_INT;


public class Top extends ElementBase {

    private final String register;

    public Top(String register) {
        this.register = register;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.TOP;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
    }
}
