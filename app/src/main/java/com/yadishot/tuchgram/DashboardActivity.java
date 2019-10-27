package com.yadishot.tuchgram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;


public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView textView;
    private SharedPreferencesApi sharedPreferencesApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("پنل کاربری");

        textView = findViewById(R.id.dashboardTxt);
        sharedPreferencesApi = new SharedPreferencesApi(DashboardActivity.this);

        if (!sharedPreferencesApi.readLoginStatus()){
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            DashboardActivity.this.finish();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                textView.setText("menu settings clicked");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
