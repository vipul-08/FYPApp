package socio.connect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import socio.connect.R;
import socio.connect.fragments.AddPostFragment;
import socio.connect.fragments.HomeFragment;
import socio.connect.fragments.NotificationsFragment;
import socio.connect.fragments.ProfileFragment;
import socio.connect.fragments.SearchFragment;
import socio.connect.models.User;

public class MainActivity extends AppCompatActivity {

//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_search:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.nav_addPost:
                        selectedFragment = new AddPostFragment();
                        break;
                    case R.id.nav_notifications:
                        selectedFragment = new NotificationsFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                if(selectedFragment!=null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selectedFragment).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new HomeFragment()).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new HomeFragment()).commit();

//        sharedPreferences = getSharedPreferences("user_session",MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//
//
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("user", "");
//        User user = gson.fromJson(json, User.class);

    }
}
