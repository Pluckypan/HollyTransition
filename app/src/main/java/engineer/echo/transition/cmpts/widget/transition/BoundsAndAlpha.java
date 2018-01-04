package engineer.echo.transition.cmpts.widget.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import engineer.echo.transition.R;

/**
 * BoundsAndAlpha
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午11:36.
 * more about me: http://www.1991th.com
 */

public class BoundsAndAlpha extends ChangeBounds {
    private static final String TAG = "BoundsAndAlpha";

    //是否为入场动画
    private boolean mIn;

    public BoundsAndAlpha(boolean mIn) {
        this.mIn = mIn;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        Log.d(TAG, "captureStartValues: transitionValues=" + transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        Log.d(TAG, "captureEndValues: transitionValues=" + transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        //对进入的页面的元素进行alpha变化  进入时，渐隐 返回时，渐现
        View endView = endValues != null ? endValues.view : null;
        View startView = startValues != null ? startValues.view : null;
        View shareView = mIn ? endView : startView;
        Animator animator = super.createAnimator(sceneRoot, startValues, endValues);
        if (animator != null) {
            if (animator instanceof AnimatorSet) {
                AnimatorSet animatorSet = (AnimatorSet) animator;
                Animator first = animatorSet.getChildAnimations().get(0);
                executeAlphaChange(first, shareView);
            } else {
                executeAlphaChange(animator, shareView);
            }
        }
        return animator;
    }


    /**
     * 实现共享元素在做形变动画的同时做alpha变化
     *
     * @param animator Animator
     * @param view     View
     */
    private void executeAlphaChange(Animator animator, final View view) {
        if (animator == null || view == null) return;
        if (!(animator instanceof ObjectAnimator)) return;
        boolean needChangeAlpha = view.getTag(R.id.key_for_alpha_change_when_share_element) != null;
        if (!needChangeAlpha) return;
        //让共享元素在形变的同时 alpha同时发生变化
        //如果是入场动画 则从1~0
        //如果是退场动画 则从0~1
        view.setAlpha(mIn ? 1 : 0);
        ObjectAnimator objectAnimator = (ObjectAnimator) animator;
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fract = animation.getAnimatedFraction();
                float alpha = mIn ? (1 - fract) : fract;
                view.setAlpha(alpha);
            }
        });
    }

}
