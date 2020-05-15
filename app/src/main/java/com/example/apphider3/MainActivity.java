package com.example.apphider3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ListView listView;
    private ArrayList<AppInfo> modelArrayList;
    private Applicationadapter customAdapter;
    private Intent mainIntent;
    private List<ResolveInfo> app = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();

        listView = (ListView) findViewById(R.id.list1);

        new LoadApplications().execute();

        CheckAll();

        mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        app = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(app, new ResolveInfo.DisplayNameComparator(packageManager));

    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }


    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            modelArrayList = getModel(false, applist);
            customAdapter = new Applicationadapter(MainActivity.this, modelArrayList);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            listView.setAdapter(customAdapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private ArrayList<AppInfo> getModel(boolean isSelect, List<ApplicationInfo> applist) {
        ArrayList<AppInfo>list = new ArrayList<>();
        for (int i = 0; i < applist.size(); i++) {
            AppInfo model = new AppInfo();
            model.setSelected(isSelect);
            model.setPackageName(applist.get(i).packageName);
            model.setAppName(applist.get(i).loadLabel(packageManager).toString());
            model.setIcon(applist.get(i).loadIcon(packageManager));
            list.add(model);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.apply:
                String st =" ";
                for(int pos=0; pos<applist.size() ; pos++){
                    if (modelArrayList.get(pos).isSelected()) {
                        st = st + " " + modelArrayList.get(pos).getAppName();
                    }
                }

                Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();

                SaveAll();
                MainActivity.this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SaveAll() {
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        if(modelArrayList.size() != 0){
        for(int pos=0; pos<modelArrayList.size() ; pos++){
            if (modelArrayList.get(pos).isSelected()) {
                for (ResolveInfo temp : app){
                    if((temp.activityInfo.packageName).equals(modelArrayList.get(pos).getPackageName())){
                        ed.putBoolean(temp.activityInfo.packageName,true);
                    }
                }
            }else{
                ed.putBoolean(modelArrayList.get(pos).getPackageName(),false);
            }
        }}
        ed.commit();
    }

    private void CheckAll(){
        @SuppressLint("WrongConstant")
        SharedPreferences sp = getSharedPreferences("MyPref",Context.MODE_APPEND);
        if(modelArrayList.size() != 0){
        for(int pos=0; pos<modelArrayList.size() ; pos++){
            boolean value = sp.getBoolean(applist.get(pos).packageName,false);
            if(value){
                modelArrayList.get(pos).setSelected(value);
            }else {
                modelArrayList.get(pos).setSelected(value);
            }
        }}
    }

}
