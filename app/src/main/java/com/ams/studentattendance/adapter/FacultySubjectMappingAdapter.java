package com.ams.studentattendance.adapter;

import android.annotation.SuppressLint;
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
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewFacultySubjectMappingBean;
import com.ams.studentattendance.interfaces.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class FacultySubjectMappingAdapter extends RecyclerView.Adapter<CustomViewHolder> implements Filterable {
    private Context context;
    private List<ViewFacultySubjectMappingBean> mappingList;
    private List<ViewFacultySubjectMappingBean> fMappingList;
    private OnClickListener onClickListener;

    public List<ViewFacultySubjectMappingBean> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<ViewFacultySubjectMappingBean> mappingList) {
        this.mappingList = mappingList;
    }

    public List<ViewFacultySubjectMappingBean> getfMappingList() {
        return fMappingList;
    }

    public void setfMappingList(List<ViewFacultySubjectMappingBean> fMappingList) {
        this.fMappingList = fMappingList;
    }

    public FacultySubjectMappingAdapter(Context context, List<ViewFacultySubjectMappingBean> mappingList, OnClickListener onClickListener) {
        this.context = context;
        this.mappingList = mappingList;
        this.fMappingList = mappingList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.card_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtFirst.setText(fMappingList.get(position).getSubjectBean().getSubjectName());
        holder.txtSecond.setText(String.valueOf(fMappingList.get(position).getViewFacultyBean().getFirstName()) + " " + fMappingList.get(position).getViewFacultyBean().getLastName());
        holder.txtThird.setText("");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Mapping")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper db = new DatabaseHelper(context);
                                boolean res = db.deleteFacultySubjectMapping(fMappingList.get(holder.getAdapterPosition()));
                                if(res) {
                                    int pos = holder.getAdapterPosition();
                                    mappingList.remove(fMappingList.remove(pos));
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
                onClickListener.onItemClicked(fMappingList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fMappingList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<ViewFacultySubjectMappingBean> filterList = new ArrayList<>();

                if (constraint == null || charString.isEmpty()) {
                    filterList = mappingList;
                } else {
                    for (ViewFacultySubjectMappingBean item : mappingList) {
                        if (item.getViewFacultyBean().getFirstName().toLowerCase().contains(charString) || item.getViewFacultyBean().getLastName().toLowerCase().contains(charString) || item.getSubjectBean().getSubjectName().toLowerCase().contains(charString)) {
                            filterList.add(item);
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
                fMappingList = (List<ViewFacultySubjectMappingBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void removeItem(int position) {
        mappingList.remove(fMappingList.remove(position));
        notifyDataSetChanged();
    }
}