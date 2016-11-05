package reeldin.rationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.R.attr.data;

/**
 * Created by gopikm on 4/11/16.
 */

public class OTP extends AppCompatActivity {

    Button done;
    EditText card_no;
    private String url;
    int otp;
    TextView ration_text;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        url="http://192.168.43.92:8080/ration-shop/verify/";
        final RequestQueue queue = Volley.newRequestQueue(this);
        done=(Button)findViewById(R.id.done);
        ration_text=(TextView)findViewById(R.id.ration_text);
        ration_text.setText("OTP");
        card_no=(EditText)findViewById(R.id.card_num);
        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        sped=sp.edit();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject j=new JSONObject();
                try {
                    otp=Integer.valueOf(card_no.getText().toString());
                    j.put("otp",otp);
                    j.put("card_no",sp.getString("card_no",""));
                }catch (JSONException je){
                    je.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            if(response.getBoolean("success")) {
                                sped.putInt("success",1);
                                sped.putInt("shop_no",response.getInt("shop_no"));
                                sped.putString("card_type",response.getString("card_type"));
                                if(response.getString("card_type").equals("bpl")){
                                    sped.putInt("rice",20);
                                    sped.putInt("wheat",15);
                                    sped.putInt("sugar",3);
                                    sped.putInt("kerosene",4);
                                    sped.putInt("ricep",2);
                                    sped.putInt("wheatp",22);
                                    sped.putInt("sugarp",8);
                                    sped.putInt("kerosenep",12);

                                }
                                else{
                                    sped.putInt("rice",15);
                                    sped.putInt("wheat",10);
                                    sped.putInt("sugar",2);
                                    sped.putInt("kerosene",2);
                                    sped.putInt("ricep",12);
                                    sped.putInt("wheatp",50);
                                    sped.putInt("sugarp",32);
                                    sped.putInt("kerosenep",28);
                                }
                                sped.commit();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getApplicationContext(),"Invalid Otp", Toast.LENGTH_SHORT).show();
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
            }
        });


    }
}
