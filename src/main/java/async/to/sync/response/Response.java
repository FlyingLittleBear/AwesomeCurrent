package async.to.sync.response;

public class Response {

    private boolean dirty;

    private String data;

    public boolean receivedData() {
        return dirty;
    }

    public String getData() {
        return data;
    }

    public synchronized void setData(String data) {
        this.data = data;
        this.dirty = true;
        this.notifyAll();
    }
}
