package models;


public abstract class Type extends ElementBase {
    private boolean deleted = false;
    private boolean toBeDeleted = false;

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

}