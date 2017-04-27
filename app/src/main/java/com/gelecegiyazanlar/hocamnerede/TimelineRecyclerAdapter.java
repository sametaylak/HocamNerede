package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
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
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {
    static final int SECOND = 1000;
    static final int MINUTE = 60 * SECOND;
    static final int HOUR = 60 * MINUTE;
    static final int DAY = 24 * HOUR;

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

        holder.itemFullname.setText(locationPost.getUserFullname());
        holder.itemDescription.setText(locationPost.getUserDescription());
        holder.itemTimestamp.setText(getTimeAgo(locationPost.getTimestamp()));
        holder.itemMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+ locationPost.getUserLatitude() + "," + locationPost.getUserLongitude() + "?q=("+locationPost.getUserFullname()+")@"+locationPost.getUserLatitude()+","+locationPost.getUserLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    private String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = Math.abs(now - time);
        if (diff < MINUTE) {
            return "Şimdi";
        } else if (diff < 2 * MINUTE) {
            return "1 dakika önce";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " dakika önce";
        } else if (diff < 90 * MINUTE) {
            return "1 saat önce";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " saat önce";
        } else if (diff < 48 * HOUR) {
            return "Dün";
        } else {
            return diff / DAY + " gün önce";
        }
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
        CustomTextView itemTimestamp;



        public ViewHolder(View view) {
            super(view);
            itemMap = (ImageView)view.findViewById(R.id.itemMap);
            itemAvatar = (CircleImageView) view.findViewById(R.id.itemAvatar);
            itemFullname = (CustomTextView) view.findViewById(R.id.itemFullname);
            itemDescription = (CustomTextView)view.findViewById(R.id.itemDescription);
            itemTimestamp = (CustomTextView) view.findViewById(R.id.itemTimestamp);
        }

    }

}
