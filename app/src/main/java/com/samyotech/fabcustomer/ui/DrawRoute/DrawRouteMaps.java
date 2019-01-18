package com.samyotech.fabcustomer.ui.DrawRoute;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ocittwo on 11/14/16.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class DrawRouteMaps {

    private static com.samyotech.fabcustomer.ui.DrawRoute.DrawRouteMaps instance;
    private Context context;

    public static com.samyotech.fabcustomer.ui.DrawRoute.DrawRouteMaps getInstance(Context context) {
        instance = new com.samyotech.fabcustomer.ui.DrawRoute.DrawRouteMaps();
        instance.context = context;
        return instance;
    }

    public com.samyotech.fabcustomer.ui.DrawRoute.DrawRouteMaps draw(LatLng origin, LatLng destination, GoogleMap googleMap){
        String url_route = FetchUrl.getUrl(origin, destination,context);
        DrawRoute drawRoute = new DrawRoute(googleMap);
        drawRoute.execute(url_route);
        return instance;
    }

    public static Context getContext() {
        return instance.context;
    }
}
