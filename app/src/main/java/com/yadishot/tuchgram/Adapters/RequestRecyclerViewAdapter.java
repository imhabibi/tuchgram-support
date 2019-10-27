package com.yadishot.tuchgram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yadishot.tuchgram.Models.RequestModel;
import com.yadishot.tuchgram.R;

import java.util.List;

public class RequestRecyclerViewAdapter extends RecyclerView.Adapter<RequestRecyclerViewAdapter.viewHolder> {

    Context context;
    List<RequestModel> mList;

    public RequestRecyclerViewAdapter(Context context, List<RequestModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supports_request_items, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RequestModel mModel = mList.get(position);
        holder.projectName.setText(mModel.getProjectName());
        holder.projectDesc.setText(mModel.getProjectDescription());
        holder.projectTime.setText(mModel.getProjectTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView projectName, projectDesc, projectTime;
        Button goSupport;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            projectDesc = itemView.findViewById(R.id.projectDescription);
            projectTime = itemView.findViewById(R.id.projectTime);
            goSupport = itemView.findViewById(R.id.openSupport);
        }
    }
}
