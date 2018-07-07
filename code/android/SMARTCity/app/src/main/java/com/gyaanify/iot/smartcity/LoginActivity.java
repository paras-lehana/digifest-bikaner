package com.gyaanify.iot.smartcity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class LoginActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    boolean firebase_login=FALSE;
    private static final String URL = "http://gyaanify.com/campfire_token.php";
    private static final String TAG = "buraahh";
    AlertDialog alertDialog;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-9968987511053896~5091636363");
        //AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in as "+user.getEmail()+" as admin",
                            Toast.LENGTH_SHORT).show();

                    Intent act_profile = new Intent(LoginActivity.this, com.gyaanify.iot.smartcity.StreetMap.class);
                    startActivity(act_profile);


                } else {
                    Toast.makeText(LoginActivity.this, "Sign In for Admin Access",Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };


    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();

        if (!validateEmail(username)) {
            UsernameEt.setError("Not a valid email address!");
        }
        else {

            //UsernameEt.setErrorEnabled(false);
            //PasswordEt.setErrorEnabled(false);

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Wrong",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                firebase_login = TRUE;

                            }

                        }

                    });

            if (firebase_login) {

                Intent act_profile = new Intent(LoginActivity.this, com.gyaanify.iot.smartcity.StreetMap.class);
                startActivity(act_profile);

            }
        }

    }

    public void OnOffline(View view) {

        String password = PasswordEt.getText().toString();
        if(password.contentEquals("smarttest")){

            Toast.makeText(LoginActivity.this, "Granted Offline Mode Access",
                    Toast.LENGTH_SHORT).show();

            Intent act_profile = new Intent(LoginActivity.this, com.gyaanify.iot.smartcity.StreetMap.class);
            startActivity(act_profile);
        }
        else{

            Toast.makeText(LoginActivity.this, "Enter correct Offline Mode password and then click",
                    Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }



    }


}

