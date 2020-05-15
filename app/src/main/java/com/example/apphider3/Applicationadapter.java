package com.example.apphider3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Applicationadapter extends BaseAdapter {


    private Context context;
    private static ArrayList<AppInfo> modelArrayList;
    private PackageManager packageManager;
    Applicationadapter(Context context, ArrayList<AppInfo> modelArrayList) {
        this.context = context;
        Applicationadapter.modelArrayList = modelArrayList;
        packageManager = context.getPackageManager();
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return modelArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @SuppressLint("InflateParams")
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(inflater).inflate(R.layout.listitem, null, true);
            holder.checkBox = convertView.findViewById(R.id.cb_app);
            holder.appName = convertView.findViewById(R.id.app_name);
            holder.IconView = convertView.findViewById(R.id.app_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.checkBox.setText("Checkbox " + position);
        holder.appName.setText(modelArrayList.get(position).getAppName());
        holder.checkBox.setChecked(modelArrayList.get(position).isSelected());
        holder.IconView.setImageDrawable(modelArrayList.get(position).getIcon());
        holder.checkBox.setTag(R.integer.btnPlusView, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.checkBox.getTag();
                // Toast.makeText(context, "Checkbox " +pos+ "Clicked!",
                //         Toast.LENGTH_SHORT).show();
                if (modelArrayList.get(pos).isSelected()) {
                    modelArrayList.get(pos).setSelected(false);
                    Toast.makeText(context, modelArrayList.get(pos).getAppName().toUpperCase() +" "+ "unclickd",
                            Toast.LENGTH_SHORT).show();
                } else {
                    modelArrayList.get(pos).setSelected(true);
                    Toast.makeText(context, modelArrayList.get(pos).getAppName().toUpperCase()+" "+"clicked",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
    private class ViewHolder {
        CheckBox checkBox;
        private TextView appName;
        ImageView IconView;
    }

}
