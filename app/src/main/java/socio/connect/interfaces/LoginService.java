package socio.connect.interfaces;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import socio.connect.models.LoginRequest;
import socio.connect.models.LoginResponse;

public interface LoginService {

    @POST("login?type=api")
    Call<LoginResponse> login(@Body LoginRequest request);
}
