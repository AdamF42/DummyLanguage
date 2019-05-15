package models;


public class Factor extends Term {

    private final String op;

    public Factor(Exp left, Exp right, String op) {
        super(left, right);
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        Type result = super.typeCheck();
        if (op != null) {
            result = new TypeBool();
        }
        return result;
    }
}
