package edu.ozyegin.notisode;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.animation.PathInterpolator;

/**
 * Created by Batuhan on 3.6.2015.
 */
public class Utils {
    public final static int COLOR_ANIMATION_DURATION = 1000;

    public static void animateViewColor(View v, int startColor, int endColor) {

        ObjectAnimator animator = ObjectAnimator.ofObject(v, "backgroundColor",
                new ArgbEvaluator(), startColor, endColor);

        if (Build.VERSION.SDK_INT >= 21) {
            animator.setInterpolator(new PathInterpolator(0.4f, 0f, 1f, 1f));
        }
        animator.setDuration(COLOR_ANIMATION_DURATION);
        animator.start();
    }

    public static Palette.Swatch getSwatch(Bitmap bitmap) {

        if (bitmap != null && !bitmap.isRecycled()) {
            Palette palette = PaletteTransformation.getPalette(bitmap);

            if (palette != null) {
                Palette.Swatch s = palette.getVibrantSwatch();
                if (s == null) {
                    s = palette.getDarkVibrantSwatch();
                }
                if (s == null) {
                    s = palette.getLightVibrantSwatch();
                }
                if (s == null) {
                    s = palette.getMutedSwatch();
                }

                if (s != null) {
                    // just delete the reference again.
                    bitmap = null;
                    return s;
                }
            }
        }

        return null;
    }
}
