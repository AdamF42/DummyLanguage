package interpretermodels;

import parser.CVMParser;

public class Label extends ElementBase {
    private final String label;

    public Label(String label) {
        this.label = label;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.getLabelAdd().put(label,env.i);
       // env.code[env.i++] = CVMParser.LABEL;

    }
}
