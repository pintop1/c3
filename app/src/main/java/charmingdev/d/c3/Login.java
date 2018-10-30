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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import charmingdev.d.c3.prefs.C3UserSession;
import charmingdev.d.c3.prefs.UserInfo;

public class Login extends AppCompatActivity {

    ImageView BACK;
    TextView FORGET,CREATE;

    EditText EMAIL,PASSWORD;
    Button LOGIN;

    UserInfo userInfo;
    C3UserSession c3UserSession;
    ProgressDialog progressDialog;

    private String TAG = Login.class.getSimpleName();
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userInfo = new UserInfo(this);
        c3UserSession = new C3UserSession(this);
        progressDialog = new ProgressDialog(this);

        BACK = (ImageView) findViewById(R.id.back);
        FORGET = (TextView) findViewById(R.id.forget);
        CREATE = (TextView) findViewById(R.id.signUp);
        EMAIL = (EditText) findViewById(R.id.email);
        PASSWORD = (EditText) findViewById(R.id.password);

        LOGIN = (Button) findViewById(R.id.login);

        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FORGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, PasswordRecovery.class));
            }
        });

        CREATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        final String email = EMAIL.getText().toString().trim();
        final String password = PASSWORD.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EMAIL.setError("Enter a valid email address");
            EMAIL.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)) {
            PASSWORD.setError("Please enter your password");
            PASSWORD.requestFocus();
            return;
        }

        progressDialog.setMessage("Validating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Login Response: " + response.toString());
                        progressDialog.hide();

                        try {
                            JSONObject jobj = new JSONObject(response);
                            boolean error = jobj.getBoolean("error");

                            //checking error node in json
                            if (!error) {
                                //Now store in DB
                                JSONObject user = jobj.getJSONObject("user");
                                String id = user.getString("id");
                                String fname = user.getString("firstname");
                                String lname = user.getString("lastname");
                                String email = user.getString("email");
                                String gender = user.getString("gender");
                                String phone = user.getString("phone");
                                String address = user.getString("address");
                                String address_two = user.getString("address_two");
                                String state = user.getString("state");
                                String country = user.getString("country");
                                String status = user.getString("status");
                                String daym = user.getString("daym");
                                String monthm = user.getString("monthm");
                                String timem = user.getString("timem");
                                String yearm = user.getString("yearm");
                                String datetimem = user.getString("datetimem");
                                String profile_dp_url = user.getString("profile_dp_url");

                                userInfo.setKeyId(id);
                                userInfo.setKeyFirstname(fname);
                                userInfo.setKeyLastname(lname);
                                userInfo.setKeyEmail(email);
                                userInfo.setKeyGender(gender);
                                userInfo.setKeyPhone(phone);
                                userInfo.setKeyAddress(address);
                                userInfo.setKeyAddressTwo(address_two);
                                userInfo.setKeyState(state);
                                userInfo.setKeyCountry(country);
                                userInfo.setKeyStatus(status);
                                userInfo.setKeyDay(daym);
                                userInfo.setKeyMonth(monthm);
                                userInfo.setKeyYear(yearm);
                                userInfo.setKeyTime(timem);
                                userInfo.setKeyTimestamp(datetimem);
                                userInfo.setKeyProfileUrl(profile_dp_url);

                                c3UserSession.setLoggedIn(true);
                                progressDialog.setMessage("Redirecting...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Login.this, MainPage.class));
                                        finish();
                                    }
                                }, TIME_OUT);

                            } else {
                                //error report
                                String errorMsg = jobj.getString("error_msg");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage(errorMsg);
                                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Json error: : " + e.getMessage());
                            progressDialog.hide();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "connection error: " + error.getMessage());
                        Toast.makeText(Login.this, "Connection error", Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);

    }
}
