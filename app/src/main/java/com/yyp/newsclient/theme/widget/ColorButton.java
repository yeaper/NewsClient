package com.yyp.newsclient.theme.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

public class ColorButton extends android.support.v7.widget.AppCompatButton implements ColorUiInterface {

    private int attr_background = -1;
    private int attr_textAppreance = -1;
    private int attr_drawableTop = -1;

    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
        this.attr_drawableTop = ViewAttributeUtil.getDrawableTopAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_background!=-1)
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        if(attr_textAppreance!=-1)
        ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppreance);
        if(attr_drawableTop!=-1)
        ViewAttributeUtil.applyTextAppearance(this, themeId, attr_drawableTop);
    }
}
