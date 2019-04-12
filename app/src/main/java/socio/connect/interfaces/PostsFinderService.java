package socio.connect.interfaces;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import socio.connect.models.PostsRequest;

public interface PostsFinderService {

    @POST("load-friend-posts")
    Call<JsonObject> loadPosts(@Body PostsRequest postsRequest);

}
