package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flurgle.camerakit.CameraView;

import engineer.echo.transition.R;
import engineer.echo.transition.context.BaseFragment;

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
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        mCameraView = view.findViewById(R.id.app_camera_view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
