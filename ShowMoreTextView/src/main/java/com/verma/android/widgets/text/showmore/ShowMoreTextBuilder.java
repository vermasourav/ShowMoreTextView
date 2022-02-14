/*
 * *
 *  * Created by SOURAV VERMA on 07/02/22, 1:10 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 07/02/22, 1:10 PM
 *
 */

package com.verma.android.widgets.text.showmore;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

/**
 * The type Builder.
 */
public class ShowMoreTextBuilder {
    public static final int MAX_LINE = 5;
    public static final int MAX_CHARACTER = 100;
    public static final int TYPE_LINE = 1;
    public static final int TYPE_CHARACTER = 150;
    public final Context context;

    int textLength = MAX_CHARACTER;
    int textLengthType = TYPE_CHARACTER;
    String moreLabel;
    String lessLabel;
    @ColorInt
    int moreLabelColor;
    @ColorInt
    int lessLabelColor;
    boolean labelUnderLine;
    boolean labelBold;
    boolean expandAnimation;
    boolean textClickableInExpand;
    boolean textClickableInCollapse;
    boolean enableLinkify;


    public ShowMoreTextBuilder(Context context) {
        this.context = context;
        moreLabelColor = ContextCompat.getColor(context, R.color.show_more);
        lessLabelColor = ContextCompat.getColor(context, R.color.show_less);
        moreLabel = context.getString(R.string.show_more);
        lessLabel = context.getString(R.string.show_less);
        textLengthAndLengthType(MAX_LINE, TYPE_LINE);
        textClickable(true, true);
        expandAnimation = true;
        labelBold = true;
        labelUnderLine = true;

    }

    public void textLengthAndLengthType(int length, int textLengthType) {
        this.textLength = length;
        this.textLengthType = textLengthType;
    }

    public void textClickable(boolean textClickableInExpand, boolean textClickableInCollapse) {
        this.textClickableInExpand = textClickableInExpand;
        this.textClickableInCollapse = textClickableInCollapse;
    }

    public ShowMoreTextBuilder setMoreLabelColor(@ColorInt int moreLabelColor) {
        if (-1 == moreLabelColor) {
            this.moreLabelColor = ContextCompat.getColor(context, R.color.show_more);
        } else {
            this.moreLabelColor = moreLabelColor;
        }
        return this;
    }

    public ShowMoreTextBuilder setLessLabelColor(@ColorInt int lessLabelColor) {
        if (-1 == lessLabelColor) {
            this.lessLabelColor = ContextCompat.getColor(context, R.color.show_less);
        } else {
            this.lessLabelColor = lessLabelColor;
        }
        return this;
    }

    public ShowMoreTextBuilder setLabelBold(boolean labelBold) {
        this.labelBold = labelBold;
        return this;
    }

    public ShowMoreTextBuilder setLabelUnderLine(boolean labelUnderLine) {
        this.labelUnderLine = labelUnderLine;
        return this;
    }

    public ShowMoreTextBuilder setExpandAnimation(boolean expandAnimation) {
        this.expandAnimation = expandAnimation;
        return this;
    }

    public ShowMoreTextBuilder setMoreLabel(String moreLabel) {
        if (null == moreLabel) {
            this.moreLabel = context.getString(R.string.show_more);
        } else {
            this.moreLabel = moreLabel;
        }
        return this;
    }

    public ShowMoreTextBuilder setLessLabel(String lessLabel) {
        if (null == lessLabel) {
            this.lessLabel = context.getString(R.string.show_less);
        } else {
            this.lessLabel = lessLabel;
        }
        return this;
    }

    public ShowMoreTextBuilder setLinkify(boolean enableLinkify) {
        this.enableLinkify = enableLinkify;
        return this;
    }

    public ShowMoreTextBuilder build() {
        return this;
    }
}
