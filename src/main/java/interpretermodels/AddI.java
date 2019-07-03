package interpretermodels;

import parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class AddI extends ElementBase  {

    private final String storeRegister;
    private final String register;
    private final String number;

    public AddI(String storeRegister, String register, String number) {
        this.storeRegister = storeRegister;
        this.register = register;
        this.number = number;
    }

    @Override
    public void loadCode(InterpreterEnv env) {
        env.code[env.i++] = CVMParser.ADDI;
        env.code[env.i++] = REGISTER_TO_INT.get(storeRegister);
        env.code[env.i++] = REGISTER_TO_INT.get(register);
        env.code[env.i++] = Integer.parseInt(number);
    }
}
