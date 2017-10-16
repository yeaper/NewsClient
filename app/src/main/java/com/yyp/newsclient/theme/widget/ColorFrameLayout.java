package com.yyp.newsclient.theme.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

public class ColorFrameLayout extends FrameLayout implements ColorUiInterface {

    private int attr_background = -1;

    public ColorFrameLayout(Context context) {
        super(context);
    }

    public ColorFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}
