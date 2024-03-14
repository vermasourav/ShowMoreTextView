/*
 * *
 *  * Created by SOURAV VERMA on 07/02/22, 1:10 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 07/02/22, 1:10 PM
 *
 */

package com.verma.android.widgets.text.showmore;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.verma.android.widgets.text.showmore.ui.ShowMoreSpannable;
import com.verma.android.widgets.text.showmore.ui.ShowMoreState;
import com.verma.android.widgets.text.showmore.ui.StateChangeListener;


/**
 * Show More Text View.
 */
public final class ShowMoreTextView extends AppCompatTextView {

    private ShowMoreTextBuilder builder;
    private StateChangeListener listener;

    public ShowMoreTextView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShowMoreTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        populateCustomValue(attrs);
    }

    public ShowMoreTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        populateCustomValue(attrs);

    }

    private void init(Context context) {
        builder = new ShowMoreTextBuilder(context);
    }

    private void populateCustomValue(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShowMoreTextView);
        builder = builder.setLabelUnderLine(typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_underline, true))
                .setLabelBold(typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_bold, true))
                .setLinkify(typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_linkify, true))
                .setExpandAnimation(typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_animation, true))
                .setLessLabel(typedArray.getString(R.styleable.ShowMoreTextView_show_less_text))
                .setMoreLabel(typedArray.getString(R.styleable.ShowMoreTextView_show_more_text))
                .setMoreLabelColor(typedArray.getColor(R.styleable.ShowMoreTextView_show_more_color, -1))
                .setLessLabelColor(typedArray.getColor(R.styleable.ShowMoreTextView_show_less_color, -1))
                .build();
        int type = typedArray.getColor(R.styleable.ShowMoreTextView_show_more_text_length_type, ShowMoreTextBuilder.TYPE_CHARACTER);
        int length = ShowMoreTextBuilder.MAX_CHARACTER;

        if (ShowMoreTextBuilder.TYPE_CHARACTER == type) {
            length = typedArray.getInt(R.styleable.ShowMoreTextView_show_more_max_line, ShowMoreTextBuilder.MAX_CHARACTER);
        } else if (ShowMoreTextBuilder.TYPE_LINE == type) {
            length = typedArray.getInt(R.styleable.ShowMoreTextView_show_more_max_line, ShowMoreTextBuilder.MAX_LINE);
        }
        builder.textLengthAndLengthType(length, type);
        builder.textClickable(typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_expand_click, true), typedArray.getBoolean(R.styleable.ShowMoreTextView_show_more_collapse_click, true));
        typedArray.recycle();

        addShowMore(getText(), false);
    }

    private void addShowMore(@NonNull CharSequence text, boolean isContentExpanded) {
        if (builder.textLengthType == ShowMoreTextBuilder.TYPE_CHARACTER) {
            if (text.length() <= builder.textLength) {
                setText(text);
                return;
            }
        } else if (builder.textLengthType == ShowMoreTextBuilder.TYPE_LINE) {
            setMaxLines(builder.textLength);
            setText(text);
        }

        post(() -> {
            CharSequence trimText = trimText(text);
            setText(trimText);
            if (TextUtils.isEmpty(trimText)) {
                return;
            }
            if (builder.textLengthType == ShowMoreTextBuilder.TYPE_LINE) {
                if (null != getLayout() && getLineCount() <= builder.textLength) {
                    setText(trimText);
                }
            }
            if (isContentExpanded) {
                addLess(trimText);
            } else {
                addMore(trimText);
            }
        });
    }

    private void addMore(@NonNull CharSequence trimText) {
        try {
            CharSequence newSubString;
            if (builder.textLengthType == ShowMoreTextBuilder.TYPE_LINE) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
                CharSequence subString = trimText.subSequence(getLayout().getLineStart(0), getLayout().getLineEnd(builder.textLength - 1));
                if (subString.toString().endsWith("\n")) {
                    int index = subString.toString().lastIndexOf("\n");
                    newSubString = (index > 0) ? subString.subSequence(0, index) : subString;
                } else {
                    int startRange = subString.length() - (builder.moreLabel.length() + 4 + lp.rightMargin / 6);
                    int endRange = subString.length();
                    if (startRange > 0) {
                        CharSequence remove = subString.subSequence(startRange, endRange);
                        int index = subString.toString().lastIndexOf(remove.toString());
                        newSubString = (index > 0) ? subString.subSequence(0, index) : subString;
                    } else {
                        newSubString = subString;
                    }
                }
            } else {
                newSubString = trimText.subSequence(0, builder.textLength);
            }

            ShowMoreSpannable showMoreSpannable = new ShowMoreSpannable(() -> {
                addLess(trimText);

                callBack(ShowMoreState.EXPEND);
            }, builder.labelUnderLine, builder.moreLabelColor, builder.labelBold);

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(newSubString).append("...").append(builder.moreLabel);
            SpannableString ss = new SpannableString(spannableStringBuilder);
            ss.setSpan(showMoreSpannable, ss.length() - builder.moreLabel.length(), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (builder.textClickableInExpand) {
                if (TextUtils.isEmpty(builder.moreLabel)) {
                    post(() -> setOnClickListener(view -> {
                        addLess(trimText);
                        callBack(ShowMoreState.EXPEND);
                    }));
                } else {
                    ShowMoreSpannable exceptMoreLabelClickableSpan = new ShowMoreSpannable(() -> {
                        addLess(trimText);
                        callBack(ShowMoreState.EXPEND);
                    }, false, getCurrentTextColor(), false);

                    ss.setSpan(exceptMoreLabelClickableSpan, 0, ss.length() - builder.moreLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            }
            setAnimation();
            setText(ss);
            if (builder.enableLinkify) {
                //TODO Depricated
                //Linkify.addLinks(this, Linkify.ALL);
            }
            setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            //Do Nothing
            e.printStackTrace();
        }
    }

    private void callBack(ShowMoreState state) {
        if (null != listener) {
            listener.onStateChange(state);
        }
    }

    private void addLess(@NonNull CharSequence trimText) {
        try {
            setMaxLines(Integer.MAX_VALUE);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(trimText);
            if (!TextUtils.isEmpty(builder.lessLabel)) {
                spannableStringBuilder.append("...").append(builder.lessLabel);
            }
            SpannableString ss = SpannableString.valueOf(spannableStringBuilder);

            ShowMoreSpannable clickableSpan = new ShowMoreSpannable(() -> {
                addMore(trimText);
                callBack(ShowMoreState.COLLAPSE);
            }, builder.labelUnderLine, builder.lessLabelColor, builder.labelBold);

            if (!TextUtils.isEmpty(builder.lessLabel)) {
                ss.setSpan(clickableSpan, ss.length() - builder.lessLabel.length(), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


            if (builder.textClickableInCollapse) {
                if (TextUtils.isEmpty(builder.lessLabel)) {
                    post(() -> setOnClickListener(view -> {
                        addMore(trimText);
                        callBack(ShowMoreState.COLLAPSE);

                    }));
                } else {
                    ShowMoreSpannable lessLabelSpan = new ShowMoreSpannable(() -> {
                        addMore(trimText);
                        callBack(ShowMoreState.COLLAPSE);
                    }, false, getCurrentTextColor(), false);

                    ss.setSpan(lessLabelSpan, 0, ss.length() - builder.lessLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            }
            setAnimation();
            setText(ss);
            if (builder.enableLinkify) {
                //TODO Depricated
               // Linkify.addLinks(this, Linkify.ALL);
            }
            setMovementMethod(LinkMovementMethod.getInstance());

        } catch (Exception e) {
            //DO Nothing
            e.printStackTrace();
        }
    }

    private void setAnimation() {
        if (builder.expandAnimation) {
            LayoutTransition layoutTransition = new LayoutTransition();
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
            ((ViewGroup) getParent()).setLayoutTransition(layoutTransition);
        }
    }

    private CharSequence trimText(@NonNull CharSequence pText) {
        int length = pText.length();
        int trimmedLength = TextUtils.getTrimmedLength(pText);
        if (length > trimmedLength) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(pText);
            int start = 0;
            while (start < length && stringBuilder.charAt(start) <= ' ') {
                start++;
            }
            stringBuilder.delete(0, start);
            length -= start;
            int end = length;
            while (end >= 0 && stringBuilder.charAt(end - 1) <= ' ') {
                end--;
            }
            stringBuilder.delete(end, length);
            return stringBuilder;
        }
        return pText;
    }

    /**
     * Update text content.
     *
     * @param charSequence      char sequence
     * @param isContentExpanded is content expanded
     */
    public void updateText(CharSequence charSequence, boolean isContentExpanded) {
        addShowMore(charSequence, isContentExpanded);
    }

    /**
     * Sets state change listener.
     *
     * @param listener listener
     */
    public void setStateChangeListener(StateChangeListener listener) {
        this.listener = listener;
    }

}
