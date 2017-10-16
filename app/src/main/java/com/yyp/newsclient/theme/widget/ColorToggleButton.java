package com.yyp.newsclient.theme.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ToggleButton;

import com.yyp.newsclient.theme.ColorUiInterface;
import com.yyp.newsclient.theme.util.ViewAttributeUtil;

public class ColorToggleButton extends ToggleButton implements ColorUiInterface {

    private int attr_textAppearance = -1;
    private int attr_background = -1;

    public ColorToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppearance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppearance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_textAppearance != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
        if(attr_background != -1) {
            ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppearance);
        }

    }
}
