package com.gelecegiyazanlar.hocamnerede;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfile extends Fragment {
    private static final int PICK_PHOTO_FOR_AVATAR = 1;

    @BindView(R.id.userSettingAvatar) CircleImageView userSettingAvatar;
    @BindView(R.id.userSettingFullName) EditText userSettingFullName;
    @BindView(R.id.userSettingMail) EditText userSettingMail;
    @BindView(R.id.userSettingOldPassword) EditText userSettingOldPassword;
    @BindView(R.id.userSettingNewPassword) EditText userSettingNewPassword;

    private User user;

    public UserProfile () { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        FirebaseHelper.getFirebaseUserDetail(new FirebaseHelper.FirebaseCallback() {
            @Override
            public void onSuccess(Object result) {
                user = (User) result;
                userSettingFullName.setText(user.getFullname());
                userSettingMail.setText(user.getMail());

                if (user.getAvatar() != null) {
                    Glide.with(getActivity())
                            .using(new FirebaseImageLoader())
                            .load(FirebaseHelper.getUserAvatarRef(user.getAvatar()))
                            .into(userSettingAvatar);
                }
            }
        });


        return view;
    }

    @OnClick(R.id.userSettingSave)
    public void userSettingSave() {
        updateMail();
        updatePassword();
    }

    @OnClick(R.id.userSettingAvatar)
    public void onUserSettingAvatarClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            final Uri selectedImage = data.getData();

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference avatarRef = firebaseStorage
                    .getReference()
                    .child("avatars/" + selectedImage.getLastPathSegment());

            avatarRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    FirebaseHelper.updateUserAvatar(selectedImage.getLastPathSegment());
                    userSettingAvatar.setImageURI(selectedImage);
                }
            });
        }
    }

    private void updatePassword() {
        String oldPassword = userSettingOldPassword.getText().toString();
        String newPassword = userSettingNewPassword.getText().toString();
        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            FirebaseHelper.updateUserPassword(oldPassword, newPassword, new FirebaseHelper.FirebaseCallback() {
                @Override
                public void onSuccess(Object result) {
                    boolean status = (boolean)result;
                    if(status) Toast.makeText(getActivity(), "Şifre değiştirildi!", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getActivity(), "Şifre değiştirirken bir sorun meydana geldi!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void updateMail() {
        if(!userSettingMail.getText().toString().equals(user.getMail())) {
            FirebaseHelper.updateFirebaseUserMail(userSettingMail.getText().toString(),
                    new FirebaseHelper.FirebaseCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            boolean status = (boolean) result;
                            if (status)
                                Toast.makeText(getActivity(), "Mail değiştirildi.", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getActivity(), "Mail değiştirirken bir sorun meydana geldi.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

}
