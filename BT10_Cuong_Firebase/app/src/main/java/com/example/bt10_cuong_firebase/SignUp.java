package com.example.bt10_cuong_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsername, edtCFUsername, edtPassword;
    private Button btn_signup;
    private ImageView logofood;
    private TextView tv_loginnow;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        btn_signup = (Button) findViewById(R.id.bt_signup);
        btn_signup.setOnClickListener(this);

        logofood = (ImageView) findViewById(R.id.imageView1) ;
        logofood.setOnClickListener(this);

        edtUsername = (EditText) findViewById(R.id.tvusername);
        edtPassword = (EditText) findViewById(R.id.tvpassword);
        edtCFUsername = (EditText) findViewById(R.id.tvcfpassword);
        tv_loginnow =(TextView)findViewById(R.id.tv_login);
        tv_loginnow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.bt_signup:
                signupuser();
                break;
        }

    }
    private void signupuser(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String cfpassword = edtCFUsername.getText().toString().trim();

        if(username.isEmpty()){
            edtUsername.setError("User Name is requird");
            edtUsername.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            edtUsername.setError("Please provide valid user name");
            edtUsername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edtPassword.setError("Pass Word is requird");
            edtPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            edtPassword.setError("Min password length should be 6 characters!");
            edtPassword.requestFocus();
            return;
        }
        if(cfpassword.isEmpty()){
            edtCFUsername.setError("Confirm Pass Word is requird");
            edtCFUsername.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}