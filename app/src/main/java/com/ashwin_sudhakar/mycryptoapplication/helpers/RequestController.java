package com.ashwin_sudhakar.mycryptoapplication.helpers;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestController extends Application {

    public static final String TAG = RequestController.class.getSimpleName();

    private RequestQueue requestQueue;

    private static RequestController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static synchronized RequestController getInstance()
    {
        return instance;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag)? TAG : tag);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
//        req.setRetryPolicy(new DefaultRetryPolicy(10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
        req.setRetryPolicy(new DefaultRetryPolicy( 0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag)
    {
        if(requestQueue != null)
        {
            requestQueue.cancelAll(tag);
        }
    }

}
