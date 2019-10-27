package com.yadishot.tuchgram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yadishot.tuchgram.Adapters.RequestRecyclerViewAdapter;
import com.yadishot.tuchgram.Fragments.AppsFragment;
import com.yadishot.tuchgram.Fragments.MoneyFragment;
import com.yadishot.tuchgram.Fragments.SettingsFragment;
import com.yadishot.tuchgram.Fragments.SupportFragment;
import com.yadishot.tuchgram.Models.RequestModel;
import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView textView, requestDined;
    private SharedPreferencesApi sharedPreferencesApi;
    private BottomNavigationView bottomNavigationView;
    // we need to that for fetch support requests and config recyclerView
    private RequestRecyclerViewAdapter requestRecyclerViewAdapter;
    private List<RequestModel> requestSupprotModel = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findByViewIds();
        configureRecyclerView();
        toolbar.setTitle("پنل کاربری");
        sharedPreferencesApi = new SharedPreferencesApi(DashboardActivity.this);

        if (!sharedPreferencesApi.readLoginStatus()) {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            DashboardActivity.this.finish();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                new SupportFragment()).commit();
    }

    private void configureRecyclerView() {
        requestRecyclerViewAdapter = new RequestRecyclerViewAdapter(getApplicationContext(), requestSupprotModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
        recyclerView.setAdapter(requestRecyclerViewAdapter);
        fetchSupportRequests();
    }

    private void findByViewIds() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        requestDined = findViewById(R.id.requestDined);
        textView = findViewById(R.id.dashboardTxt);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.support:
                            selectedFragment = new SupportFragment();
                            break;
                        case R.id.appsLucks:
                            selectedFragment = new AppsFragment();
                            break;
                        case R.id.moneyDrop:
                            selectedFragment = new MoneyFragment();
                            break;
                        case R.id.settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                            selectedFragment).commit();
                    return true;
                }
            };

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
                Toast.makeText(DashboardActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", sharedPreferencesApi.readUserId());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                textView.setText("menu settings clicked");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
