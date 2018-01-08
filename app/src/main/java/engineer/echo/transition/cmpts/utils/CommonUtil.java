package engineer.echo.transition.cmpts.utils;

import android.os.Build;
import android.util.DisplayMetrics;

import engineer.echo.transition.App;

/**
 * AppCtrlFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/3 - 17:28
 * more about me: http://www.1991th.com
 */

public class CommonUtil {
    public static float dip2px(float pDipValue) {
        DisplayMetrics dm = App.getApp().getResources().getDisplayMetrics();
        return pDipValue * dm.density;
    }

    public static boolean isBelowLollipop() {
        return android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP;
    }

    //API 21
    public static boolean isOverLollipop() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    }

    //API 19
    public static boolean isOverKITKAT() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
