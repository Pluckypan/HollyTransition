package engineer.echo.transition.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import engineer.echo.transition.R;
import engineer.echo.transition.cmpts.context.BaseFragment;
import engineer.echo.transition.cmpts.events.CaptureFinishEvent;
import engineer.echo.transition.cmpts.events.StartCaptureEvent;

/**
 * AppDisplayFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:28.
 * more about me: http://www.1991th.com
 */

public class AppDisplayFragment extends BaseFragment {

    private CameraView mCameraView;

    public AppDisplayFragment() {

    }

    public static AppDisplayFragment newInstance() {
        AppDisplayFragment fragment = new AppDisplayFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        mCameraView = view.findViewById(R.id.app_camera_view);
        mCameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                Log.d(TAG, "onPictureTaken: ");
                // Create a bitmap
                Bitmap result = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                EventBus.getDefault().post(new CaptureFinishEvent(result));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartCaptureEvent(StartCaptureEvent event) {
        Log.d(TAG, "onStartCaptureEvent: ");
        takePhoto();
    }

    private void takePhoto() {
        if (mCameraView != null) {
            mCameraView.captureImage();
        }
    }
}
