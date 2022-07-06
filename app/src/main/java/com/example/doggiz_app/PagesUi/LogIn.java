package com.example.doggiz_app.PagesUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggiz_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LogIn extends AppCompatActivity {

    EditText  mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegisterBtn;

    public static String email;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mEmail       = (EditText) findViewById(R.id.loginTextEmail);
        mPassword    = (EditText) findViewById(R.id.loginTextPassword);
        mLoginBtn    = (Button)   findViewById(R.id.loginBtn);
        mRegisterBtn = (TextView) findViewById(R.id.createNew);



        fAuth        =  FirebaseAuth.getInstance();
        progressBar  = (ProgressBar)  findViewById(R.id.loginProgressBar);



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString().trim().toLowerCase(Locale.ROOT);
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required!");
                    mEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required!");
                    mPassword.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be more then 6 Characters");
                    mPassword.requestFocus();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                if(email.equals("admin") && password.equals("admin1234")){
                    Toast.makeText(LogIn.this, "Welcome admin!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogIn.this, AdminPage.class));
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogIn.this, PersonProfile.class));
                                progressBar.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(LogIn.this, "Log in Error: Please try again" , Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                    //fAuth.getCurrentUser()
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,Register.class));
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}