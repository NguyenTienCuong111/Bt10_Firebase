package com.example.bt10_cuong_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    Button btn_save,btn_back;
    EditText edt_sinhvien,edt_masv,edt_address_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Anhxa();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserData();
                clearAll();

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,HomeActivity.class);

                startActivity(intent);

            }
        });
    }
    private void Anhxa() {
        btn_save=(Button) findViewById(R.id.btn_save);
        btn_back=(Button) findViewById(R.id.btn_back);

        edt_sinhvien = findViewById(R.id.edt_sinhvien);
        edt_masv = findViewById(R.id.edt_masv);
        edt_address_image = findViewById(R.id.edt_address_image);
    }
    private void inserData(){

        Map<String,Object> map = new HashMap<>();
        map.put("name",edt_sinhvien.getText().toString());
        map.put("masv",edt_masv.getText().toString());
        map.put("img",edt_address_image.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Sinhvien").push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Successfully completed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Failure addition",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private  void clearAll(){
        edt_sinhvien.setText("");
        edt_masv.setText("");
        edt_address_image.setText("");
    }
}