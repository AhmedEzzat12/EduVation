package org.mat.eduvation.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.mat.eduvation.R;

import java.util.ArrayList;

/**
 * Created by Hima on 7/23/2016.
 */
public class ViewDialog {
    ArrayList<String>descriptions ;
    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.firstdialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
      /*  descriptions= new ArrayList<String>();
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");
        descriptions.add("this is spectruijsjd;sjd;lsjd;lsjd");

        StringBuilder mybuilder=new StringBuilder();
        for(int i=0;i<descriptions.size();i++){

            mybuilder.append(descriptions.get(i) + "\n\n--------------------------------------\n\n");
        }
        String res=mybuilder.toString();
        text.setText(res);
*/
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}