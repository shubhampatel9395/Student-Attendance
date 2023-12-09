package com.ams.studentattendance.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView txtFirst;
    public TextView txtSecond;
    public TextView txtThird;
    public CardView cardRow;
    public Button btnDelete;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        txtFirst = (TextView) itemView.findViewById(R.id.txtFirst);
        txtSecond = (TextView) itemView.findViewById(R.id.txtSecond);
        txtThird = (TextView) itemView.findViewById(R.id.txtThird);
        cardRow = (CardView) itemView.findViewById(R.id.cardRow);
        btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
    }
}
