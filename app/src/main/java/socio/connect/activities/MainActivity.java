package socio.connect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import socio.connect.R;
import socio.connect.models.User;

public class MainActivity extends AppCompatActivity {

    TextView dummyText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        dummyText = findViewById(R.id.dummyText);
        sharedPreferences = getSharedPreferences("user_session",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);

        dummyText.setText(user.get_id()+"\n"+user.getFirstName()+"\n"+user.getEmail()+"\n"+user.getUserName()+"\n"+user.getPassword()+"\n"+user.getGender()+"\n"+user.getDateTimepicker());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
