package models.compiler.types;

public abstract class TypeReferenceable extends Type {

    private boolean reference = false;

    public boolean isReference() {
        return reference;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

}
