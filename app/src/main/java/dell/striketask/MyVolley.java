package dell.striketask;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dell on 2/9/2016.
 */
public class MyVolley  {

    private static RequestQueue mRequestQueue;


    private MyVolley() {
        // no instances
    }


    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }


    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }


}
