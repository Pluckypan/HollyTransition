package engineer.echo.transition.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import ch.halcyon.squareprogressbar.utils.PercentStyle;
import engineer.echo.transition.Constants;
import engineer.echo.transition.R;
import engineer.echo.transition.cmpts.context.BaseFragment;
import engineer.echo.transition.cmpts.events.CaptureFinishEvent;
import engineer.echo.transition.cmpts.utils.CommonUtil;
import engineer.echo.transition.cmpts.widget.reflect.ReflectItemView;
import engineer.echo.transition.cmpts.widget.transition.ProgressTransition;

/**
 * AppCtrlFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:26.
 * more about me: http://www.1991th.com
 * 点击控制
 */

public class AppCtrlFragment extends BaseFragment implements View.OnClickListener {

    public static String SHARE_NAME_LEFT = "share_element_of_left";
    public static String SHARE_NAME_MIDDLE = "share_element_of_middle";
    public static String SHARE_NAME_RIGHT = "share_element_of_right";
    private View mSceneBtn, mPhotoBtn, mSettingsBtn;
    private Pair<View, String>[] mShareViews = new Pair[3];
    private ReflectItemView mCaptureHolder;
    private ImageView mCaptureView;
    private ConstraintLayout mRootView;
    private int mEndSize, mMargin;
    private SquareProgressBar mProgressBar;

    public AppCtrlFragment() {
        mEndSize = (int) CommonUtil.dip2px(50);
        mMargin = (int) CommonUtil.dip2px(30);
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
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_ctrl, container, false);

        mRootView = view.findViewById(R.id.root_view);
        mSceneBtn = view.findViewById(R.id.scene_btn);
        mPhotoBtn = view.findViewById(R.id.take_photo_btn);
        mSettingsBtn = view.findViewById(R.id.settings_btn);
        mCaptureHolder = view.findViewById(R.id.capture_holder);
        mCaptureView = view.findViewById(R.id.capture_image);

        mProgressBar = view.findViewById(R.id.progressBar);
        mProgressBar.setImage(R.drawable.girl_4);
        mProgressBar.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        mProgressBar.setOnClickListener(this);
        mProgressBar.setWidth(2);
        PercentStyle mStyle = new PercentStyle(Paint.Align.CENTER, 14f, true);
        mStyle.setTextColor(Color.WHITE);
        mProgressBar.setPercentStyle(mStyle);
        mProgressBar.drawCenterline(true);
        mProgressBar.drawOutline(true);

        mSceneBtn.setOnClickListener(this);
        mPhotoBtn.setOnClickListener(this);
        mSettingsBtn.setOnClickListener(this);

        ViewCompat.setTransitionName(mSceneBtn, SHARE_NAME_LEFT);
        ViewCompat.setTransitionName(mPhotoBtn, SHARE_NAME_MIDDLE);
        ViewCompat.setTransitionName(mSettingsBtn, SHARE_NAME_RIGHT);

        mShareViews[0] = new Pair<>(mSceneBtn, SHARE_NAME_LEFT);
        mShareViews[1] = new Pair<>(mPhotoBtn, SHARE_NAME_MIDDLE);
        mShareViews[2] = new Pair<>(mSettingsBtn, SHARE_NAME_RIGHT);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCaptureFinishEvent(CaptureFinishEvent event) {
        Log.d(TAG, "onCaptureFinishEvent: ");
        mCaptureView.setImageResource(R.drawable.girl_4);

        mCaptureHolder.setAlpha(1.0f);

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setPathMotion(new ArcMotion());
        changeBounds.setDuration(Constants.TRANSITION_TIME);
        TransitionManager.beginDelayedTransition(mRootView,
                changeBounds);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mCaptureHolder.getLayoutParams();
        params.width = mEndSize;
        params.height = mEndSize;

        params.endToEnd = -1;
        params.startToStart = -1;
        params.topToTop = -1;

        params.bottomToBottom = R.id.root_view;
        params.endToStart = R.id.take_photo_btn;

        params.bottomMargin = mMargin;
        params.rightMargin = mMargin;
        params.topMargin = 0;

        mCaptureHolder.setLayoutParams(params);
    }

    private void smoothProgress() {
        Random random = new Random();
        int value = random.nextInt(100);
        TransitionManager.beginDelayedTransition(mRootView, new ProgressTransition());
        value = Math.max(0, Math.min(100, value));
        mProgressBar.setProgress(value);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scene_btn:
                break;
            case R.id.take_photo_btn:
                //拍照很卡 暂时屏蔽
                //EventBus.getDefault().post(new StartCaptureEvent());
                onCaptureFinishEvent(null);
                break;
            case R.id.settings_btn:
                SettingsFragment.gotoPage(getFragmentManager(), mShareViews);
                break;
            case R.id.progressBar:
                smoothProgress();
                break;
        }
    }
}
