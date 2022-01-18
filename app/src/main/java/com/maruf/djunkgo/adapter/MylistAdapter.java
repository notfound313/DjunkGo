package com.maruf.djunkgo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.Item;
import com.maruf.djunkgo.MylistActivity;
import com.maruf.djunkgo.R;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.ResponseDasboard;
import com.maruf.djunkgo.model.ResponseMessage;
import com.maruf.djunkgo.model.ResponseMylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MylistAdapter extends RecyclerView.Adapter<MylistAdapter.ViewHolder> {
    private List<ResponseMylist>Mylist;
    private Activity activity;
    SessionManger sessionManger;

    public MylistAdapter(ArrayList<ResponseMylist> mylist, Activity activity) {
        this.Mylist = mylist;
        this.activity = activity;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mylist_write,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseMylist mylistlit= Mylist.get(position);
        holder.sampah.setText(mylistlit.getNamaItem());
        holder.jenis.setText(mylistlit.getJenis());
        holder.jumlah.setText(mylistlit.getJumlah());

        sessionManger = new SessionManger(activity);

        String hapus = mylistlit.getId().toString();
        String nama= mylistlit.getNamaItem();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String,String> userdetail= sessionManger.getUserDetailFromSession();
                        String get_token= userdetail.get(sessionManger.TOKENJWT);
                        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
                        Call<ResponseMessage> call =  apiInterface.getMessageResponse(hapus,"Bearer "+get_token);
                        call.enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                if(response.isSuccessful()){
                                    String message = response.body().getMessage();
                                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(activity, "gagal mengahapus"+ response.body().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                Toast.makeText(activity, "gagal mengahapus "+ t.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah ingin di hapus? "+mylistlit.getNamaItem());
                builder.show();;

            }
        });
        holder.List_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,Item.class);
                intent.putExtra("id", hapus);
                intent.putExtra("nama", nama);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Mylist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sampah,jenis,jumlah;
        ImageView delete;
        LinearLayout List_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sampah= itemView.findViewById(R.id.junk);
            jenis= itemView.findViewById(R.id.jenis);
            jumlah= itemView.findViewById(R.id.jumlah);
            delete= itemView.findViewById(R.id.hapus);
            List_item=itemView.findViewById(R.id.mylist_item);


        }
    }
}
