package interpretermodels;

public class Jal extends ElementBase {

    private final String label;

    public Jal(String label) {
        this.label = label;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.getLabelRef().put(env.i++, label);
    }
}
