package com.example.apphider3;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private String packageName;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setIcon(Drawable icon) {
        Icon = icon;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private String appName;

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return Icon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    private Drawable Icon;
    private boolean isSelected;

}
