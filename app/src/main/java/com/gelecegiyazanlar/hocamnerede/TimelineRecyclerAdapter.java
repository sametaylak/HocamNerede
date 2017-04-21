package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {

    private List<LocationPost> postList;
    private Context context;

    public double latitude,longtitude;

    public TimelineRecyclerAdapter(Context context, List<LocationPost> postList) {
        this.postList = postList;
        this.context = context;
    }

    public void setLatLong(Location location){
        latitude = location.getLatitude();
        longtitude = location.getLongitude();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        final LocationPost locationPost = postList.get(position);

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

        holder.itemMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String geoUriString="geo:"+locationPost.getUserLatitude()+","+locationPost.getUserLongitude()+"?q=("+locationPost.getUserFullname()+")@"+locationPost.getUserLatitude()+","+locationPost.getUserLongitude();

                Uri gmmIntentUri = Uri.parse(geoUriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                }
            }
        });
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
