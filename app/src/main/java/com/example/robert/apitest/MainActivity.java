package com.example.robert.apitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.example.robert.apitest.model.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String AUTHENTIATION="X-Authentication";
    private static final String HOST = "http://172.16.14.22:8080";

    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnList = (Button) findViewById(R.id.btnList);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetBuilder builder = OkHttpUtils.get();
                builder.addHeader(AUTHENTIATION,token);
                builder.url(HOST+"/user/list");
                builder.build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        try {
                            JSONArray array=new JSONArray(s);
                            String json=array.toString(4);
                            Log.e("结果",json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public String parseNetworkResponse(Response response) throws IOException {
                        return super.parseNetworkResponse(response);
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PostFormBuilder postFormBuilder = OkHttpUtils.post();
                postFormBuilder.url(HOST + "/sign");
                postFormBuilder.addParams("username","robert");
                postFormBuilder.addParams("password","123456");

                postFormBuilder.build().execute(new StringCallback() {

                    @Override
                    public String parseNetworkResponse(Response response) throws IOException {
                        token=response.header(AUTHENTIATION);
                        Log.e("TOKEN",token);
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("-------",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        Log.e("=====", s);
                    }

                });

            }
        });
    }
}