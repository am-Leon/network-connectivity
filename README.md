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

## License

```text
MIT License

Copyright (c) 2020 am-Leon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```