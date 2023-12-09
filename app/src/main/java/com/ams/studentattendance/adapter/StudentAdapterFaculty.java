package com.ams.studentattendance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.activity.CustomViewHolder;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewStudentBean;
import com.ams.studentattendance.interfaces.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapterFaculty extends RecyclerView.Adapter<CustomViewHolder> implements Filterable {
    private Context context;
    private List<ViewStudentBean> studentList;
    private List<ViewStudentBean> mStudentList;
    private OnClickListener onClickListener;

    public List<ViewStudentBean> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<ViewStudentBean> studentList) {
        this.studentList = studentList;
    }

    public List<ViewStudentBean> getmStudentList() {
        return mStudentList;
    }

    public void setmStudentList(List<ViewStudentBean> mStudentList) {
        this.mStudentList = mStudentList;
    }

    public StudentAdapterFaculty(Context context, List<ViewStudentBean> studentList, OnClickListener onClickListener) {
        this.context = context;
        this.studentList = studentList;
        this.mStudentList = studentList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.card_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtFirst.setText(String.valueOf(mStudentList.get(position).getFirstName()) + " " + mStudentList.get(position).getLastName());
        holder.txtSecond.setText(mStudentList.get(position).getRollNumber() + "- " + mStudentList.get(position).getSemester());
        holder.txtThird.setText(mStudentList.get(position).getBranchName());
        holder.btnDelete.setVisibility(View.INVISIBLE);
        holder.cardRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickListener.onItemClicked(mStudentList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<ViewStudentBean> filterList = new ArrayList<>();

                if (constraint == null || charString.isEmpty()) {
                    filterList = studentList;
                } else {
                    for (ViewStudentBean studentBean : studentList) {
                        if (studentBean.getFirstName().toLowerCase().contains(charString) || studentBean.getLastName().toLowerCase().contains(charString) || studentBean.getRollNumber().toLowerCase().contains(charString)) {
                            filterList.add(studentBean);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                filterResults.count = filterList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mStudentList = (List<ViewStudentBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
