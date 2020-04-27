package am.networkconnectivity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkAccess extends AsyncTask<Void, Void, Void> {

    private InternetCheckListener listener;

    @Override
    protected Void doInBackground(Void... voids) {
        listener.onComplete(hasInternetAccess());
        return null;
    }

    void isInternetConnectionAvailable(InternetCheckListener l) {
        this.listener = l;
        execute();
    }


    private boolean hasInternetAccess() {
        try {
            HttpURLConnection http = (HttpURLConnection) new URL("https://www.google.com").openConnection();
            http.setRequestProperty("User-Agent", "Android");
            http.setRequestProperty("Connection", "close");
            http.setConnectTimeout(1500);
            http.connect();

            return (http.getResponseCode() == 200);
        } catch (IOException e) {
            Log.e(getClass().getName(), "Error checking internet connection " + e.getMessage());
        }
        return false;
    }


    public interface InternetCheckListener {
        void onComplete(boolean isConnected);
    }

}