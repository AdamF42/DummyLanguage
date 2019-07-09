package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class LoadI extends ElementBase {

    private final String register;
    private final String number;

    public LoadI(String register, String number) {
        this.register = register;
        this.number = number;
    }

    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.LOADI;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
        env.code[env.i++] = Integer.parseInt(number);
    }

    public String getRegister() {
        return register;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+" "+ register +" " + number + "\n";
    }
}
