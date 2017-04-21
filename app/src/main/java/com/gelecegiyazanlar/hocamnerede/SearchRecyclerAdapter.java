package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.gelecegiyazanlar.hocamnerede.views.CustomTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS-PC on 21.4.2017.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>{



    public List<UserTeacher> teacherList;
    private Context context;

    public double latitude,longtitude;

    public SearchRecyclerAdapter(Context context, List<UserTeacher> teacherList) {
        this.teacherList = teacherList;
        this.context = context;
    }



    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        return new SearchRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder holder, int position) {



        final UserTeacher userTeacher = teacherList.get(position);

        /*
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
        */

        if (userTeacher.getUserAvatar() != null) {
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseHelper.getUserAvatarRef(userTeacher.getUserAvatar()))
                    .into(holder.itemAvatar);
        }

        holder.itemDescription.setText(userTeacher.getUserDescription());
        holder.itemFullname.setText(userTeacher.getUserFullname());


        //Burada card view tıklandıgını control et

        /*
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
        */
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView itemAvatar;
        CustomTextView itemFullname;
        CustomTextView itemDescription;
        public ViewHolder(View view) {
            super(view);
            itemAvatar = (CircleImageView) view.findViewById(R.id.itemAvatar);
            itemFullname = (CustomTextView) view.findViewById(R.id.itemFullname);
            itemDescription = (CustomTextView)view.findViewById(R.id.itemDescription);


        }

    }
}
