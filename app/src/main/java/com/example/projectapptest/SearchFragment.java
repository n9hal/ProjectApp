package com.example.projectapptest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class SearchFragment extends Fragment {
    EditText edCheck;
    TextView txtResult;
    Button btnCheck;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edCheck = view.findViewById(R.id.edCheck);
        btnCheck = view.findViewById(R.id.btnCheck);
        txtResult = view.findViewById(R.id.txtResult);
        progressBar = view.findViewById(R.id.progressBar5);

        //Flask api

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edCheck.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                CheckNews(url);

            }
        });
        return view;
    }
    public void CheckNews(String url){
        String savedata = url;
        String apiUrl= "http://192.168.0.109:5000/predict";
        OkHttpClient okHttpClient =new OkHttpClient();
        RequestBody body= new FormBody.Builder().add("url",savedata).build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(apiUrl).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"Error:"+ e,Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull final okhttp3.Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            txtResult.setText("News is "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

    }

}
