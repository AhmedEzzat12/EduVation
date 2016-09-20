package org.mat.eduvation.adapters;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mat.eduvation.R;
import org.mat.eduvation.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class attendanceAdapter extends RecyclerView.Adapter<attendanceAdapter.myholder> {
    private List<UserModel> userslist = new ArrayList<>();
    //private List<Bitmap>temp=new ArrayList<>();
    private HashMap<String, Bitmap> hashMapUpdated = new HashMap<>();

    public attendanceAdapter(HashMap<String, Bitmap> hashMapUpdated, List<UserModel> userslist1) {

        this.userslist.clear();
        this.userslist = userslist1;
        this.hashMapUpdated.clear();
        this.hashMapUpdated = hashMapUpdated;
    }
    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row, parent, false);
        return new myholder(row);
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {

        if (hashMapUpdated.get(userslist.get(position).getEmail().toLowerCase()) != null) {
            holder.userImage.setImageBitmap(hashMapUpdated.get(userslist.get(position).getEmail().toLowerCase()));
            holder.name.setText(userslist.get(position).getName());
        } else {
            //Log.e("attendance adapter", e.getMessage());
            holder.name.setText(userslist.get(position).getName());
        }
    }
    @Override
    public int getItemCount() {
        return userslist.size();
    }

    public void updateData(HashMap<String, Bitmap> hashMapUpdated, List<UserModel> userslist) {
        this.userslist = userslist;
        this.hashMapUpdated = hashMapUpdated;
        notifyDataSetChanged();
    }

    class myholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView userImage;
        public myholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemName);
            userImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }
}
