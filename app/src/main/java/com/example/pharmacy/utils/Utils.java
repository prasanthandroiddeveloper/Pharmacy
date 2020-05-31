package com.example.pharmacy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Utils {

   public   static class internetConnection {

         @RequiresApi(api = Build.VERSION_CODES.M)
         public static boolean checkConnection(Context context) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr != null) {
                NetworkCapabilities capabilities = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());

                if (capabilities != null) {

                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true;
                    } else
                        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                }
            }
            return false;
        }

     }

}
