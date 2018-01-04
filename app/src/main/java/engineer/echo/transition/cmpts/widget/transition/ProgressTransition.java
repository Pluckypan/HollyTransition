package engineer.echo.transition.cmpts.widget.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.IntProperty;
import android.util.Property;
import android.view.ViewGroup;

import ch.halcyon.squareprogressbar.SquareProgressBar;

/**
 * ProgressTransition
 * Created by Plucky<plucky@echo.engineer> on 2018/1/4 - 17:19
 * more about me: http://www.1991th.com
 */

public class ProgressTransition extends Transition {

    private static final String TAG = "ProgressTransition";
    /**
     * Property is like a helper that contain setter and getter in one place
     */
    private static final Property<SquareProgressBar, Integer> PROGRESS_PROPERTY =
            new IntProperty<SquareProgressBar>(TAG) {

                @Override
                public void setValue(SquareProgressBar progressBar, int value) {
                    progressBar.setProgress(value);
                }

                @Override
                public Integer get(SquareProgressBar progressBar) {
                    Double pro = progressBar.getProgress();
                    return pro.intValue();
                }
            };

    /**
     * Internal name of property. Like a intent bundles
     */
    private static final String PROPNAME_PROGRESS = "ProgressTransition:progress";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof SquareProgressBar) {
            // save current progress in the values map
            SquareProgressBar progressBar = ((SquareProgressBar) transitionValues.view);
            transitionValues.values.put(PROPNAME_PROGRESS, progressBar.getProgress());
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues != null && endValues != null && endValues.view instanceof SquareProgressBar) {
            SquareProgressBar progressBar = (SquareProgressBar) endValues.view;
            int start = ((Double) startValues.values.get(PROPNAME_PROGRESS)).intValue();
            int end = ((Double) endValues.values.get(PROPNAME_PROGRESS)).intValue();
            if (start != end) {
                // first of all we need to apply the start value, because right now
                // the view is have end value
                progressBar.setProgress(start);
                // create animator with our progressBar, property and end value
                return ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, end);
            }
        }
        return null;
    }
}