package am.networkconnectivity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class NetworkConnectivity extends LiveData<NetworkConnectivity.NetworkStatus> implements NetworkAccess.InternetCheckListener {

    private Context context;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    public enum NetworkStatus {OnLost, OnWaiting, OnConnected}

    public NetworkConnectivity(Context context) {
        this.context = context;
        init();
    }


    private void init() {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NotNull Network network) {
                super.onAvailable(network);
                new NetworkAccess().isInternetConnectionAvailable(NetworkConnectivity.this);
            }

            @Override
            public void onLost(@NotNull Network network) {
                super.onLost(network);
                Log.d(getClass().getName(), "Network is Lost...");
                postValue(NetworkStatus.OnLost);
            }
        };
    }


    @Override
    protected void onActive() {
        super.onActive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            connectivityManager.registerDefaultNetworkCallback(networkCallback);

        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            lollipopNetworkAvailableRequest();

        else
            context.registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }


    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            connectivityManager.unregisterNetworkCallback(networkCallback);
        else
            context.unregisterReceiver(networkReceiver);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lollipopNetworkAvailableRequest() {
        NetworkRequest.Builder builder = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }


    @Override
    public void onComplete(boolean isConnected) {
        if (isConnected) {
            Log.d(getClass().getName(), "Network is Connected...");
            postValue(NetworkStatus.OnConnected);

        } else {
            Log.d(getClass().getName(), "Network is Waiting...");
            postValue(NetworkStatus.OnWaiting);

            Observable.timer(5000, TimeUnit.MILLISECONDS)
                    .map(aLong -> {
                        new NetworkAccess().isInternetConnectionAvailable(this);
                        return true;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }


    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

}