package models.stentry;

import models.types.Type;


public class VarSTentry extends STentry {

    private final int offset;

    public VarSTentry(int nl, int offset, Type t, String id) {
        super(nl, t, id);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
