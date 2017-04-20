package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.ExecutionException;

public class FirebaseHelper {

    public interface FirebaseCallback{
        void onSuccess(Object result);
    }

    public static FirebaseUser getCurrentUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser();
    }

    public static void getFirebaseUserDetail(final FirebaseCallback callback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.onSuccess(dataSnapshot.getValue(User.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public static void updateFirebaseUserMail(final String newMail, final FirebaseCallback callback) {
        FirebaseUser firebaseUser = getCurrentUser();
        firebaseUser.updateEmail(newMail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(true);
                    updateUserMail(newMail);
                }
                else
                    callback.onSuccess(false);
            }
        });
    }

    public static void updateUserMail(String newMail) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = getCurrentUser();

        firebaseDatabase.getReference()
                .child("users")
                .child(firebaseUser.getUid())
                .child("mail")
                .setValue(newMail);
    }

    public static void updateUserAvatar(String newAvatar) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = getCurrentUser();

        firebaseDatabase.getReference()
                .child("users")
                .child(firebaseUser.getUid())
                .child("avatar")
                .setValue(newAvatar);
    }

    public static void updateUserPassword(String oldPassword, final String newPassword, final FirebaseCallback callback) {
        final FirebaseUser firebaseUser = getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(firebaseUser.getEmail(), oldPassword);

        firebaseUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            callback.onSuccess(true);
                                        }
                                    });
                        } else {
                            callback.onSuccess(false);
                        }
                    }
                });
    }

    public static StorageReference getUserAvatarRef(String avatar) {
        return FirebaseStorage
                .getInstance()
                .getReference()
                .child("avatars")
                .child(avatar);
    }
}
