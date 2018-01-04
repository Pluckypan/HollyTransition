package engineer.echo.transition.cmpts.events;

import android.graphics.Bitmap;

/**
 * CaptureFinishEvent
 * Created by Plucky<plucky@echo.engineer> on 2018/1/4 - 15:23
 * more about me: http://www.1991th.com
 */

public class CaptureFinishEvent {

    private Bitmap mPicture;

    public CaptureFinishEvent(Bitmap mPicture) {
        this.mPicture = mPicture;
    }

    public Bitmap getPicture() {
        return mPicture;
    }
}
