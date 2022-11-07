package com.example.bt10_cuong_firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SinhVienAdapter sinhVienAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView =(RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SinhVienModel> options =
                new FirebaseRecyclerOptions.Builder<SinhVienModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Sinhvien"), SinhVienModel.class)
                        .build();

        sinhVienAdapter=new SinhVienAdapter(options);
        recyclerView.setAdapter(sinhVienAdapter);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),AddActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        sinhVienAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        sinhVienAdapter.stopListening();
    }
}