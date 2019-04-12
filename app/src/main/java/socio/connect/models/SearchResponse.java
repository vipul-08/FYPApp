package socio.connect.models;

import com.google.gson.JsonArray;

import org.json.JSONArray;

public class SearchResponse {
    JsonArray user;

    public SearchResponse() {
    }

    public SearchResponse(JsonArray user) {
        this.user = user;
    }

    public JsonArray getUser() {
        return user;
    }

    public void setUser(JsonArray user) {
        this.user = user;
    }
}
