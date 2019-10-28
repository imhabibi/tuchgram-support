package com.yadishot.tuchgram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
//    private TextView textView, requestDined;
    private SharedPreferencesApi sharedPreferencesApi;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findByViewIds();
        toolbar.setTitle("پنل کاربری");
        sharedPreferencesApi = new SharedPreferencesApi(DashboardActivity.this);

        if (!sharedPreferencesApi.readLoginStatus()) {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            DashboardActivity.this.finish();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                new SupportFragment()).commit();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void findByViewIds() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
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
}
