package org.mat.eduvation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mat.eduvation.R;
import org.mat.eduvation.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmgn on 8/20/2016.
 */
public class attendanceadapter extends RecyclerView.Adapter<attendanceadapter.myholder>{

    List<UserModel> userslist = new ArrayList<>();
    Context context;
    String namerow;

    public attendanceadapter(List<UserModel> userslist1, Context context) {
    this.userslist=userslist1;
    this.context=context;
    }
    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {


        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row,parent,false);
        myholder holder=new myholder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {
        namerow=userslist.get(position).getName();
        holder.name.setText(namerow);
    }

    @Override
    public int getItemCount() {


        return userslist.size();
    }

    class myholder extends RecyclerView.ViewHolder{

        TextView name;
        public myholder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.itemname);

        }
    }

}
