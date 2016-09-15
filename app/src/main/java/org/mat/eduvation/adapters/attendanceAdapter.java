package org.mat.eduvation.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mat.eduvation.ImageModel;
import org.mat.eduvation.R;
import org.mat.eduvation.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mat.eduvation.navigation_items.Profile.decodeBase64;

/**
 * Created by gmgn on 8/20/2016.
 */

public class attendanceAdapter extends RecyclerView.Adapter<attendanceAdapter.myholder> {

    private List<UserModel> userslist = new ArrayList<>();
    private HashMap<String, ImageModel> userImageList = new HashMap<>();

    public attendanceAdapter(List<UserModel> userslist1, HashMap<String, ImageModel> usersImagelist1) {
    this.userslist=userslist1;
        this.userImageList = usersImagelist1;
    }
    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {


        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row,parent,false);
        myholder holder=new myholder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {

        String nameRow = userslist.get(position).getName();
        try {
            String imageStr = userImageList.get(userslist.get(position).getEmail().toLowerCase()).getImagestr();
            Bitmap myBitmapAgain = decodeBase64(imageStr);
            //Log.d("getDataFB", imageMap.get("imageStr"));
            Drawable d = new BitmapDrawable(myBitmapAgain);
            holder.userImage.setImageDrawable(d);
            holder.name.setText(nameRow);

        } catch (Exception e) {

            //Log.e("attendance adapter", e.getMessage());

            holder.name.setText(nameRow);
        }

    }

    @Override
    public int getItemCount() {


        return userslist.size();
    }

    public void updateData(List<UserModel> userslist1, HashMap<String, ImageModel> usersImagelist1) {
        this.userslist = userslist1;
        this.userImageList = usersImagelist1;
        notifyDataSetChanged();
    }

    class myholder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView userImage;
        public myholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemName);
            userImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }
}
