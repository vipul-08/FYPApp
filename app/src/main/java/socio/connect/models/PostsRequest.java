package socio.connect.models;

public class PostsRequest {
    private String cid;

    public PostsRequest() {
    }

    public PostsRequest(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
