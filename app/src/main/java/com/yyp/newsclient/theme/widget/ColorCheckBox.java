package com.yyp.newsclient.theme.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

public class ColorCheckBox extends android.support.v7.widget.AppCompatCheckBox implements ColorUiInterface {

    private int attr_background = -1;

    public ColorCheckBox(Context context) {
        super(context);
    }

    public ColorCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
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
