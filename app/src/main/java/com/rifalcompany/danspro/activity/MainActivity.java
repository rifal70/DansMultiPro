package com.rifalcompany.danspro.activity;

//     token openssl   3ZLW/TAqPvR43Zh79ejFQDOdka8=

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rifalcompany.danspro.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginFb;
    Button login;
    SignInButton loginGmail;
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);

        // Mengambil objek SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Mengambil nilai
        String id = sharedPreferences.getString("id_account", "");
        String name = sharedPreferences.getString("name_account", "Hello Name");

        if (!id.equals("") && !name.equals("")) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        /*Alternative*/
        login.setOnClickListener(v -> {
            // Menyimpan nilai
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id_account", "12345");
            editor.putString("name_account", "Alternative");
            editor.apply();

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
            /*Alternative*/
        });


        /*Facebook*/
        loginFb = findViewById(R.id.btn_fb);
        callbackManager = CallbackManager.Factory.create();
        loginFb.setReadPermissions(Arrays.asList(EMAIL,NAME));

        // Callback registration
        loginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Menyimpan nilai
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_account", String.valueOf(loginFb.getId()));
                editor.putString("name_account", String.valueOf(loginFb.getTransitionName()));
                editor.apply();

                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this, "" + exception, Toast.LENGTH_SHORT).show();
            }
        });
        /*Facebook*/


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        loginGmail = findViewById(R.id.btn_gmail);
        loginGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginGoogle();
            }
        });
    }


    /*GMAIL*/
    private void LoginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (!account.getId().equals("") && !account.getId().equals(null)) {
                // Mengambil objek SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

                // Menyimpan nilai
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_account", account.getId());
                editor.putString("name_account", account.getDisplayName());
                editor.apply();

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Login Gagal " + account, Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.d("error", "" + e);
            Toast.makeText(this, "Login Gagal " + e, Toast.LENGTH_SHORT).show();
        }
    }
    /*GMAIL*/
}