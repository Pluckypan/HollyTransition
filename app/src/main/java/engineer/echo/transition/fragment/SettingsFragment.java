package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
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
import engineer.echo.transition.widget.transition.BoundsAndAlpha;

import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_LEFT;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_MIDDLE;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_RIGHT;

/**
 * AppDisplayFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:28.
 * more about me: http://www.1991th.com
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private View mPhotoBtn;
    private LinearLayout mRoot;
    private View mOne, mTwo, mThree;


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
        mRoot = view.findViewById(R.id.app_settings_change);
        mOne = view.findViewById(R.id.app_settings_change_one);
        mTwo = view.findViewById(R.id.app_settings_change_two);
        mThree = view.findViewById(R.id.app_settings_change_three);
        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);

        ViewCompat.setTransitionName(mOne, SHARE_NAME_LEFT);
        ViewCompat.setTransitionName(mTwo, SHARE_NAME_MIDDLE);
        ViewCompat.setTransitionName(mThree, SHARE_NAME_RIGHT);
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
                changeAttr(view);
                break;
        }
    }

    private void changeAttr(View v) {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(1000);
        TransitionManager.beginDelayedTransition(mRoot, changeBounds);
        LinearLayout.LayoutParams paramsOne = (LinearLayout.LayoutParams) mOne.getLayoutParams();
        paramsOne.weight = v == mOne ? 1.0f : 0.5f;
        mOne.setLayoutParams(paramsOne);
        LinearLayout.LayoutParams paramsTwo = (LinearLayout.LayoutParams) mTwo.getLayoutParams();
        paramsTwo.weight = v == mTwo ? 1.0f : 0.5f;
        mTwo.setLayoutParams(paramsTwo);
        LinearLayout.LayoutParams paramsThree = (LinearLayout.LayoutParams) mThree.getLayoutParams();
        paramsThree.weight = v == mThree ? 1.0f : 0.5f;
        mThree.setLayoutParams(paramsThree);
    }

    public static void gotoPage(FragmentManager manager, Pair<View, String>... shareViews) {
        SettingsFragment fragment = SettingsFragment.newInstance();
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);

        Transition transitionIn = TransitionInflater.from(App.getApp()).inflateTransition(R.transition.transition_settings_in);
        fragment.setEnterTransition(transitionIn);

        Transition transitionOut = TransitionInflater.from(App.getApp()).inflateTransition(R.transition.transition_settings_out);
        fragment.setReturnTransition(transitionOut);

        BoundsAndAlpha boundsAndAlpha = new BoundsAndAlpha();
        boundsAndAlpha.setDuration(400);
        fragment.setSharedElementEnterTransition(boundsAndAlpha);

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.app_ctrl_view, fragment);
        fragmentTransaction.addToBackStack(null);
        if (shareViews != null) {
            for (Pair<View, String> shareView : shareViews) {
                fragmentTransaction.addSharedElement(shareView.first, shareView.second);
            }
        }
        fragmentTransaction.commit();

    }
}
