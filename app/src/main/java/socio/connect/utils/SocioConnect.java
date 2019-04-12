package socio.connect.utils;

import android.app.Application;

import com.squareup.picasso.Picasso;

public class SocioConnect extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
