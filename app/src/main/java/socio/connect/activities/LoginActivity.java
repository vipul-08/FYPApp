package socio.connect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import socio.connect.R;
import socio.connect.interfaces.LoginService;
import socio.connect.models.LoginRequest;
import socio.connect.models.LoginResponse;
import socio.connect.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText loginUserName , loginPassword;
    Button loginSubmit;
    TextView loginRegisterNowBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);
        loginSubmit = findViewById(R.id.loginSubmit);
        loginRegisterNowBtn = findViewById(R.id.loginRegisterNowBtn);

        sharedPreferences = getSharedPreferences("user_session",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean("loggedIn",false)) {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        loginRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(loginUserName.getText()) || TextUtils.isEmpty(loginPassword.getText())) {
                    Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    login(loginUserName.getText().toString(),loginPassword.getText().toString());
                }
            }
        });

    }

    private void login(String uname, String pwd) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:4500/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        LoginService service = retrofit.create(LoginService.class);

        Call<LoginResponse> call = service.login(new LoginRequest(uname,pwd));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getUser());
                    editor.putBoolean("loggedIn",true);
                    editor.putString("user",json);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Invalid Credentials!!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
