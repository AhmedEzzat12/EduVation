package org.mat.eduvation.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class attendanceAdapter extends RecyclerView.Adapter<attendanceAdapter.myHolder> {
    private List<UserModel> userslist = new ArrayList<>();
    private Context context;
    //private List<Bitmap>temp=new ArrayList<>();
    private HashMap<String, Bitmap> hashMapUpdated = new HashMap<>();

    public attendanceAdapter(HashMap<String, Bitmap> hashMapUpdated, List<UserModel> userslist1, Context context) {
        this.context = context;
        this.userslist = userslist1;
        this.hashMapUpdated = hashMapUpdated;
    }
    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row, parent, false);
        return new myHolder(row);
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {

        if (hashMapUpdated.get(userslist.get(position).getEmail().toLowerCase()) != null) {
            holder.userImage.setImageBitmap(hashMapUpdated.get(userslist.get(position).getEmail().toLowerCase()));
            Log.d("IFAttAdapter", userslist.get(position).getName());
            holder.name.setText(userslist.get(position).getName());
        } else {
            //Log.e("attendance adapter", e.getMessage());
            Log.d("elseAttAdapter", userslist.get(position).getName());
            holder.userImage.setImageResource(R.mipmap.ic_launcher);
            holder.name.setText(userslist.get(position).getName());
        }
    }
    @Override
    public int getItemCount() {
        return userslist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateData(HashMap<String, Bitmap> hashMapUpdated, List<UserModel> userslist) {
        this.userslist = userslist;
        this.hashMapUpdated = hashMapUpdated;
        notifyDataSetChanged();
    }

    class myHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView userImage;

        public myHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemName);
            userImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }
}
