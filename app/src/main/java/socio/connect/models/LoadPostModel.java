package socio.connect.models;

import com.google.gson.JsonArray;

public class LoadPostModel {

    private String _id;
    private String postedBy;
    private String caption;
    private String file;
    private JsonArray comments;
    private String postedByPic;
    private String postedByName;
    private String postedByUserName;

    public LoadPostModel() {
    }

    public LoadPostModel(String _id, String postedBy, String caption, String file, JsonArray comments, String postedByPic, String postedByName, String postedByUserName) {
        this._id = _id;
        this.postedBy = postedBy;
        this.caption = caption;
        this.file = file;
        this.comments = comments;
        this.postedByPic = postedByPic;
        this.postedByName = postedByName;
        this.postedByUserName = postedByUserName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public JsonArray getComments() {
        return comments;
    }

    public void setComments(JsonArray comments) {
        this.comments = comments;
    }

    public String getPostedByPic() {
        return postedByPic;
    }

    public void setPostedByPic(String postedByPic) {
        this.postedByPic = postedByPic;
    }

    public String getPostedByName() {
        return postedByName;
    }

    public void setPostedByName(String postedByName) {
        this.postedByName = postedByName;
    }

    public String getPostedByUserName() {
        return postedByUserName;
    }

    public void setPostedByUserName(String postedByUserName) {
        this.postedByUserName = postedByUserName;
    }
}
