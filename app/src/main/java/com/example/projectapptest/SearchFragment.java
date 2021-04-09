package com.example.projectapptest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;


public class SearchFragment extends Fragment {
    EditText edCheck;
    TextView txtResult,txtHead;
    Button btnCheck;
    Interpreter interpreter;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edCheck = view.findViewById(R.id.edCheck);
        btnCheck = view.findViewById(R.id.btnCheck);
        txtResult = view.findViewById(R.id.txtResult);
        txtHead = view.findViewById(R.id.txtHead3);

        //Flask api

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edCheck.getText().toString().trim();
                String data = "{" + "\"url\"" + ": \"" + url + "\"" + "}";
                submit(data);
                getResult();

            }
        });


                //In-built Check (Tensorflow lite model)
        /*CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
        FirebaseModelDownloader.getInstance().getModel("NewsCheckModel",
                DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel customModel) {
                        File modelFile = customModel.getFile();
                        if (modelFile != null) {
                            interpreter = new Interpreter(modelFile);
                        }
                    }
                });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkNews();


            }
        });*/
        return view;
    }
    public void submit(String data){
        final String savedata = data;
        //String apiUrl= "http://192.168.0.107:5000/json";
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.107:5000/predict", new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Server error",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata==null?null:savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getResult(){
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url("http://192.168.0.107:5000/predict").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(),"Server Problem",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                    txtResult.setText(response.body().string());
            }
        });


    }
    /*public void checkNews(){
        String inputNews = edCheck.getText().toString();
        int bufferSize = 1000* Float.SIZE;
        ByteBuffer modelOutput =ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());
        interpreter.run(inputNews,modelOutput);
    }*/
}
