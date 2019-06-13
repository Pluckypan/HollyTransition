package engineer.echo.transition;

import android.app.Application;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * App
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午9:06.
 * more about me: http://www.1991th.com
 */

public class App extends Application {

    private static App mApp = null;
    public static Point sScreenSize = new Point();
    public static float sDensity = 1f;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        sScreenSize.set(metrics.widthPixels, metrics.heightPixels);
        sDensity = metrics.density;
    }

    public static App getApp() {
        return mApp;
    }

    public static int dpToPx(float dp) {
        return (int) (dp * sDensity + 0.5f);
    }
}
