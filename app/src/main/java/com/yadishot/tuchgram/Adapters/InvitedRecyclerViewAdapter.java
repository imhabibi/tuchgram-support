package com.yadishot.tuchgram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yadishot.tuchgram.Models.InviteModel;
import com.yadishot.tuchgram.R;

import java.util.List;

public class InvitedRecyclerViewAdapter extends RecyclerView.Adapter<InvitedRecyclerViewAdapter.viewHolder> {


    Context context;
    List<InviteModel> mInvite;

    public InvitedRecyclerViewAdapter(Context context, List<InviteModel> mInvite) {
        this.context = context;
        this.mInvite = mInvite;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invited_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        InviteModel inviteModel = mInvite.get(position);

        holder.projectInviter.setText(inviteModel.getProjectInviter());
        holder.projectDecs.setText(inviteModel.getProjectDecs());
        holder.projectAccept.setText(inviteModel.getProjectAccept());
        holder.projectMoney.setText(inviteModel.getProjectMoney());
        holder.projectSod.setText(inviteModel.getProjectSod());
    }

    @Override
    public int getItemCount() {
        return mInvite.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView projectInviter, projectDecs, projectAccept, projectMoney, projectSod;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            projectInviter = itemView.findViewById(R.id.projectInviter);
            projectDecs = itemView.findViewById(R.id.projectDecs);
            projectAccept = itemView.findViewById(R.id.projectAccept);
            projectMoney = itemView.findViewById(R.id.projectMoney);
            projectSod = itemView.findViewById(R.id.projectSod);
        }
    }
}
