package com.example.yvtc.my040603;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    InputStream inputStream;
    TextView tv;
    ByteArrayOutputStream os;
    byte[] buffer;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView);

        os = new ByteArrayOutputStream();
        img = (ImageView)findViewById(R.id.imageView);
        buffer = new byte[64];
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("https://uc.udn.com.tw/photo/2017/04/06/1/3364635.jpg");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    inputStream = conn.getInputStream();

                    int readSize = 0;
                    while ((readSize=inputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, readSize);
                    }
                    byte[] result = os.toByteArray();
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(result,0,result.length);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           img.setImageBitmap(bitmap);
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
