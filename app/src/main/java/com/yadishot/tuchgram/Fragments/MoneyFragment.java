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
import com.yadishot.tuchgram.Adapters.InvitedRecyclerViewAdapter;
import com.yadishot.tuchgram.Models.InviteModel;
import com.yadishot.tuchgram.R;
import com.yadishot.tuchgram.SharedPreferencesConfig.SharedPreferencesApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyFragment extends Fragment {

    private String currentSod;
    private InvitedRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private List<InviteModel> mInvite = new ArrayList<>();
    private TextView moneyDropEnough;
    private SharedPreferencesApi sharedPreferencesApi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        moneyDropEnough = view.findViewById(R.id.moneyDropEnough);
        currentSod = "";
        sharedPreferencesApi = new SharedPreferencesApi(getContext());
        configureRecyclerView();
        return view;
    }

    private void configureRecyclerView() {
        recyclerViewAdapter = new InvitedRecyclerViewAdapter(getContext(), mInvite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        fetchInviteProjects();

    }

    private void fetchInviteProjects() {
        String url = "https://horse-breeding-danaei.ir/api/inviter.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    mInvite.clear();
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String accept_project = jsonObject.getString("Condition");
                        InviteModel inviteModel = new InviteModel();
                        inviteModel.setProjectInviter(jsonObject.getString("inviter_name"));
                        inviteModel.setProjectDecs(jsonObject.getString("project_decs"));
                        inviteModel.setProjectMoney(jsonObject.getString("project_price"));

                        if (accept_project.equals("0")){
                            inviteModel.setProjectAccept("درحال بررسی");
                        }else if (accept_project.equals("1")){
                            inviteModel.setProjectAccept("تایید شده");
                        }
                        // darsad giri
                        String money_price = jsonObject.getString("project_price");
                        Toast.makeText(getContext(), "" + money_price, Toast.LENGTH_SHORT).show();
                        int money = Integer.parseInt(money_price);
                        int sod = money - (money*80/100);
//                        mohasebeSod(sod);
                        inviteModel.setProjectSod(String.valueOf(sod));
                        mInvite.add(inviteModel);
                        recyclerViewAdapter.notifyDataSetChanged();

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
                params.put("user_id", sharedPreferencesApi.readUserId());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

//    private void mohasebeSod(int currentSodNum){
//        currentSod = currentSod + currentSodNum;
//        int currentSodOne = Integer.parseInt(currentSod);
//        int SodKoli = currentSodOne + currentSodNum;
//        String namayeshSodeKoli = String.valueOf(SodKoli);
//        moneyDropEnough.setText(namayeshSodeKoli);
//    }
}
