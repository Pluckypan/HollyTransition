package engineer.echo.transition.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * AppDisplayFragment
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:28.
 * more about me: http://www.1991th.com
 */

public class RoundAngleFrameLayout extends FrameLayout {


    private float mRadius;
    private Paint mRoundPaint;
    private Paint mImagePaint;

    public RoundAngleFrameLayout(Context context) {
        this(context, null);
    }

    public RoundAngleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundAngleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRoundPaint = new Paint();
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mRadius = canvas.getWidth() / 2;
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        if (mRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mRadius);
            path.lineTo(0, 0);
            path.lineTo(mRadius, 0);
            path.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, 0, width,
                    mRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - mRadius);
            path.lineTo(0, height);
            path.lineTo(mRadius, height);
            path.arcTo(new RectF(0, height - 2 * mRadius,
                    mRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, height - 2
                    * mRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

}