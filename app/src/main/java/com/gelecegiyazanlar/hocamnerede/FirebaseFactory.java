package com.gelecegiyazanlar.hocamnerede;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseFactory {

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
                //TODO: sıkıntılı
                callback.onSuccess(newMail);
                updateUserMail(newMail);
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

}
