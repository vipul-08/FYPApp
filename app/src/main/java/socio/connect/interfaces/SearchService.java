package socio.connect.interfaces;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import socio.connect.models.SearchRequest;
import socio.connect.models.SearchResponse;

public interface SearchService {
    @POST("search")
    Call<SearchResponse> search(@Body SearchRequest request);
}
