package am.networkconnectivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NetworkConnectivity(this).observe(this, new Observer<NetworkConnectivity.NetworkStatus>() {
            @Override
            public void onChanged(NetworkConnectivity.NetworkStatus networkStatus) {
                switch (networkStatus) {
                    case OnConnected:
                        Toast.makeText(MainActivity.this, "Connection is back", Toast.LENGTH_SHORT).show();
                        break;
                    case OnWaiting:
                        Toast.makeText(MainActivity.this, "Connection is waiting", Toast.LENGTH_SHORT).show();
                        break;
                    case OnLost:
                        Toast.makeText(MainActivity.this, "Connection is lost", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}
