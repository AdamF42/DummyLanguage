package compilermodels.stentry;

import compilermodels.types.Type;


public class VarStEntry extends StEntry {

    private final int offset;

    public VarStEntry(int nl, int offset, Type t, String id) {
        super(nl, t, id);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
