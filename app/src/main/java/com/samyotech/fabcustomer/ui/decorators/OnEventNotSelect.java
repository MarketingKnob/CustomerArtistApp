package com.samyotech.fabcustomer.ui.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.samyotech.fabcustomer.R;

public class OnEventNotSelect implements DayViewDecorator {

    private final Drawable drawable;
    private CalendarDay day;

    public OnEventNotSelect(Context context, CalendarDay day) {
        this.day = day;
        drawable = context.getResources().getDrawable(R.drawable.event_selector);
    }

    public OnEventNotSelect(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.event_selector);
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
