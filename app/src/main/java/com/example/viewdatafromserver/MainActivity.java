package com.example.viewdatafromserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtname;
    Button btnview;
    TextView nametxt,emailtxt,mobiletxt,addresstxt;

    String web_url="https://shitalkawale.000webhostapp.com/viewSingleRecord.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname=findViewById(R.id.nameedt);
        btnview=findViewById(R.id.viewbtn);
        nametxt=findViewById(R.id.txtname);
        emailtxt=findViewById(R.id.txtemail);
        mobiletxt=findViewById(R.id.txtmobile);
        addresstxt=findViewById(R.id.txtaddress);

    }

    public void viewdata(View view) {
        searchData(edtname.getText().toString());
    }
    public  void  searchData(String name)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, web_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            int sucess=jsonObject.getInt("sucess");
                            if(sucess==1)
                            {
                                nametxt.setText(jsonObject.getString("name"));
                                emailtxt.setText(jsonObject.getString("email"));
                                mobiletxt.setText(jsonObject.getString("mobileno"));
                                addresstxt.setText(jsonObject.getString("address"));

                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Name",name);

                return  map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}