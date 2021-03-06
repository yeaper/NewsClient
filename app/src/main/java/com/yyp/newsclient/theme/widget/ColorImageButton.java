package com.yyp.newsclient.theme.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

public class ColorImageButton extends android.support.v7.widget.AppCompatImageButton implements ColorUiInterface {

    private int attr_drawable = -1;

    public ColorImageButton(Context context) {
        super(context);
    }

    public ColorImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_drawable = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    public ColorImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_drawable = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_drawable != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_drawable);
        }
    }
}
