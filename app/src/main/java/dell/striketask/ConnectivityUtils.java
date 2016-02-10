package dell.striketask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dell on 2/9/2016.
 */
public class ConnectivityUtils {
    private static final String LOG_TAG = "ConnectivityUtils";

    /**
     * @param pContext
     * @return
     */
    public static boolean isNetworkEnabled(Context pContext) {
        NetworkInfo activeNetwork = getActiveNetwork(pContext);
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static NetworkInfo getActiveNetwork(Context pContext) {
        ConnectivityManager conMngr = (ConnectivityManager) pContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMngr == null ? null : conMngr.getActiveNetworkInfo();
    }
}
