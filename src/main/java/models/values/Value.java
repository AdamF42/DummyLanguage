package models.values;


import models.expressions.Factor;
import static util.Strings.*;

public abstract class Value extends Factor {

    private String val;

    public Value(String val) {
        super(null,null, null);
        this.val=val;
    }
    public String getVal() {
        return val;
    }

    @Override
    public String codeGeneration() {
        return loadI(ACC,val);
    }

}
