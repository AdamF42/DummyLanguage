package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class StoreW extends ElementBase {

    private final String register;
    private final String offset;
    private final String deferiencedRegister;

    public StoreW(String register, String offset, String deferiencedRegister) {
        this.register = register;
        this.offset = offset;
        this.deferiencedRegister = deferiencedRegister;
    }


    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.STOREW;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
        env.code[env.i++] = Integer.parseInt(offset);
        env.code[env.i++] = REGISTER_TO_INT.get(deferiencedRegister);
    }

    public String getRegister() {
        return register;
    }

    public String getOffset() {
        return offset;
    }

    public String getDeferiencedRegister() {
        return deferiencedRegister;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+" "+ register +" " + offset + " " + deferiencedRegister + "\n";
    }
}
