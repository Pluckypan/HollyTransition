package engineer.echo.transition.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private View mPhotoBtn;


    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mPhotoBtn = view.findViewById(R.id.take_photo_btn);
        mPhotoBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo_btn:
                getActivity().onBackPressed();
                break;
        }
    }

    public static void gotoPage(FragmentManager manager) {
        SettingsFragment fragment = SettingsFragment.newInstance();
        manager.beginTransaction()
                .replace(R.id.app_ctrl_view, fragment)
                .addToBackStack(null)
                .commit();

    }
}
