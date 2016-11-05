package reeldin.rationmanager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sherlock on 4/11/16.
 */
public class fragment_buy extends Fragment{

    CrystalSeekbar r_seeker,w_seeker,s_seeker,k_seeker;
    TextView rice_qt,sugar_qt,wheat_qt,kerosene_qt,rice_p,sugar_p,wheat_p,kerosene_p, cost, cost_words;
    Button ok,done;
    int rlimit,wlimit,slimit,klimit,ricep,wheatp,sugarp,kerop;
    SharedPreferences sp;
    SharedPreferences.Editor sped;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_buy, container, false);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.ll);
        sp=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        sped=sp.edit();
        r_seeker=(CrystalSeekbar) rootView.findViewById(R.id.rangeSeekbar1);
        w_seeker=(CrystalSeekbar) rootView.findViewById(R.id.rangeSeekbar2);
        s_seeker=(CrystalSeekbar) rootView.findViewById(R.id.rangeSeekbar3);
        k_seeker=(CrystalSeekbar) rootView.findViewById(R.id.rangeSeekbar4);
        rice_qt=(TextView)rootView.findViewById(R.id.rqt);
        rice_p=(TextView)rootView.findViewById(R.id.rp);
        wheat_qt=(TextView)rootView.findViewById(R.id.wqt);
        wheat_p=(TextView)rootView.findViewById(R.id.wp);
        sugar_qt=(TextView)rootView.findViewById(R.id.sqt);
        sugar_p=(TextView)rootView.findViewById(R.id.sp);
        kerosene_qt=(TextView)rootView.findViewById(R.id.kqt);
        kerosene_p=(TextView)rootView.findViewById(R.id.kp);
        cost=(TextView)rootView.findViewById(R.id.total);
        cost_words=(TextView)rootView.findViewById(R.id.cost_words);
        ok=(Button)rootView.findViewById(R.id.ok);
        done=(Button)rootView.findViewById(R.id.done);
        cost_words.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        done.setVisibility(View.GONE);
        rice_qt.setText("0");
        wheat_qt.setText("0");
        sugar_qt.setText("0");
        kerosene_qt.setText("0");
        rice_p.setText("0");
        sugar_p.setText("0");
        wheat_p.setText("0");
        kerosene_p.setText("0");
        rlimit=sp.getInt("rice",0)*2;
        wlimit=sp.getInt("wheat",0)*2;
        slimit=sp.getInt("sugar",0)*2;
        klimit=sp.getInt("kerosene",0)*2;
        ricep=sp.getInt("ricep",0);
        wheatp=sp.getInt("wheatp",0);
        sugarp=sp.getInt("sugarp",0);
        kerop=sp.getInt("kerosenep",0);
        r_seeker.setMinValue(0);
        w_seeker.setMinValue(0);
        s_seeker.setMinValue(0);
        k_seeker.setMinValue(0);
        r_seeker.setMaxValue(rlimit);
        w_seeker.setMaxValue(wlimit);
        s_seeker.setMaxValue(slimit);
        k_seeker.setMaxValue(klimit);

        r_seeker.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                float v=(float)value.intValue()/2;
                rice_qt.setText(String.valueOf(v));
                v*=ricep;
                rice_p.setText(String.valueOf(v));

            }
        });

        k_seeker.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                float v=(float)value.intValue()/2;
                kerosene_qt.setText(String.valueOf(v));
                v*=kerop;
                kerosene_p.setText(String.valueOf(v));

            }
        });

       w_seeker.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                float v=(float)value.intValue()/2;
                wheat_qt.setText(String.valueOf(v));
                v*=wheatp;
                wheat_p.setText(String.valueOf(v));

            }
        });

        s_seeker.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                float v=(float)value.intValue()/2;
                sugar_qt.setText(String.valueOf(v));
                v*=sugarp;
                sugar_p.setText(String.valueOf(v));

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double cost1= Double.valueOf(rice_p.getText().toString())+Double.valueOf(sugar_p.getText().toString())+Double.valueOf(wheat_p.getText().toString())+Double.valueOf(kerosene_p.getText().toString());
                cost.setVisibility(View.VISIBLE);
                cost_words.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                ok.setVisibility(View.GONE);
                cost.setText(cost1.toString());
            }

        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sped.putString("riceqt",rice_qt.getText().toString());
                sped.putString("wheatqt",wheat_qt.getText().toString());
                sped.putString("sugarqt",sugar_qt.getText().toString());
                sped.putString("keroseneqt",kerosene_qt.getText().toString());
                sped.commit();
                Intent intent=new Intent(getContext(),QrCode.class);
                startActivity(intent);


            }
        });

        return rootView;
    }

}
