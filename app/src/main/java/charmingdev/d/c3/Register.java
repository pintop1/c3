package charmingdev.d.c3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

import charmingdev.d.c3.prefs.C3UserSession;
import charmingdev.d.c3.prefs.UserInfo;

public class Register extends AppCompatActivity {

    ImageView BACK;
    EditText FNAME,LNAME,EMAIL,PASSWORD,CPASSWORD,PHONE;
    Button REGISTER;
    ProgressDialog progressDialog;
    C3UserSession c3UserSession;
    UserInfo userInfo;
    private String TAG = Register.class.getSimpleName();
    private static int TIME_OUT = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        c3UserSession = new C3UserSession(this);
        userInfo = new UserInfo(this);

        BACK = (ImageView) findViewById(R.id.back);
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FNAME = (EditText) findViewById(R.id.fname);
        LNAME = (EditText) findViewById(R.id.lname);
        EMAIL = (EditText) findViewById(R.id.email);
        PASSWORD = (EditText) findViewById(R.id.password);
        CPASSWORD = (EditText) findViewById(R.id.cpassword);
        PHONE = (EditText) findViewById(R.id.phone);

        REGISTER = (Button) findViewById(R.id.register);
        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

    }

    private void userRegister() {
        final String firstname = FNAME.getText().toString().trim();
        final String lastname = LNAME.getText().toString().trim();
        final String email = EMAIL.getText().toString();
        final String password = PASSWORD.getText().toString();
        final String cpassword = CPASSWORD.getText().toString();
        final String phone = PHONE.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            FNAME.setError("Please enter first name");
            FNAME.requestFocus();
            return;
        } else if (TextUtils.isEmpty(lastname)) {
            LNAME.setError("Please enter last name");
            LNAME.requestFocus();
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EMAIL.setError("Enter a valid email address");
            EMAIL.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            PASSWORD.setError("Please enter your password");
            PASSWORD.requestFocus();
            return;
        }else if(!cpassword.equals(password)){
            CPASSWORD.setError("Passwords does not match");
            CPASSWORD.requestFocus();
            return;
        }else if(TextUtils.isEmpty(phone)){
            PHONE.setError("Please enter a phone number");
            PHONE.requestFocus();
            return;
        } else {

            progressDialog.setMessage("Sending data...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "server Response: " + response.toString());
                            progressDialog.hide();

                            if (response.equalsIgnoreCase("success")) {
                                //Now store in DB

                                progressDialog.setMessage("Registration successful! Redirecting...");
                                progressDialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }
                                }, TIME_OUT);


                            } else {
                                //error report
                                String errorMsg = response.toLowerCase();
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage(response);
                                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "connection error: " + error.getMessage());
                            Toast.makeText(Register.this, "Connection error", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("firstname", firstname);
                    params.put("lastname", lastname);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("phone", phone);

                    return params;
                }
            };

            VolleySingleton.getInstance(Register.this).addToRequestQueue(stringRequest);
        }
    }
}
