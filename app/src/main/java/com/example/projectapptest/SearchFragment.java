package com.example.projectapptest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;

import java.io.File;

public class SearchFragment extends Fragment {
    EditText edCheck;
    Button btnCheck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edCheck = view.findViewById(R.id.edCheck);
        btnCheck = view.findViewById(R.id.btnCheck);


        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
        FirebaseModelDownloader.getInstance().getModel("NewsCheckModel",
                DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel customModel) {
                        File modelFile = customModel.getFile();
                        if (modelFile != null) {
                            Interpreter interpreter = new Interpreter(modelFile);
                        }
                    }
                });
        
        
         return view;
    }
}
