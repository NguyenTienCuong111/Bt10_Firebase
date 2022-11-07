package com.example.bt10_cuong_firebase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinhVienAdapter extends FirebaseRecyclerAdapter<SinhVienModel,SinhVienAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SinhVienAdapter(@NonNull FirebaseRecyclerOptions<SinhVienModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull SinhVienModel model) {
        holder.name.setText(model.getName());
        holder.masv.setText(model.getMasv());

        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_sinhvien))
                        .setExpanded(true, 1300)
                        .create();
                View view = dialogPlus.getHolderView();
                EditText sinhvien = view.findViewById(R.id.edt_sinhvien);
                EditText masv    = view.findViewById(R.id.edt_masv);
                EditText img   = view.findViewById(R.id.edt_address_image);

                Button btn_update = view.findViewById(R.id.btn_update);
                sinhvien.setText(model.getName());
                masv.setText(model.getMasv());
                img.setText(model.getImg());
                dialogPlus.show();

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",sinhvien.getText().toString());
                        map.put("masv",masv.getText().toString());
                        map.put("img",img.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Sinhvien")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Succesfully Updated ",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(),"Updated Failur2 ",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });




            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Remove");
                builder.setMessage("Deleted Data can't be recovered!");
                // xoa
                builder.setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Sinhvien")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(),"Removed was not done",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sinhvien_item, parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name, masv;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name=(TextView) itemView.findViewById(R.id.nametext);
            masv=(TextView) itemView.findViewById(R.id.masvtext);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }
}
