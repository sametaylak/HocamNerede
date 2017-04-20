package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.gelecegiyazanlar.hocamnerede.model.LocationPost;
import com.gelecegiyazanlar.hocamnerede.views.CustomTextView;
import com.mypopsy.maps.StaticMap;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {

    private List<LocationPost> postList;
    private Context context;

    public TimelineRecyclerAdapter(Context context, List<LocationPost> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationPost locationPost = postList.get(position);

        StaticMap map = new StaticMap()
                .center(locationPost.getUserLatitude(), locationPost.getUserLongitude())
                .marker(locationPost.getUserLatitude(), locationPost.getUserLongitude())
                .zoom(15)
                .size(320,240);

        try {
            Glide.with(context)
                    .load(map.toURL())
                    .centerCrop()
                    .into(holder.itemMap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (locationPost.getUserAvatar() != null) {
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseHelper.getUserAvatarRef(locationPost.getUserAvatar()))
                    .into(holder.itemAvatar);
        }

        holder.itemDescription.setText(locationPost.getUserDescription());
        holder.itemFullname.setText(locationPost.getUserFullname());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemMap;
        CircleImageView itemAvatar;
        CustomTextView itemFullname;
        CustomTextView itemDescription;

        public ViewHolder(View view) {
            super(view);
            itemMap = (ImageView)view.findViewById(R.id.itemMap);
            itemAvatar = (CircleImageView) view.findViewById(R.id.itemAvatar);
            itemFullname = (CustomTextView) view.findViewById(R.id.itemFullname);
            itemDescription = (CustomTextView)view.findViewById(R.id.itemDescription);
        }

    }

}
