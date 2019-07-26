package models.stentry;

import models.types.Type;
import java.util.HashSet;
import java.util.Set;

public class FunStEntry extends StEntry {

    private final String f_label;
    private Set<StEntry> rwAccesses = new HashSet<>();
    private Set<StEntry> deletions = new HashSet<>();


    public FunStEntry(int nl, Type t, String id, String f_label) {
        super(nl, t, id);
        this.f_label = f_label;
    }

    public void addAllDeletions(Set<StEntry> deletions) {
        this.deletions.addAll(deletions);
    }

    public Set<StEntry> getDeletions() {
        return deletions;
    }

    public void addAllrwAccesses(Set<StEntry> deletions) {
        this.rwAccesses.addAll(deletions);
    }

    public String GetLabel(){return f_label;}

    public Set<StEntry> getRwAccesses() {
        return rwAccesses;
    }
}
