package com.yadishot.tuchgram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInformation extends AppCompatActivity {

    private SharedPreferencesApi sharedPreferencesApi;
    private Button btnSaveInformation;
    private TextInputEditText fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        fullname = findViewById(R.id.fullname);
        btnSaveInformation = findViewById(R.id.btnSaveInformation);
        sharedPreferencesApi = new SharedPreferencesApi(this);

        btnSaveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnames = fullname.getText().toString().trim();

                if (fullnames.isEmpty()){
                    Toast.makeText(UserInformation.this, "لطفا نام خود را وارد کنید", Toast.LENGTH_SHORT).show();
                }else if (!fullnames.isEmpty()){
                    register(fullnames);
                }
            }
        });
    }

    private void register(final String fullnames) {
        final String number = sharedPreferencesApi.readPhoneNumber().trim();
        String url = "https://horse-breeding-danaei.ir/api/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("fullname", fullnames);
                params.put("number", number);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
