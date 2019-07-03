package models.compiler.stentry;

import models.compiler.types.Type;

public class FunStEntry extends StEntry {

    private final String f_label;

    public FunStEntry(int nl, Type t, String id, String f_label) {
        super(nl, t, id);
        this.f_label = f_label;
    }

    public String GetLabel(){return f_label;}

}
