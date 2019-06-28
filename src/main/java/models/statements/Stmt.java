package models.statements;

import models.ElementBase;
import models.stentry.StEntry;

import java.util.*;


public abstract class Stmt extends ElementBase {

    private final Set<StEntry> deletions = new HashSet<>();
    private final Set<StEntry> rwAccesses = new HashSet<>();


    public void addAllDeletions(Set<StEntry> deletions) {
        this.deletions.addAll(deletions);
    }

    public void addDeletion(StEntry deletion) {
        this.deletions.add(deletion);
    }

    public Set<StEntry> getDeletions() {
        return deletions;
    }

    public void addAllrwAccesses(Set<StEntry> deletions) {
        this.rwAccesses.addAll(deletions);
    }

    public void addrwAccess(StEntry deletion) {
        this.rwAccesses.add(deletion);
    }

    public Set<StEntry> getRwAccesses() {
        return rwAccesses;
    }

}
