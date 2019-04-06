package socio.connect.activities;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import socio.connect.R;
import socio.connect.interfaces.RegisterService;
import socio.connect.models.RegisterResponse;

public class RegisterActivity extends AppCompatActivity {

    EditText registerFullName,registerUserName,registerEmail,registerPassword,registerDob;
    ImageView registerImageUpload;
    TextView registerFileName,registerLoginNowBtn;
    Button registerSubmit;

    RadioButton registerMale,registerFemale;

    File imageFile;
    Uri imageUri;
    boolean imageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFullName = findViewById(R.id.registerFullName);
        registerUserName = findViewById(R.id.registerUserName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerDob = findViewById(R.id.registerDob);
        registerImageUpload = findViewById(R.id.registerImageUpload);
        registerFileName = findViewById(R.id.registerFileName);
        registerLoginNowBtn = findViewById(R.id.registerLoginNowBtn);
        registerSubmit = findViewById(R.id.registerSubmit);
        registerMale = findViewById(R.id.registerMale);
        registerFemale = findViewById(R.id.registerFemale);

        registerDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        Log.d("YEAH1",registerFullName.getText().toString());
        Log.d("YEAH2",registerEmail.getText().toString());
        Log.d("YEAH3",registerUserName.getText().toString());
        Log.d("YEAH4",registerPassword.getText().toString());
        Log.d("YEAH5",registerDob.getText().toString());


        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(registerFullName.getText())||TextUtils.isEmpty(registerEmail.getText())||TextUtils.isEmpty(registerUserName.getText())||TextUtils.isEmpty(registerPassword.getText())||TextUtils.isEmpty(registerDob.getText())||imageSelected == false) {
                    Toast.makeText(RegisterActivity.this,"Please fill all the fields!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(registerFullName.getText().toString(),registerEmail.getText().toString(),registerUserName.getText().toString(),registerPassword.getText().toString(),registerDob.getText().toString());
                }
            }
        });

        registerImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });

        registerLoginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String rfName, String rEmail, String ruName, String rpwd, String rDob) {

        RequestBody firstNamePart = RequestBody.create(MultipartBody.FORM,rfName);
        RequestBody emailPart = RequestBody.create(MultipartBody.FORM,rEmail);
        RequestBody userNamePart = RequestBody.create(MultipartBody.FORM,ruName);
        RequestBody passwordPart = RequestBody.create(MultipartBody.FORM,rpwd);
        RequestBody dobPart = RequestBody.create(MultipartBody.FORM,rDob);
        RequestBody genderPart = RequestBody.create(MultipartBody.FORM,registerMale.isChecked() ? "M" : "F");

        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)),imageFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",imageFile.getName(),filePart);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:4500/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        RegisterService service = retrofit.create(RegisterService.class);

        Call<RegisterResponse> call = service.register(firstNamePart,userNamePart,emailPart,passwordPart,dobPart,genderPart,part);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Something Went Wrong!!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Failed"+"\n"+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("MY ERROR","Error: "+t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK) {
            if(data == null) {
                Toast.makeText(RegisterActivity.this, "Unable to choose file!!", Toast.LENGTH_SHORT).show();
                return;
            }
            imageUri = data.getData();
            imageFile = new File(getRealFilePath(data.getData()));
            imageSelected = true;
            registerFileName.setText(imageFile.getName());
        }
    }

    private void showDateDialog() {
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                registerDob.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
            }
        };
        DatePickerDialog dpDialog=new DatePickerDialog(RegisterActivity.this, listener, 1997, 0, 1);
        dpDialog.show();
    }

    private String getRealFilePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(col_index);
        cursor.close();
        return result;
    }

}
