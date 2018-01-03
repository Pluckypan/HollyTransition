package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import engineer.echo.transition.App;
import engineer.echo.transition.R;
import engineer.echo.transition.context.BaseFragment;
import engineer.echo.transition.utils.CommonUtil;
import engineer.echo.transition.widget.transition.BoundsAndAlpha;

import static engineer.echo.transition.Constants.TRANSITION_TIME;
import static engineer.echo.transition.Constants.TRANSITION_TIME_SHORT;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_LEFT;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_MIDDLE;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_RIGHT;

/**
 * SettingsFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:28.
 * more about me: http://www.1991th.com
 * 总结：
 * 类似于Flash的关键帧，补间动画概念
 * captureStarValues--->CaptureEndValues--->根据createAnimator设定的动画来 执行动画变化
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private View mFakeLeft, mPhotoBtn, mFakeRight;
    private LinearLayout mRoot;
    private View mOne, mTwo, mThree, mFour;
    private View mSceneOne, mSceneTwo, mSceneThree, mSceneFour;
    private int mNormalSize, mScaleSize;
    private LinearLayout mSceneRoot;


    public SettingsFragment() {
        mNormalSize = (int) CommonUtil.dip2px(60);
        mScaleSize = (int) (mNormalSize * 1.5f);
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
        mFakeLeft = view.findViewById(R.id.app_settings_fake_left);
        mPhotoBtn = view.findViewById(R.id.take_photo_btn);
        mFakeRight = view.findViewById(R.id.app_settings_fake_right);
        mFakeLeft.setTag(R.id.key_for_alpha_change_when_share_element, true);
        mFakeRight.setTag(R.id.key_for_alpha_change_when_share_element, true);

        mPhotoBtn.setOnClickListener(this);
        mRoot = view.findViewById(R.id.app_settings_change);

        mOne = view.findViewById(R.id.app_settings_change_one);
        mTwo = view.findViewById(R.id.app_settings_change_two);
        mThree = view.findViewById(R.id.app_settings_change_three);
        mFour = view.findViewById(R.id.app_settings_change_four);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);

        initSceneView(view);

        mSceneRoot = view.findViewById(R.id.app_settings_scene_change);
        view.findViewById(R.id.app_settings_scene_btn).setOnClickListener(this);

        ViewCompat.setTransitionName(mFakeLeft, SHARE_NAME_LEFT);
        ViewCompat.setTransitionName(mPhotoBtn, SHARE_NAME_MIDDLE);
        ViewCompat.setTransitionName(mFakeRight, SHARE_NAME_RIGHT);
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
            case R.id.app_settings_change_one:
            case R.id.app_settings_change_two:
            case R.id.app_settings_change_three:
            case R.id.app_settings_change_four:
                changeAttr(view);
                break;
            case R.id.app_settings_scene_btn:
                sceneChange();
                break;

            case R.id.app_settings_scene_one:
            case R.id.app_settings_scene_two:
            case R.id.app_settings_scene_three:
            case R.id.app_settings_scene_four:
                visibilityChange(view);
                break;
        }
    }

    private void initSceneView(View view) {
        mSceneOne = view.findViewById(R.id.app_settings_scene_one);
        mSceneTwo = view.findViewById(R.id.app_settings_scene_two);
        mSceneThree = view.findViewById(R.id.app_settings_scene_three);
        mSceneFour = view.findViewById(R.id.app_settings_scene_four);

        mSceneOne.setOnClickListener(this);
        mSceneTwo.setOnClickListener(this);
        mSceneThree.setOnClickListener(this);
        mSceneFour.setOnClickListener(this);
    }

    private void visibilityChange(View view) {
        TransitionManager.beginDelayedTransition(mSceneRoot, new Fade());
        view.setVisibility(View.GONE);
    }

    /**
     * 场景动画
     */
    private void sceneChange() {
        boolean isSelected = mSceneRoot.isSelected();
        int resID = isSelected ? R.layout.layout_scene_start : R.layout.layout_scene_end;
        Scene sceneTwo = Scene.getSceneForLayout(mSceneRoot, resID, getContext());
        TransitionManager.go(sceneTwo, new ChangeBounds());
        mSceneRoot.setSelected(!isSelected);
    }

    /**
     * 延迟动画
     *
     * @param v View 当前点击的View
     */
    private void changeAttr(View v) {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(TRANSITION_TIME_SHORT);
        TransitionManager.beginDelayedTransition(mRoot, changeBounds);
        int c = mRoot.getChildCount();
        for (int i = 0; i < c; i++) {
            View child = mRoot.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
            if (child == v) {
                params.width = mScaleSize;
                params.height = mScaleSize;
            } else {
                params.width = mNormalSize;
                params.height = mNormalSize;
            }
            child.setLayoutParams(params);
        }
    }

    public static void gotoPage(FragmentManager manager, Pair<View, String>... shareViews) {
        SettingsFragment fragment = SettingsFragment.newInstance();
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);

        Transition transitionIn = TransitionInflater.from(App.getApp()).inflateTransition(R.transition.transition_settings_in);
        fragment.setEnterTransition(transitionIn);

        Transition transitionOut = TransitionInflater.from(App.getApp()).inflateTransition(R.transition.transition_settings_out);
        fragment.setReturnTransition(transitionOut);

        BoundsAndAlpha boundsIn = new BoundsAndAlpha(true);
        boundsIn.setDuration(TRANSITION_TIME);
        fragment.setSharedElementEnterTransition(boundsIn);

        BoundsAndAlpha boundsOut = new BoundsAndAlpha(false);
        boundsOut.setDuration(TRANSITION_TIME);
        fragment.setSharedElementReturnTransition(boundsOut);

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.app_ctrl_view, fragment);
        fragmentTransaction.addToBackStack(null);
        if (shareViews != null && shareViews.length > 0) {
            for (Pair<View, String> shareView : shareViews) {
                fragmentTransaction.addSharedElement(shareView.first, shareView.second);
            }
        }
        fragmentTransaction.commit();

    }
}
