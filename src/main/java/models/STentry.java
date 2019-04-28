package models;


public class STentry {

    private final int nl;
    private final Type type;

    /**
     * Constructor for STentry with type.
     *
     * @param nl --> entry nesting level
     * @param t  --> entry type
     */
    public STentry(int nl, Type t) {
        this.nl = nl;
        this.type = t;
    }

    public Type getType() {
        return this.type;
    }

    public int getNestinglevel() {
        return this.nl;
    }

}  