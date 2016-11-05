package reeldin.rationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class login extends AppCompatActivity {

    Button done;
    EditText card_no;
    private String url;
    int otp;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        url="http://192.168.43.92:8080/ration-shop/login-customer/";
        final RequestQueue queue = Volley.newRequestQueue(this);
        done=(Button)findViewById(R.id.done);
        card_no=(EditText)findViewById(R.id.card_num);
        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sp.getInt("success",0)==1){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        sped=sp.edit();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject j=new JSONObject();
                try {
                    j.put("card_no", card_no.getText().toString());
                    Log.d("card_no",

                            card_no.getText().toString());
                }catch (JSONException je){
                    je.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("success")) {
                                sped.putString("card_no", card_no.getText().toString());
                                sped.commit();
                                Toast.makeText(getApplicationContext(),"Sending Otp", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), OTP.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(getApplicationContext(),"Invalid Ration Card Number", Toast.LENGTH_SHORT).show();
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
