package reeldin.rationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sherlock on 4/11/16.
 */
public class fragment_check extends Fragment {

    TextView ralot,ravail,walot,wavail,salot,savail,kalot,kavail;
    private String url;
    JSONObject j;
    SharedPreferences sp;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_check, container, false);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.ll);
        url="http://192.168.43.92:8080/ration-shop/balance/";
        sp=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        ralot=(TextView)rootView.findViewById(R.id.ralot);
        ravail=(TextView)rootView.findViewById(R.id.ravail);
        walot=(TextView)rootView.findViewById(R.id.walot);
        wavail=(TextView)rootView.findViewById(R.id.wavail);
        salot=(TextView)rootView.findViewById(R.id.salot);
        savail=(TextView)rootView.findViewById(R.id.savail);
        kalot=(TextView)rootView.findViewById(R.id.kalot);
        kavail=(TextView)rootView.findViewById(R.id.kavail);
        ralot.setText("0");
        ravail.setText("0");
        salot.setText("0");
        savail.setText("0");
        walot.setText("0");
        wavail.setText("0");
        kalot.setText("0");
        kavail.setText("0");
        try{
            j=new JSONObject();
            j.put("card_no",sp.getString("card_no",""));
        }catch(JSONException je){
            je.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getBoolean("success")){
                        JSONObject json=new JSONObject();
                        json=response.getJSONObject("balance");
                        ralot.setText(String.valueOf(json.getDouble("Rice")));
                        walot.setText(String.valueOf(json.getDouble("Wheat")));
                        salot.setText(String.valueOf(json.getDouble("Sugar")));
                        kalot.setText(String.valueOf(json.getDouble("Kerosene")));
                        ravail.setText(String.valueOf(json.getDouble("Rice-Shop")));
                        wavail.setText(String.valueOf(json.getDouble("Wheat-Shop")));
                        savail.setText(String.valueOf(json.getDouble("Sugar-Shop")));
                        kavail.setText(String.valueOf(json.getDouble("Kerosene-Shop")));
                    }
                }catch (JSONException je){
                    je.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
        return rootView;
    }
}
