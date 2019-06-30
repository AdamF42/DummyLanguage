package interpretermodels;

import parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class BranchEq extends ElementBase {

    private final String register1;
    private final String register2;
    private final String label;

    public BranchEq(String register1, String register2, String label) {
        this.register1 = register1;
        this.register2 = register2;
        this.label = label;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.BRANCHEQ;
        env.code[env.i++] = REGISTER_TO_INT.get(register1);
        env.code[env.i++] = REGISTER_TO_INT.get(register2);
        env.getLabelRef().put(env.i++, label);
    }
}
