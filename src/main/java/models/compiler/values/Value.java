package models.compiler.values;


import models.compiler.expressions.Factor;

public abstract class Value extends Factor {

    private final String val;

    public Value(String val) {
        super(null,null);
        this.val=val;
    }

    public String getVal() {
        return val;
    }



}
