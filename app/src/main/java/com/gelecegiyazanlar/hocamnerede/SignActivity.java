package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.signInConfirm)
    public void signInConfirm() {
        String mail = signInMailLayout.getEditText().getText().toString();
        String password = signInPasswordLayout.getEditText().getText().toString();

        Toast.makeText(this, "Mail : " + mail + "\nPassword : " + password, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.signUpConfirm)
    public void signUpConfirm() {
        String fullname = signUpNameLayout.getEditText().getText().toString();
        String mail = signUpMailLayout.getEditText().getText().toString();
        String password = signUpPasswordLayout.getEditText().getText().toString();
        String university = signUpUniversity.getSelectedItem().toString();
        String role = ((RadioButton)findViewById(signUpRole.getCheckedRadioButtonId())).getText().toString();

        Toast.makeText(this, "Fullname : " + fullname + "\n" +
                "Mail : " + mail + "\n" +
                "Password : " + password + "\n" +
                "University : " + university + "\n" +
                "Role : " + role
                , Toast.LENGTH_LONG).show();
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
}
