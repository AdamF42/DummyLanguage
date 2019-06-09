package models.values;


import models.expressions.Factor;

public abstract class Value extends Factor {



    private String val;

    public Value(String val) {
        super(null,null, null);
        this.val=val;
    }
    public String getVal() {
        return val;
    }

}
