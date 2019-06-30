package interpretermodels;

public class Jr extends ElementBase {

    private final String register;


    public Jr(String label) {
        this.register = label;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.getLabelRef().put(env.i++, register);
    }
}
