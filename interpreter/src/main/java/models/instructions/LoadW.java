package models.instructions;

import models.CodeMemory;
import models.ElementBase;
import interpreter.parser.CVMParser;

import static util.RegisterUtils.REGISTER_TO_INT;

public class LoadW extends ElementBase {

    private final String register;
    private final String offset;
    private final String deferiencedRegister;

    public LoadW(String register, String offset, String deferiencedRegister) {
        this.register = register;
        this.offset = offset;
        this.deferiencedRegister = deferiencedRegister;
    }


    @Override
    public void loadCode(CodeMemory env) {
        env.code[env.i++] = CVMParser.LOADW;
        env.code[env.i++] = REGISTER_TO_INT.get(register);
        env.code[env.i++] = Integer.parseInt(offset);
        env.code[env.i++] = REGISTER_TO_INT.get(deferiencedRegister);
    }

}
