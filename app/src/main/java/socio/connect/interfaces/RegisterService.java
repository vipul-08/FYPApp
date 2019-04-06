package socio.connect.interfaces;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import socio.connect.models.RegisterResponse;

public interface RegisterService {
    @Multipart
    @POST("signup?type=api")
    Call<RegisterResponse> register(
            @Part("firstName") RequestBody firstName,
            @Part("userName") RequestBody userName,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("dateTimepicker") RequestBody dateTimepicker,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part photo
     );
}
