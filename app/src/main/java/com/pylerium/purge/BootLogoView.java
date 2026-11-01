package com.pylerium.purge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/** A GPU-light, two-second brand reveal drawn at the device's native resolution. */
final class BootLogoView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap logo;
    private final ValueAnimator animator;
    private final Runnable finished;
    private float progress;

    BootLogoView(Context context, Runnable finished) {
        super(context); this.finished = finished;
        logo = BitmapFactory.decodeResource(getResources(), R.drawable.pylerium_logo);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setBackgroundColor(Color.rgb(5, 7, 9));
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1820); animator.setInterpolator(new DecelerateInterpolator(1.35f));
        animator.addUpdateListener(a -> { progress = (float) a.getAnimatedValue(); invalidate(); });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                animate().alpha(0f).setDuration(180).withEndAction(() -> { if (finished != null) finished.run(); }).start();
            }
        });
    }

    void start() { post(animator::start); }

    @Override protected void onDetachedFromWindow() {
        animator.cancel(); super.onDetachedFromWindow();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth(), h = getHeight(), cx = w * .5f, cy = h * .47f;
        float reveal = smooth(0f, .52f, progress), words = smooth(.38f, .76f, progress);
        float pulse = 1f + .025f * (float) Math.sin(progress * Math.PI * 3.2);

        glow.setShader(new RadialGradient(cx, cy, Math.min(w, h) * .48f,
                new int[]{Color.argb((int)(66 * reveal), 38, 216, 238), Color.argb((int)(22 * reveal), 255, 123, 28), Color.TRANSPARENT},
                new float[]{0f, .38f, 1f}, Shader.TileMode.CLAMP));
        canvas.drawCircle(cx, cy, Math.min(w, h) * .48f, glow);

        canvas.save(); canvas.translate(cx, cy - h * .045f); canvas.scale(pulse, pulse);
        float size = Math.min(w, h) * .19f;
        paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(Math.max(3f, size * .045f)); paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setShader(new LinearGradient(-size, -size, size, size, 0xff26d8ee, 0xffff7b1c, Shader.TileMode.CLAMP));
        paint.setAlpha((int)(255 * reveal));
        float sweep = 34f + 326f * reveal;
        canvas.drawArc(-size, -size, size, size, -90f, sweep, false, paint);

        paint.setShader(null); paint.setStyle(Paint.Style.FILL); paint.setAlpha((int)(255 * smooth(.10f, .60f, progress)));
        float imageSize = size * 1.62f; canvas.drawBitmap(logo, null,
                new android.graphics.RectF(-imageSize, -imageSize, imageSize, imageSize), paint); canvas.restore();

        Typeface face;
        try { face = Typeface.createFromAsset(getContext().getAssets(), "fonts/Onest.ttf"); }
        catch (RuntimeException ignored) { face = Typeface.create("sans-serif", Typeface.BOLD); }
        paint.setTypeface(face); paint.setTextAlign(Paint.Align.CENTER); paint.setStyle(Paint.Style.FILL);
        paint.setLetterSpacing(.22f); paint.setTextSize(Math.min(w, h) * .048f); paint.setColor(0xfff3f3f3); paint.setAlpha((int)(255 * words));
        canvas.drawText("PYLERIUM", cx, cy + size * 1.56f, paint);
        paint.setLetterSpacing(.32f); paint.setTextSize(Math.min(w, h) * .017f); paint.setColor(0xff8fa5af); paint.setAlpha((int)(220 * smooth(.53f, .85f, progress)));
        canvas.drawText("PYLERIUM / PURGE", cx, cy + size * 1.92f, paint);
    }

    private static float smooth(float start, float end, float value) {
        float x = Math.max(0f, Math.min(1f, (value - start) / (end - start))); return x * x * (3f - 2f * x);
    }
}
