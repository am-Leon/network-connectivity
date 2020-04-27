# Network Connectivity

Leon Network Connectivity is an Android library written to track the status of your Internet connection.

## Installation

1- Add this library as a dependency in your app's build.project file.

```groovy

allprojects {  
      repositories {  
         maven { url 'https://jitpack.io' }  
      }  
   }  
   
   ```

2- Add the dependency.

```groovy

    implementation 'com.github.am-Leon:network-connectivity:v1.0.0'

```

## Usage

```java
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

```

