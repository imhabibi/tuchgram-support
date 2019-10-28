package com.yadishot.tuchgram.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yadishot.tuchgram.Adapters.RequestRecyclerViewAdapter;
import com.yadishot.tuchgram.DashboardActivity;
import com.yadishot.tuchgram.Models.RequestModel;
import com.yadishot.tuchgram.R;
import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportFragment extends Fragment {

    private TextView supportFragment, requestDined;
    private SharedPreferencesApi sharedPreferencesApi;
    // we need to that for fetch support requests and config recyclerView
    private RequestRecyclerViewAdapter requestRecyclerViewAdapter;
    private List<RequestModel> requestSupprotModel = new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_support, container, false);
        sharedPreferencesApi = new SharedPreferencesApi(getContext());
        requestDined = view.findViewById(R.id.requestDined);
        supportFragment = view.findViewById(R.id.dashboardTxt);
        recyclerView = view.findViewById(R.id.recyclerView);
        configureRecyclerView();
        return view;
    }

    private void configureRecyclerView() {
        requestRecyclerViewAdapter = new RequestRecyclerViewAdapter(getContext(), requestSupprotModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(requestRecyclerViewAdapter);
        fetchSupportRequests();
    }

    private void fetchSupportRequests() {
        String support_requests = "https://horse-breeding-danaei.ir/api/fetchs.php";

        StringRequest request = new StringRequest(Request.Method.POST, support_requests, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // fetch user support requests...
                try {
                    requestDined.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = new JSONArray(response);
                    requestSupprotModel.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectName = jsonObject.getString("project_name");
                        String projectDecs = jsonObject.getString("msg");
                        String projectTime = jsonObject.getString("time");

                        // set call back res in model items getter and setter
                        RequestModel model = new RequestModel();
                        model.setProjectName(projectName);
                        model.setProjectDescription(projectDecs);
                        model.setProjectTime(projectTime);
                        requestSupprotModel.add(model);
                        requestRecyclerViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
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