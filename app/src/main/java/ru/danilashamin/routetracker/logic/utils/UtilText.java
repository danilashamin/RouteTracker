package ru.danilashamin.routetracker.logic.utils;

import android.widget.TextView;

import ru.danilashamin.routetracker.R;

public final class UtilText {

    public boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public boolean isBlank(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public void setTextOrElseUndefined(TextView tv, String text){
        if (isBlank(text)) {
            tv.setText(R.string.undefined);
        } else {
            tv.setText(text);
        }
    }
}
