package models.stentry;

import models.types.Type;

public class FunSTentry extends STentry {

    private final String f_label;

    public FunSTentry(int nl, Type t, String id, String f_label) {
        super(nl, t, id);
        this.f_label = f_label;
    }

    public String GetLabel(){return f_label;}

}
