package models.stentry;


import models.types.Type;
import models.types.TypeReferenceable;
import java.util.Objects;


public abstract class StEntry {

    private final int nl;
    private final Type type;
    private final String id;
    private boolean deleted = false;
    private boolean toBeDeleted = false;

    public StEntry(int nl, Type t, String id) {
        this.nl = nl;
        this.type = t;
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isToBeDeletedOnFunCall() {
        return toBeDeleted;
    }

    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }

    public boolean isReference(){
        return (this.type != null && this.type instanceof TypeReferenceable) && ((TypeReferenceable) this.type).isReference();
    }

    public Type getType() {
        return this.type;
    }

    public int getNestinglevel() {
        return this.nl;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StEntry sTentry = (StEntry) o;
        return nl == sTentry.nl &&
                Objects.equals(id, sTentry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nl, id, type);
    }

}