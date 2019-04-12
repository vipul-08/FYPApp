package socio.connect.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import socio.connect.R;
import socio.connect.models.User;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    List<User> list;
    Context context;

    public SearchAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_user_entry,viewGroup,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder searchViewHolder, int i) {
        final User user = list.get(i);
        searchViewHolder.userName.setText(user.getFirstName());
        searchViewHolder.userUserName.setText(user.getUserName());
        Picasso.get()
                .load("http://192.168.0.104:4500/uploads/profile_pic/"+user.getProfilePic())
                .placeholder(R.drawable.ic_profile)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(searchViewHolder.userImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        // successful
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load("http://192.168.0.104:4500/uploads/profile_pic/"+user.getProfilePic())
                                .placeholder(R.drawable.ic_profile)
                                .into(searchViewHolder.userImage);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView userName;
        TextView userUserName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.single_user_pic);
            userName = itemView.findViewById(R.id.single_user_name);
            userUserName = itemView.findViewById(R.id.single_user_username);
        }
    }
}
