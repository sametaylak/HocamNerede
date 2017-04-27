package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.gelecegiyazanlar.hocamnerede.model.User;
import com.gelecegiyazanlar.hocamnerede.views.CustomTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public UserRecyclerAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = userList.get(position);

        if (user.getAvatar() != null) {
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseHelper.getUserAvatarRef(user.getAvatar()))
                    .into(holder.itemAvatar);
        }


       
        holder.itemFullname.setText(user.getFullname());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView itemAvatar;
        CustomTextView itemFullname;

        public ViewHolder(View view) {
            super(view);
            itemAvatar = (CircleImageView) view.findViewById(R.id.itemAvatar);
            itemFullname = (CustomTextView) view.findViewById(R.id.itemFullname);
        }

    }

}
