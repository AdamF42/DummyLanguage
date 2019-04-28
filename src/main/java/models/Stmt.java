package models;

import java.util.*;

/**
 * Represents language statements
 * Some children of this class are SimpleAssignment, SimplePrint, SimpleDelete and SimpleBlock
 *
 * @author Abel
 */
public abstract class Stmt extends ElementBase {

    private final Set<STentry> deletions = new HashSet<>();
    private final Set<STentry> rwAccesses = new HashSet<>();


    public void addAllDeletions(Set<STentry> deletions) {
        this.deletions.addAll(deletions);
    }

    public void addDeletion(STentry deletion) {
        this.deletions.add(deletion);
    }

    public Set<STentry> getDeletions() {
        return deletions;
    }

    public void addAllrwAccesses(Set<STentry> deletions) {
        this.rwAccesses.addAll(deletions);
    }

    public void addrwAccess(STentry deletion) {
        this.rwAccesses.add(deletion);
    }

    public Set<STentry> getRwAccesses() {
        return rwAccesses;
    }

}
