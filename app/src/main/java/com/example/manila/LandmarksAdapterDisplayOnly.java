package com.example.manila;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LandmarksAdapterDisplayOnly extends RecyclerView.Adapter<LandmarksAdapterDisplayOnly.ViewHolder> {
    private ArrayList<Landmarks> landmarks;
    private DatabaseHelperForUsers mydb;
    private int userID;

    public LandmarksAdapterDisplayOnly(Context context, ArrayList<Landmarks> list,int userID){
        landmarks=list;
        this.userID=userID;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Icons;
        TextView Title;
        TextView Details;
        ImageView notification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title=itemView.findViewById(R.id.TextViewTitleDisplay);
            Details=itemView.findViewById(R.id.TextViewDetailsDisplay);
            Icons=itemView.findViewById(R.id.ImageViewRizalDisplay);
            notification=itemView.findViewById(R.id.ivVisited);


        }
    }

    @NonNull
    @Override
    public LandmarksAdapterDisplayOnly.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayitems,parent,false);
        mydb=new DatabaseHelperForUsers(v.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarksAdapterDisplayOnly.ViewHolder holder, int position) {
        holder.itemView.setTag(landmarks.get(position));

        holder.Title.setText(landmarks.get(position).getLandmarks());
        holder.Details.setText(landmarks.get(position).getID());

        if(landmarks.get(position).getPreferences().equals("National Planetarium")){
            holder.Icons.setImageResource(R.drawable.planetarium);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("National Museum of Natural History")){
            holder.Icons.setImageResource(R.drawable.history);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("National Museum of Anthropology")){
            holder.Icons.setImageResource(R.drawable.annthropology);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("National Museum of Fine Arts")){
            holder.Icons.setImageResource(R.drawable.finearts);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Museo ni Jose Rizal")){
            holder.Icons.setImageResource(R.drawable.rizal);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Intramuros, Fort Santiago")){
            holder.Icons.setImageResource(R.drawable.intram);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Rizal Park")){
            holder.Icons.setImageResource(R.drawable.rizalpark);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Paco Park")){
            holder.Icons.setImageResource(R.drawable.pacopark);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Manila Ocean Park")){
            holder.Icons.setImageResource(R.drawable.manilaocean);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("San Agustin Church")){
            holder.Icons.setImageResource(R.drawable.sanagustin);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Quiapo Church")){
            holder.Icons.setImageResource(R.drawable.quaipo);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Manila Cathedral")){
            holder.Icons.setImageResource(R.drawable.manilacathedral);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("China Town")){
            holder.Icons.setImageResource(R.drawable.binondo);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Divisoria")){
            holder.Icons.setImageResource(R.drawable.divisoria);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Bahay Tsino")){
            holder.Icons.setImageResource(R.drawable.bahaytsino);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Kartilya ng Katipunan")){
            holder.Icons.setImageResource(R.drawable.kartilya);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Malacañang Palace")){
            holder.Icons.setImageResource(R.drawable.malacanang);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Casa Manila")){
            holder.Icons.setImageResource(R.drawable.casemanila);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Museo Pambata")){
            holder.Icons.setImageResource(R.drawable.muesuempambata);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }else if(landmarks.get(position).getPreferences().equals("Money Museum")){
            holder.Icons.setImageResource(R.drawable.moneymuseum);
            if(mydb.CheckIfUser_ListExist(userID,mydb.getIDFromTableLandmarks(landmarks.get(position).getLandmarks()))){
                holder.notification.setImageResource(R.drawable.listed);
            }else{
                holder.notification.setImageResource(R.drawable.about);
            }
        }

    }

    @Override
    public int getItemCount() {
        return landmarks.size();
    }
}
