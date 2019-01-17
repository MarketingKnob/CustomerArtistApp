package com.samyotech.fabcustomer.ui.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.samyotech.fabcustomer.R;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private CalendarDay day;

    public OneDayDecorator(Context context, CalendarDay day) {
        this.day = day;
        drawable = context.getResources().getDrawable(R.drawable.rectangle_red);
    }

    public OneDayDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.rectangle_red);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
