package socio.connect.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import socio.connect.R;
import socio.connect.adapters.SearchAdapter;
import socio.connect.interfaces.LoginService;
import socio.connect.interfaces.SearchService;
import socio.connect.models.LoginRequest;
import socio.connect.models.LoginResponse;
import socio.connect.models.SearchRequest;
import socio.connect.models.SearchResponse;
import socio.connect.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText searchBox;
    RecyclerView userList;
    View rootView;

    ArrayList<User> list = new ArrayList<>();
    SearchAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        sharedPreferences = container.getContext().getSharedPreferences("user_session",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        searchBox = rootView.findViewById(R.id.searchBox);
        userList = rootView.findViewById(R.id.searchedUsersList);

        adapter = new SearchAdapter(list,rootView.getContext());

        userList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        userList.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= 3) {
                    search(s.toString());
                }
                else {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);



        return rootView;
    }

    private void search(String stringToSearch) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:4500/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        SearchService service = retrofit.create(SearchService.class);

        Call<SearchResponse> call = service.search(new SearchRequest(stringToSearch));
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    for(int i = 0 ; i < response.body().getUser().size() ; i++) {
                        String jsn = response.body().getUser().get(i).toString();
                        Gson gson = new Gson();
                        User user = gson.fromJson(jsn,User.class);
                        list.add(user);
                        adapter.notifyDataSetChanged();
                    }

                }
                else {
                    Toast.makeText(rootView.getContext(),"Some Error: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(rootView.getContext(),"Error: "+t.toString(),Toast.LENGTH_LONG).show();
                Log.d("Response: ","Error: "+t.toString());
            }
        });
    }

}
