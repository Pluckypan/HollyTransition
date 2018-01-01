package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import engineer.echo.transition.R;
import engineer.echo.transition.context.BaseFragment;

/**
 * AppCtrlFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:26.
 * more about me: http://www.1991th.com
 * 点击控制
 */

public class AppCtrlFragment extends BaseFragment implements View.OnClickListener {

    private View mSceneBtn, mPhotoBtn, mSettingsBtn;

    public AppCtrlFragment() {

    }

    public static AppCtrlFragment newInstance() {
        AppCtrlFragment fragment = new AppCtrlFragment();
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
        View view = inflater.inflate(R.layout.fragment_ctrl, container, false);
        mSceneBtn = view.findViewById(R.id.scene_btn);
        mPhotoBtn = view.findViewById(R.id.take_photo_btn);
        mSettingsBtn = view.findViewById(R.id.settings_btn);

        mSceneBtn.setOnClickListener(this);
        mPhotoBtn.setOnClickListener(this);
        mSettingsBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scene_btn:
                break;
            case R.id.take_photo_btn:
                break;
            case R.id.settings_btn:
                SettingsFragment.gotoPage(getFragmentManager());
                break;
        }
    }
}
