package duke.items;

public abstract class Item {
    private String name;
    private Boolean isDone;

    protected Item(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getDone() {
        return isDone;
    }

    protected void setDone(Boolean done) {
        isDone = done;
    }

    public void markDone() {
        this.setDone(true);
    }
}
