package com.ams.studentattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.activity.CustomViewHolder;
import com.ams.studentattendance.activity.CustomViewHolderText;
import com.ams.studentattendance.bean.SubjectBean;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewStudentBean;
import com.ams.studentattendance.interfaces.OnClickListener;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<CustomViewHolderText>{
    private Context context;
    private List<SubjectBean> subjectList;

    public SubjectAdapter(Context context, List<SubjectBean> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    public SubjectAdapter() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<SubjectBean> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectBean> subjectList) {
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public CustomViewHolderText onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolderText(LayoutInflater.from(context).inflate(R.layout.card_row_textview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderText holder, int position) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        holder.txtFirst.setText(subjectList.get(position).getSubjectName());
        holder.txtSecond.setText("Credits: "+ subjectList.get(position).getSubjectCredits());
        holder.txtThird.setText(subjectList.get(position).getSubjectCode());
        holder.txtFourth.setText(dbHelper.getYearSemesterBySemId(subjectList.get(position).getSemesterId()));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
