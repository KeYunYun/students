package com.kcy.mobilesafe.db.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by kcy on 2017/6/2.
 */

public class AppInfo {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSdCard() {
        return isSdCard;
    }

    public void setSdCard(boolean sdCard) {
        isSdCard = sdCard;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String packName;
    public Drawable icon;
    public boolean isSdCard;
    public boolean isSystem;
}
