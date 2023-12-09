package com.ams.studentattendance.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.activity.CustomViewHolder;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.MarkAttendanceStudentBean;
import com.ams.studentattendance.dto.ViewStudentBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private Context context;
    private List<MarkAttendanceStudentBean> studentList;
    boolean isAllSelected;

    public void unSelectAll() {
        isAllSelected = false;
        setBeanCheckedStatus(isAllSelected);
        notifyDataSetChanged();
    }

    public void selectAll() {
        isAllSelected = true;
        setBeanCheckedStatus(isAllSelected);
        notifyDataSetChanged();
    }

    private void setBeanCheckedStatus(boolean isAllSelected) {
        for(int i=0;i<studentList.size();i++) {
            studentList.get(i).setChecked(isAllSelected);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRollNumber;
        public TextView txtStudentName;
        public TextView txtEnrollmentNumber;
        public CheckBox chkSelected;
        public CardView cardRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRollNumber = (TextView) itemView.findViewById(R.id.txtRollNumber);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            txtEnrollmentNumber = (TextView) itemView.findViewById(R.id.txtEnrollmentNumber);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            cardRow = (CardView) itemView.findViewById(R.id.cardRow);
        }
    }

    public AttendanceAdapter() {
    }

    public void setStudentList(List<MarkAttendanceStudentBean> studentList) {
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    public List<MarkAttendanceStudentBean> getStudentList() {
        return studentList;
    }

    public boolean isAllSelected() {
        return isAllSelected;
    }

    public void setAllSelected(boolean allSelected) {
        isAllSelected = allSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_ateendance_row, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtRollNumber.setText(studentList.get(position).getStudentBean().getRollNumber());
        holder.txtStudentName.setText(studentList.get(position).getStudentBean().getFirstName() + " " + studentList.get(position).getStudentBean().getLastName());
        holder.txtEnrollmentNumber.setText(studentList.get(position).getStudentBean().getEnrollmentNumber());
        if(isAllSelected) {
            holder.chkSelected.setChecked(true);
        } else {
            holder.chkSelected.setChecked(false);
        }
        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.chkSelected.isChecked()) {
                    studentList.get(holder.getAdapterPosition()).setChecked(true);
                } else {
                    if(isAllSelected) {
                        isAllSelected = false;
                    }
                    studentList.get(holder.getAdapterPosition()).setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public AttendanceAdapter(Context context, List<MarkAttendanceStudentBean> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @SuppressLint("NewApi")
    public List<ViewStudentBean> getSelectedStudents() {
        List<MarkAttendanceStudentBean> lst = studentList.stream().filter(item -> item.isChecked() == true).collect(Collectors.toList());
        List<ViewStudentBean> list = new ArrayList<>();
        for(MarkAttendanceStudentBean item: lst) {
            list.add(item.getStudentBean());
        }
        return list;
    }
}
