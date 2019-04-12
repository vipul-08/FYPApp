package socio.connect.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import socio.connect.R;
import socio.connect.adapters.PostDisplayAdapter;
import socio.connect.interfaces.LoginService;
import socio.connect.interfaces.PostsFinderService;
import socio.connect.models.LoadPostModel;
import socio.connect.models.LoginRequest;
import socio.connect.models.LoginResponse;
import socio.connect.models.PostsRequest;
import socio.connect.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<LoadPostModel> list = new ArrayList<>();

    PostDisplayAdapter adapter;
    RecyclerView postLists;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = container.getContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        adapter = new PostDisplayAdapter(rootView.getContext(),list);

        postLists = rootView.findViewById(R.id.postsAllFriends);

        postLists.setLayoutManager(new LinearLayoutManager(container.getContext()));
        postLists.setAdapter(adapter);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:4500/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        PostsFinderService service = retrofit.create(PostsFinderService.class);

        Call<JsonObject> call = service.loadPosts(new PostsRequest(user.get_id()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonArray jsonArray = response.body().get("posts").getAsJsonArray();
                    for(int i = 0 ; i < jsonArray.size() ; i++) {
                        Gson g = new Gson();
                        String p = jsonArray.get(i).toString();
                        LoadPostModel model = g.fromJson(p,LoadPostModel.class);
                        list.add(model);
                        adapter.notifyDataSetChanged();
                    }

                }
                else {
                    Log.d("RESP: ","Something Wrong!!!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("RESP: ","Error: "+t.toString());
            }
        });

        return rootView;
    }

}
