package socio.connect.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import socio.connect.R;
import socio.connect.models.LoadPostModel;

public class PostDisplayAdapter extends RecyclerView.Adapter<PostDisplayAdapter.PostViewHolder> {

    Context context;
    List<LoadPostModel> list;

    public PostDisplayAdapter(Context context, List<LoadPostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_post,viewGroup,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i) {
        final LoadPostModel model = list.get(i);
        postViewHolder.uploaderName.setText(model.getPostedByName());
        Log.d("PREVIEW" , "http://192.168.0.104:4500/uploads/newfeeds_pic/"+model.getFile());

        Picasso.get()
                .load("http://192.168.0.104:4500/uploads/profile_pic/"+model.getPostedByPic())
                .placeholder(R.drawable.ic_profile)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(postViewHolder.uploaderThumb, new Callback() {
                    @Override
                    public void onSuccess() {
                        // successful
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load("http://192.168.0.104:4500/uploads/profile_pic/"+model.getPostedByPic())
                                .placeholder(R.drawable.ic_profile)
                                .into(postViewHolder.uploaderThumb);
                    }
                });

        Picasso.get()
                .load("http://192.168.0.104:4500/uploads/newfeeds_pic/"+model.getFile())
                .placeholder(R.drawable.ic_profile)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(postViewHolder.postedImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        // successful
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load("http://192.168.0.104:4500/uploads/newfeeds_pic/"+model.getFile())
                                .placeholder(R.drawable.ic_profile)
                                .into(postViewHolder.postedImage);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        CircleImageView uploaderThumb;
        TextView uploaderName;
        ImageView postedImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            uploaderThumb = itemView.findViewById(R.id.postUploaderThumb);
            uploaderName = itemView.findViewById(R.id.postUploaderName);
            postedImage = itemView.findViewById(R.id.postedImage);
        }
    }
}
