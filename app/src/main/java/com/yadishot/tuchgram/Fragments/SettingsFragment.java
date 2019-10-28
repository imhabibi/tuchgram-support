package com.yadishot.tuchgram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yadishot.tuchgram.MainActivity;
import com.yadishot.tuchgram.R;
import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {

    private Button exitAccount, changeInformation, btnSetChanges;
    private SharedPreferencesApi sharedPreferencesApi;
    private EditText edtChangeName, edtChangeNumber;
    private LinearLayout changeInfoLayout, showInfoLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        exitAccount = view.findViewById(R.id.exitAccount);
        edtChangeName = view.findViewById(R.id.edtChangeName);
        edtChangeNumber = view.findViewById(R.id.edtChangeNumber);
        changeInformation = view.findViewById(R.id.changeInformation);
        sharedPreferencesApi = new SharedPreferencesApi(getContext());
        changeInfoLayout = view.findViewById(R.id.changeInfoLayout);
        showInfoLayout = view.findViewById(R.id.showInfoLayout);
        btnSetChanges = view.findViewById(R.id.btnSetChanges);

        changeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoLayout.setVisibility(View.GONE);
                changeInfoLayout.setVisibility(View.VISIBLE);
                edtChangeNumber.setHint(sharedPreferencesApi.readPhoneNumber());
                edtChangeName.setHint(sharedPreferencesApi.readFullname());
                changeInformationUserData();
            }
        });
        btnSetChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfoLayout.setVisibility(View.GONE);
                showInfoLayout.setVisibility(View.VISIBLE);
            }
        });
        exitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesApi.writeSuccessLoginStatus(false);
                sharedPreferencesApi.writeUserId("");
                sharedPreferencesApi.writeUserLoginNum("");
                sharedPreferencesApi.writePhoneNumber("");
                sharedPreferencesApi.writeFullname("");
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

    private void changeInformationUserData() {
        String url = "";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", sharedPreferencesApi.readUserId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
