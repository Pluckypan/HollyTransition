package engineer.echo.transition.widget.transition;

import android.animation.Animator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * BoundsAndAlpha
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午11:36.
 * more about me: http://www.1991th.com
 */

public class BoundsAndAlpha extends ChangeBounds {

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }

}
