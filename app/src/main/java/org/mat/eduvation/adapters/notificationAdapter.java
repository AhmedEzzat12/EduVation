package org.mat.eduvation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mat.eduvation.NotificationModel;
import org.mat.eduvation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 8/23/16.
 */

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.myholder> {

    private List<NotificationModel> notificationslist = new ArrayList<>();
    private Context context;

    public notificationAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<NotificationModel> notificationsList) {
        this.notificationslist.clear();
        this.notificationslist.addAll(notificationsList);
        notifyDataSetChanged();
    }

    @Override
    public notificationAdapter.myholder onCreateViewHolder(ViewGroup parent, int viewType) {


        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row, parent, false);
        notificationAdapter.myholder holder = new notificationAdapter.myholder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(notificationAdapter.myholder holder, int position) {
        holder.message.setText(notificationslist.get(position).getMessage());
        holder.date.setText(notificationslist.get(position).getDate());
    }

    @Override
    public int getItemCount() {

        return notificationslist.size();
    }

    class myholder extends RecyclerView.ViewHolder {

        TextView message;
        TextView date;

        public myholder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.notificationMessage);
            date = (TextView) itemView.findViewById(R.id.notificationDate);
        }
    }
}
