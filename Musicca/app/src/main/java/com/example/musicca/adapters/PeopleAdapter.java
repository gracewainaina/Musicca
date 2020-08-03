package com.example.musicca.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> users;

    public PeopleAdapter(Context context, List<ParseUser> users) {
        this.context = context;
        this.users = users;
        Log.i("PEOPLE ADAPTER", "ADAPTER created ");
        Log.i("PEOPLE ADAPTER", "ADAPTER users: " + this.users.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.ViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivProfileImage;
        private TextView tvMusicBio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvMusicBio = itemView.findViewById(R.id.tvMusicBio);
        }

        public void bind(ParseUser parseUser) {
            tvUsername.setText(parseUser.getUsername());
            tvMusicBio.setText(parseUser.getString("musicbio"));
            ParseFile profileImage = parseUser.getParseFile("profileimage");
            if (profileImage != null) {
                Glide.with(context).load(profileImage.getUrl()).into(ivProfileImage);
            }

        }
    }
}

