package bank;

public abstract class AbstractAccount implements Closable {
    private final String name;
    private boolean closed;

    protected AbstractAccount(String name) {
        this.name = name;
        closed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() {
        closed = true;
    }
}
