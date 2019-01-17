package com.samyotech.fabcustomer.ui.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.samyotech.fabcustomer.R;

import java.util.Date;

public class EventDecoratorColor implements DayViewDecorator {

    private Drawable drawable;
    private final CalendarDay day;
    private static final String TAG = "EventDecoratorColor";


    public EventDecoratorColor(MaterialCalendarView view, Date date, int color, String status) {
        this.day = CalendarDay.from(date);

        Log.d(TAG, "EventDecoratorColor: "+status);
        if(status.equals("0")){
            drawable = view.getContext().getResources().getDrawable(R.drawable.event_selector);

        }else if (status.equals("1")){
            this.drawable = view.getContext().getResources().getDrawable(R.drawable.green_selector);

        }
        else if(status.equals("2")){
            this.drawable = view.getContext().getResources().getDrawable(R.drawable.red_selector);

        }

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Log.e("day", String.valueOf(this.day) + "=" + day);
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    private static Drawable createTintedDrawable(Context context, int color) {
        return applyTint(createBaseDrawable(context), color);
    }

    private static Drawable applyTint(Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    private static Drawable createBaseDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.my_selector);
    }


}
