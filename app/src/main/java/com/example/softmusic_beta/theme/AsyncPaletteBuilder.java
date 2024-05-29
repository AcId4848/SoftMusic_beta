package com.example.softmusic_beta.theme;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.ColorRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.palette.graphics.Palette;

import com.example.softmusic_beta.R;
import com.example.softmusic_beta.theme.interfaces.PaletteStateListener;

import java.util.HashMap;

public class AsyncPaletteBuilder {
    private interface Action {void onValueAnimated(int value); }

    private enum PALETTE_TYPE { VIBRANT, VIBRANT_DARK, VIBRANT_LIGHT }

    private final int ANIM_DURATION = 680;

    private final HashMap<PALETTE_TYPE, ValueAnimator> mAnimators = new HashMap<>();

    private final HashMap<PALETTE_TYPE, Integer> mDefColors = new HashMap<>();
    private final HashMap<PALETTE_TYPE, Integer> mPrevColors = new HashMap<>();

    private final Context mContext;

    private final PaletteStateListener mStateListener;

    public AsyncPaletteBuilder(Context context, PaletteStateListener stateListener) {
        this.mContext = context;
        this.mStateListener = stateListener;

        this.mDefColors.put(PALETTE_TYPE.VIBRANT, getColor(R.color.default_vibrant_color));
        this.mDefColors.put(PALETTE_TYPE.VIBRANT_DARK, getColor(R.color.default_vibrant_dark_color));
        this.mDefColors.put(PALETTE_TYPE.VIBRANT_LIGHT, getColor(R.color.default_vibrant_light_color));

        this.mPrevColors.put(PALETTE_TYPE.VIBRANT, getColor(R.color.default_vibrant_color));
        this.mPrevColors.put(PALETTE_TYPE.VIBRANT_DARK, getColor(R.color.default_vibrant_dark_color));
        this.mPrevColors.put(PALETTE_TYPE.VIBRANT_LIGHT, getColor(R.color.default_vibrant_light_color));
    }

    public int getColor(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(this.mContext.getResources(), colorRes,  this.mContext.getTheme());
    }

    private ValueAnimator getColorAnimator(int fromColor, int toColor) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(
            (TypeEvaluator) new ArgbEvaluator(),
            new Object[] { Integer.valueOf(fromColor), Integer.valueOf(toColor)});
        valueAnimator.setDuration(ANIM_DURATION);
        return valueAnimator;
    }

    private void onStartColorAnimation(PALETTE_TYPE type, Action action, int fromColor, int toColor) {
        ValueAnimator animator;
        if (this.mAnimators.containsKey(type) && this.mAnimators.get(type) != null) {
            animator = this.mAnimators.get(type);
            animator.end();
            animator.removeAllUpdateListeners();
            animator.removeAllListeners();
        }

        animator = getColorAnimator(fromColor, toColor);

        this.mAnimators.put(type, animator);

        animator.addUpdateListener(valueAnimator -> {
            action.onValueAnimated((int) valueAnimator.getAnimatedValue());
        });

        animator.start();
    }

    public void onStartAnimation(Bitmap art) {
        if (art != null) {
            Palette.from(art).generate(palette -> {
                onStartColorAnimation(
                        PALETTE_TYPE.VIBRANT,
                        value -> {
                            this.mStateListener.onUpdateVibrantColor(value);
                            this.mPrevColors.put(PALETTE_TYPE.VIBRANT, value);
                        },
                        this.mPrevColors.get(PALETTE_TYPE.VIBRANT),
                        palette.getVibrantColor(this.mDefColors.get(PALETTE_TYPE.VIBRANT)));
                onStartColorAnimation(
                        PALETTE_TYPE.VIBRANT_DARK,
                        value -> {
                            this.mStateListener.onUpdateVibrantDarkColor(value);
                            this.mPrevColors.put(PALETTE_TYPE.VIBRANT_DARK, value);
                        },
                        this.mPrevColors.get(PALETTE_TYPE.VIBRANT_DARK),
                        palette.getDarkVibrantColor(this.mDefColors.get(PALETTE_TYPE.VIBRANT_DARK)));
                onStartColorAnimation(
                        PALETTE_TYPE.VIBRANT_LIGHT,
                        value -> {
                            this.mStateListener.onUpdateVibrantDarkColor(value);
                            this.mPrevColors.put(PALETTE_TYPE.VIBRANT_LIGHT, value);
                        },
                        this.mPrevColors.get(PALETTE_TYPE.VIBRANT_LIGHT),
                        palette.getDarkVibrantColor(this.mDefColors.get(PALETTE_TYPE.VIBRANT_LIGHT)));
            });
        } else {
            onStartColorAnimation(
                    PALETTE_TYPE.VIBRANT,
                    value -> {
                        this.mStateListener.onUpdateVibrantColor(value);
                        this.mPrevColors.put(PALETTE_TYPE.VIBRANT, value);
                    },
                    this.mPrevColors.get(PALETTE_TYPE.VIBRANT),
                    this.mDefColors.get(PALETTE_TYPE.VIBRANT));
            onStartColorAnimation(
                    PALETTE_TYPE.VIBRANT_DARK,
                    value -> {
                        this.mStateListener.onUpdateVibrantDarkColor(value);
                        this.mPrevColors.put(PALETTE_TYPE.VIBRANT_DARK, value);
                    },
                    this.mPrevColors.get(PALETTE_TYPE.VIBRANT_DARK),
                    this.mDefColors.get(PALETTE_TYPE.VIBRANT_DARK));
            onStartColorAnimation(
                    PALETTE_TYPE.VIBRANT_LIGHT,
                    value -> {
                        this.mStateListener.onUpdateVibrantDarkColor(value);
                        this.mPrevColors.put(PALETTE_TYPE.VIBRANT_LIGHT, value);
                    },
                    this.mPrevColors.get(PALETTE_TYPE.VIBRANT_LIGHT),
                    this.mDefColors.get(PALETTE_TYPE.VIBRANT_LIGHT));
        }
    }
}
