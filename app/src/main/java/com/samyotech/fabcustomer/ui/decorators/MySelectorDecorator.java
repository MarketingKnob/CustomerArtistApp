package com.samyotech.fabcustomer.ui.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.samyotech.fabcustomer.R;


public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private CalendarDay day;

    public MySelectorDecorator(Context context, CalendarDay day) {
        this.day = day;
        drawable = context.getDrawable(R.drawable.my_selector);
//                getResources().getDrawable(R.drawable.my_selector);
    }

    public MySelectorDecorator(Context context) {
        drawable = context.getDrawable(R.drawable.my_selector);
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
