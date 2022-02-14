/*
 * *
 *  * Created by SOURAV VERMA on 07/02/22, 1:16 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 07/02/22, 1:10 PM
 *
 */

package com.verma.android.widgets.text.showmore.ui;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorInt;

public class ShowMoreSpannable extends ClickableSpan {

    public final OnShowMoreSpannableClickListener listener;
    @ColorInt
    private final int pColor;
    private final boolean isUnderline;
    private final boolean isBold;

    public ShowMoreSpannable(OnShowMoreSpannableClickListener listener, boolean isUnderline, @ColorInt int pColor, boolean isBold) {
        this.listener = listener;
        this.isBold = isBold;
        this.isUnderline = isUnderline;
        this.pColor = pColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(pColor);
        ds.setFakeBoldText(isBold);
    }

    @Override
    public void onClick(View widget) {
        if (null != listener) {
            listener.onClick();
        }
    }

    public interface OnShowMoreSpannableClickListener {
        void onClick();
    }
}