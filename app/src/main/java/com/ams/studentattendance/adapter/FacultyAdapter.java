package com.ams.studentattendance.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.activity.CustomViewHolder;
import com.ams.studentattendance.activity.ViewFaculty;
import com.ams.studentattendance.bean.FacultyBean;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.interfaces.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<CustomViewHolder> implements Filterable {
    private Context context;
    private List<ViewFacultyBean> facultyList;
    private List<ViewFacultyBean> mFacultyList;
    private OnClickListener onClickListener;

    public List<ViewFacultyBean> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<ViewFacultyBean> facultyList) {
        this.facultyList = facultyList;
    }

    public List<ViewFacultyBean> getmFacultyList() {
        return mFacultyList;
    }

    public void setmFacultyList(List<ViewFacultyBean> mFacultyList) {
        this.mFacultyList = mFacultyList;
    }

    public FacultyAdapter(Context context, List<ViewFacultyBean> facultyList, OnClickListener onClickListener) {
        this.context = context;
        this.facultyList = facultyList;
        this.mFacultyList = facultyList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.card_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtFirst.setText(String.valueOf(mFacultyList.get(position).getFirstName()) + " " + mFacultyList.get(position).getLastName());
        holder.txtSecond.setText(mFacultyList.get(position).getAcademicQualification());
        holder.txtThird.setText(mFacultyList.get(position).getFacultyType());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Faculty")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper db = new DatabaseHelper(context);
                                boolean res = db.deleteFaculty(mFacultyList.get(holder.getAdapterPosition()));

                                if(res) {
                                    int pos = holder.getAdapterPosition();
                                    facultyList.remove(mFacultyList.remove(pos));
                                    notifyDataSetChanged();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        holder.cardRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickListener.onItemClicked(mFacultyList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFacultyList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                List<ViewFacultyBean> filterList = new ArrayList<>();

                if (constraint == null || charString.isEmpty()) {
                    filterList = facultyList;
                } else {
                    for (ViewFacultyBean facultyBean : facultyList) {
                        if (facultyBean.getFirstName().toLowerCase().contains(charString) || facultyBean.getLastName().toLowerCase().contains(charString)) {
                            filterList.add(facultyBean);
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
                mFacultyList = (List<ViewFacultyBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void removeItem(int position) {
        facultyList.remove(mFacultyList.remove(position));
        notifyDataSetChanged();
    }
}
