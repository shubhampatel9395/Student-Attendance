package com.ams.studentattendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.dto.MarkAttendanceStudentBean;
import com.ams.studentattendance.dto.ViewAttendanceBean;
import com.ams.studentattendance.dto.ViewStudentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewHolder> {
    private Context context;
    private List<ViewAttendanceBean> attendanceList;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRollNumber;
        public TextView txtStudentName;
        public TextView txtAttendanceStatus;
        public TableLayout tableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRollNumber = (TextView) itemView.findViewById(R.id.txtRollNumber);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            txtAttendanceStatus = (TextView) itemView.findViewById(R.id.txtAttendanceStatus);
            tableLayout = (TableLayout) itemView.findViewById(R.id.table_heading_layout);
        }
    }

    public ViewAttendanceAdapter() {
    }

    public ViewAttendanceAdapter(Context context, List<ViewAttendanceBean> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ViewAttendanceBean> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<ViewAttendanceBean> attendanceList) {
        this.attendanceList = attendanceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewAttendanceAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.table_row_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewAttendanceAdapter.ViewHolder holder, int position) {
        holder.txtRollNumber.setText(attendanceList.get(position).getAttendanceBean().getRollNumber());
        holder.txtStudentName.setText(attendanceList.get(position).getStudentName());
        holder.txtAttendanceStatus.setText(attendanceList.get(position).getAttendanceBean().getAttendanceStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}