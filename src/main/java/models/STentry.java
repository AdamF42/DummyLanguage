package models;


import java.util.Objects;

public class STentry {

    private final int nl;
    private final Type type;
    private final String id;

    /**
     * Constructor for STentry with type.
     *  @param nl --> entry nesting level
     * @param t  --> entry type
     * @param id
     */
    public STentry(int nl, Type t, String id) {
        this.nl = nl;
        this.type = t;
        this.id = id;
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
        STentry sTentry = (STentry) o;
        return nl == sTentry.nl &&
                Objects.equals(id, sTentry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nl, id);
    }
}