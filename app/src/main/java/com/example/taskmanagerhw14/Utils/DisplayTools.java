package com.example.taskmanagerhw14.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayTools {

    static public Point getDisplaySize(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    static public Orientation getDisplatOrientation(Activity activity)
    {
        if (activity != null)
        {
            int orientation = activity.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                return Orientation.LANDSCAPE;
            }
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                return Orientation.PORTRAIT;
            }


        }
        return Orientation.UNKNOWN;
    }

    public static enum Orientation
    {
        UNKNOWN(-1),
        UNDEFINED(0),
        PORTRAIT(1),
        LANDSCAPE(2);

        private int tag;

        Orientation(int i)
        {
            this.tag = i;
        }
    }
}
