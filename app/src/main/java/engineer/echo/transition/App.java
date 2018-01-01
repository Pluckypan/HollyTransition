package engineer.echo.transition;

import android.app.Application;

/**
 * App
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午9:06.
 * more about me: http://www.1991th.com
 */

public class App extends Application {

    private static App mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static App getApp() {
        return mApp;
    }
}
