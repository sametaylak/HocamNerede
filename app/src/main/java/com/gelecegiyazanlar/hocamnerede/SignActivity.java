package com.gelecegiyazanlar.hocamnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gelecegiyazanlar.hocamnerede.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    @BindView(R.id.loginPanel) ViewGroup loginPanel;
    @BindView(R.id.registerPanel) ViewGroup registerPanel;
    @BindView(R.id.passwordResetPanel) ViewGroup passwordResetPanel;

    @BindView(R.id.signInMailLayout) TextInputLayout signInMailLayout;
    @BindView(R.id.signInPasswordLayout) TextInputLayout signInPasswordLayout;

    @BindView(R.id.signUpNameLayout) TextInputLayout signUpNameLayout;
    @BindView(R.id.signUpMailLayout) TextInputLayout signUpMailLayout;
    @BindView(R.id.signUpPasswordLayout) TextInputLayout signUpPasswordLayout;
    @BindView(R.id.signUpUniversity) Spinner signUpUniversity;
    @BindView(R.id.signUpRole) RadioGroup signUpRole;

    @BindView(R.id.passwordResetMailLayout) TextInputLayout passwordResetMailLayout;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDatabaseReference;

    public static Intent newIntent(Activity callerActivity){
        return new Intent(callerActivity, SignActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDatabaseReference = firebaseDatabase.getReference().child("users");
    }

    @OnClick(R.id.signInConfirm)
    public void signInConfirm() {
        String mail = signInMailLayout.getEditText().getText().toString();
        String password = signInPasswordLayout.getEditText().getText().toString();

        final MaterialDialog signInProgress = new MaterialDialog.Builder(this)
                .title("Giriş")
                .content("Lütfen bekleyin...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false)
                .show();
        firebaseAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        signInProgress.dismiss();
                        if(task.isSuccessful()) {
                            Intent intent = MainActivity.newIntent(SignActivity.this);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(SignActivity.this, "Lütfen bilgilerinizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @OnClick(R.id.signUpConfirm)
    public void signUpConfirm() {
        if (validateSignUp()) {
            final String fullname = signUpNameLayout.getEditText().getText().toString();
            final String mail = signUpMailLayout.getEditText().getText().toString();
            final String password = signUpPasswordLayout.getEditText().getText().toString();
            final String university = signUpUniversity.getSelectedItem().toString();
            final String role = ((RadioButton)findViewById(signUpRole.getCheckedRadioButtonId())).getText().toString();

            final MaterialDialog signUpProgress = new MaterialDialog.Builder(this)
                    .title("Üyelik")
                    .content("Lütfen bekleyin...")
                    .progress(true, 0)
                    .progressIndeterminateStyle(true)
                    .cancelable(false)
                    .show();
            firebaseAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            signUpProgress.dismiss();
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                usersDatabaseReference.child(user.getUid()).setValue(new User(fullname,mail,university,role, null, FirebaseInstanceId.getInstance().getToken()));
                                Toast.makeText(SignActivity.this, "Başarıyla üye oldunuz!", Toast.LENGTH_LONG).show();

                                Intent intent = MainActivity.newIntent(SignActivity.this);
                                startActivity(intent);

                                finish();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.showRegisterView)
    public void showRegisterView() {
        loginPanel.setVisibility(View.INVISIBLE);
        registerPanel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.showLoginView)
    public void showLoginView() {
        registerPanel.setVisibility(View.INVISIBLE);
        loginPanel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.showLoginViewFromPasswordReset)
    public void showLoginViewFromPasswordReset() {
        passwordResetPanel.setVisibility(View.INVISIBLE);
        loginPanel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.showPasswordResetView)
    public void showPasswordResetView() {
        loginPanel.setVisibility(View.INVISIBLE);
        passwordResetPanel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.passwordResetConfirm)
    public void onPasswordResetConfirmClick() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(passwordResetMailLayout.getEditText().getText().toString());

        new MaterialDialog.Builder(this)
                .title("Başarılı!")
                .content("Parola sıfırlama maili gönderildi!")
                .contentGravity(GravityEnum.CENTER)
                .positiveText("Tamam")
                .iconRes(R.drawable.ic_check_circle)
                .show();

        passwordResetMailLayout.getEditText().getText().clear();
    }

    private boolean validateSignUp() {
        boolean valid = true;

        signUpNameLayout.setErrorEnabled(false);
        signUpMailLayout.setErrorEnabled(false);
        signUpPasswordLayout.setErrorEnabled(false);

        String name = signUpNameLayout.getEditText().getText().toString().trim();
        String mail = signUpMailLayout.getEditText().getText().toString().trim();
        String password = signUpPasswordLayout.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            valid = false;
            signUpNameLayout.setError("Bu alanın doldurulması gerekiyor!");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            valid = false;
            signUpMailLayout.setError("Geçersiz email adresi!");
        }
        if (password.length() < 6) {
            valid = false;
            signUpPasswordLayout.setError("Parola en az 6 karakterden oluşmalıdır!");
        }

        return valid;
    }
}
