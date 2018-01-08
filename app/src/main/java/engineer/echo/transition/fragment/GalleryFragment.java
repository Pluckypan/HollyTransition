package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import engineer.echo.transition.R;
import engineer.echo.transition.cmpts.context.BaseFragment;
import engineer.echo.transition.cmpts.utils.CommonUtil;
import engineer.echo.transition.cmpts.widget.reflect.ReflectItemView;

import static engineer.echo.transition.Constants.TRANSITION_TIME;
import static engineer.echo.transition.fragment.AppCtrlFragment.SHARE_NAME_LEFT;

/**
 * GalleryFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:28.
 * more about me: http://www.1991th.com
 */

public class GalleryFragment extends BaseFragment {

    private ReflectItemView mShareView;

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
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
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mShareView = view.findViewById(R.id.app_gallery_share_btn);
        ViewCompat.setTransitionName(mShareView, SHARE_NAME_LEFT);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public static void gotoPage(FragmentManager manager, Pair<View, String> shareView) {
        GalleryFragment fragment = GalleryFragment.newInstance();
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);

        ChangeBounds boundsIn = new ChangeBounds();
        boundsIn.setDuration(TRANSITION_TIME);
        // TODO: Added By Plucky 2018/1/8 11:39 路径动画需要API版本>=LOLLIPOP
        if (CommonUtil.isOverLollipop()) {
            boundsIn.setPathMotion(new ArcMotion());
        }

        fragment.setSharedElementEnterTransition(boundsIn);


        ChangeBounds boundsOut = new ChangeBounds();
        boundsOut.setDuration(TRANSITION_TIME);
        // TODO: Added By Plucky 2018/1/8 11:39 路径动画需要API版本>=LOLLIPOP
        if (CommonUtil.isOverLollipop()) {
            boundsIn.setPathMotion(new ArcMotion());
        }
        fragment.setSharedElementReturnTransition(boundsOut);

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.app_ctrl_view, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.addSharedElement(shareView.first, shareView.second);

        fragmentTransaction.commit();
    }
}
