package com.example.bradperkins.project3.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by bradperkins on 12/10/15.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewFactory(getApplicationContext());
    }

}
