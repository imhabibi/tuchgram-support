package com.yadishot.tuchgram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    private LinearLayout sendNumberLayout, verifyCodeLayout;
    private TextInputEditText txtNumberPhone, txtCodeVerifyCode;
    private Button btnSendNumber, btnSendCodeVeify;
    private String numberVerify;
    private SharedPreferencesApi sharedPreferencesApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        sharedPreferencesApi = new SharedPreferencesApi(this);
        if (sharedPreferencesApi.readLoginStatus()){
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            MainActivity.this.finish();
        }
        btnSendNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = txtNumberPhone.getText().toString().trim();
                numberVerify = number;
                if (!number.isEmpty()){
                    AuthorizationAccount(number);
                }else if (number.isEmpty()){
                    Toast.makeText(MainActivity.this, "یک شماره موبایل وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSendCodeVeify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = txtCodeVerifyCode.getText().toString().trim();

                if (!code.isEmpty()){
                    AuthorizationAccountCode(code);
                }else if (code.isEmpty()){
                    Toast.makeText(MainActivity.this, "شما باید کدفعال سازی را وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AuthorizationAccountCode(final String code) {
        String url = "https://smspanel.Trez.ir/CheckSendCode.ashx";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("true")){
                    // check already account is exits in database or not
                    checkAccount();
                    sharedPreferencesApi.writeSuccessLoginStatus(true);
                    sharedPreferencesApi.writePhoneNumber(numberVerify);
                }else if (response.equals("false")){
                    Toast.makeText(MainActivity.this, "رمز ورود اشتباه است", Toast.LENGTH_SHORT).show();
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
                params.put("UserName", "whiteshadow");
                params.put("Password", "Mehdi00991122Yuosefi200800991122");
                params.put("Mobile", numberVerify);
                params.put("Code", code);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void checkAccount() {
            String url = "https://horse-breeding-danaei.ir/api/register.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("response");

                        if (success.equals("0")){
                            sharedPreferencesApi.writeUserLoginNum("0");
                            fetchUserData(numberVerify);
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                            MainActivity.this.finish();
                        }else if (success.equals("2")){
                            sharedPreferencesApi.writeUserLoginNum("2");
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                            MainActivity.this.finish();
                        }
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
                    params.put("number", numberVerify);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }

    private void fetchUserData(final String numberVerify) {

        String url = "https://horse-breeding-danaei.ir/api/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("response");

                    if (success.equals("2")){
                        sharedPreferencesApi.writeFullname(jsonObject.getString("user_fullname"));
                        sharedPreferencesApi.writeUserId(jsonObject.getString("user_id"));
                        sharedPreferencesApi.writePhoneNumber(jsonObject.getString("user_number"));
                    }else if (success.equals("0")){
                        Toast.makeText(MainActivity.this, "این کاربر وجود ندارد.", Toast.LENGTH_SHORT).show();
                    }
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
                params.put("number", numberVerify);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void AuthorizationAccount(final String number) {
        String url = "https://smspanel.Trez.ir/AutoSendCode.ashx";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                txtNumberPhone.setText("");
                sendNumberLayout.setVisibility(View.GONE);
                verifyCodeLayout.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("UserName", "whiteshadow");
                params.put("Password", "Mehdi00991122Yuosefi200800991122");
                params.put("Mobile", number);
                params.put("Footer", "تیم زاکو");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void findViewByIds() {
        sendNumberLayout = findViewById(R.id.sendNumberLayout);
        verifyCodeLayout = findViewById(R.id.verifyCodeLayout);
        txtCodeVerifyCode = findViewById(R.id.txtCodeVerify);
        txtNumberPhone = findViewById(R.id.txtNumber);
        btnSendCodeVeify = findViewById(R.id.btnVerifyCode);
        btnSendNumber = findViewById(R.id.btnSendNumber);
    }
}
