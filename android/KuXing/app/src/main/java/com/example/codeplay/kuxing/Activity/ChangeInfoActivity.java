package com.example.codeplay.kuxing.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Adapter.UserComAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.NormalPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChangeInfoActivity extends AppCompatActivity {
    private ImageView Imageback;
    private TextView Textback;
    private ImageView iv;
    private Map<String, String> data;
    private EditText newSignature;
    private Button saveSignature;

    public void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //设置请求码，以便我们区分返回的数据
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_changeinfo);
        iv = findViewById(R.id.newtx);
        newSignature = findViewById(R.id.newSignature);
        Imageback =  findViewById(R.id.fanhui);
        Imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfoActivity.this.finish();
            }
        });
        Textback = findViewById(R.id.fh);
        Textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfoActivity.this.finish();
            }
        });
        Button buttonC = findViewById(R.id.choose);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeInfoActivity.this, "从手机相册中选择头像", Toast.LENGTH_SHORT).show();
                openAlbum();
            }
        });
        saveSignature = findViewById(R.id.saveW);
        saveSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = new HashMap<>();
                data.put("username", "codeplay");
                data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
                data.put("signature",newSignature.getText().toString());
                RequestQueue requestQueue = Volley.newRequestQueue(ChangeInfoActivity.this);
                Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/user/signature",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("ddd", "response -> " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ddd", error.getMessage(), error);
                    }
                }, data);
                requestQueue.add(request);
                Toast.makeText(ChangeInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:  //打开相册
                    if (data != null) {
                        //获取数据
                        //获取内容解析者对象
                        try {
                            Bitmap mBitmap = BitmapFactory.decodeStream(
                                    getContentResolver().openInputStream(data.getData()));
                            iv.setImageBitmap(mBitmap);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    }
}



