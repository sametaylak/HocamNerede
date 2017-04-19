package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.loginPanel) ViewGroup loginPanel;
    @BindView(R.id.registerPanel) ViewGroup registerPanel;
    @BindView(R.id.passwordResetPanel) ViewGroup passwordResetPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ButterKnife.bind(this);
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
