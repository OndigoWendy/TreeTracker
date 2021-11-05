package com.moringaschool.treecordbook.trees.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.treecordbook.R;
import com.moringaschool.treecordbook.trees.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.passwordLoginButton)
    Button passwordLoginButton;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.registerTextView)
    TextView mRegisterTextView;
    @BindView(R.id.firebaseProgressBar)
    ProgressBar signInProgressBar;
    @BindView(R.id.loadingTextView) TextView loadingSignUp;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        mRegisterTextView.setOnClickListener(this);
        passwordLoginButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        if (view==mRegisterTextView){
            Intent intent = new Intent(LoginActivity.this, UserAccountActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == passwordLoginButton){
            loginWithPassword();
            showProgressBar();
        }
    }

    private void showProgressBar() {
        signInProgressBar.setVisibility(View.VISIBLE);
        loadingSignUp.setVisibility(View.VISIBLE);
//        authProgressDialog.setMessage("Authenticating with Firebase...");
        loadingSignUp.setText("Log in ");
    }
    private void hideProgressBar(){
        signInProgressBar.setVisibility(View.GONE);
        loadingSignUp.setVisibility(View.GONE);
    }

    private void loginWithPassword() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (email.equals("")){
            emailEditText.setError("Please enter your email");
            return;
        }
        if (password.equals("")){
            passwordEditText.setError("Password cannot be blank");
            return;
        }
//        authProgressDialog.show();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressBar();
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail",task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

}