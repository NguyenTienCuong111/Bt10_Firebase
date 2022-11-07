package com.example.bt10_cuong_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signup;
    private EditText editTextUsername, editTextPassword;
    private Button bt_login;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signup = (TextView) findViewById(R.id.tv_signup);
        signup.setOnClickListener(this);

        bt_login = (Button) findViewById(R.id.bt_Login);
        bt_login.setOnClickListener(this);

        editTextUsername = (EditText) findViewById(R.id.edtUsername);
        editTextPassword =(EditText) findViewById(R.id.edtPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signup:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.bt_Login:
                userlogin();
                break;

        }


    }

    private void userlogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextUsername.setError("User Name is requird");
            editTextUsername.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            editTextUsername.setError("Please enter a valid user name");
            editTextUsername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Pass Word is requird");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));


                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}