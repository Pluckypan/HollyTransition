package engineer.echo.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import engineer.echo.transition.R;
import engineer.echo.transition.cmpts.context.BaseFragment;

/**
 * GalleryDetailFragment.java
 * Info: 详情页
 * Created by Plucky(plucky@echo.engineer) on 2019/6/13 - 10:30 AM
 * more about me: http://www.1991th.com
 */

public class GalleryDetailFragment extends BaseFragment {

    public static final String KEY_TRANSITION = "key_for_transition";

    public static void gotoFragment(Fragment fragment, View shareView, String shareName) {
        Fragment target = GalleryDetailFragment.newInstance(shareName);
        target.setSharedElementEnterTransition(new ChangeBounds());
        target.setSharedElementReturnTransition(new ChangeBounds());
        fragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.app_ctrl_view, target)
                .addSharedElement(shareView, shareName)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public GalleryDetailFragment() {

    }

    public static GalleryDetailFragment newInstance(String transitionName) {
        GalleryDetailFragment fragment = new GalleryDetailFragment();
        Bundle data = new Bundle();
        data.putString(KEY_TRANSITION, transitionName);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_detail, container, false);
        ImageView imageView = view.findViewById(R.id.iv_gallery_detail_app);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ViewCompat.setTransitionName(imageView, getArguments().getString(KEY_TRANSITION));
        return view;
    }
}
