package charmingdev.d.c3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class Address extends AppCompatActivity {

    private ImageView BACK;

    private EditText ADDRESS,ADDRESSTWO,STATE,COUNTRY;

    private Button SAVE;

    private UserInfo userInfo;

    private C3UserSession c3UserSession;

    private ProgressDialog progressDialog;

    private String TAG = Address.class.getSimpleName();
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        userInfo = new UserInfo(this);
        c3UserSession = new C3UserSession(this);
        progressDialog = new ProgressDialog(this);

        BACK = findViewById(R.id.back);
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ADDRESS = findViewById(R.id.addrOne);
        ADDRESSTWO = findViewById(R.id.addrTwo);
        STATE = findViewById(R.id.state);
        COUNTRY = findViewById(R.id.country);
        SAVE = findViewById(R.id.save);

        returnValue();

        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountUpdate();
            }
        });

    }

    private void returnValue(){
        ADDRESS.setText(userInfo.getKeyAddress());
        ADDRESSTWO.setText(userInfo.getKeyAddressTwo());
        STATE.setText(userInfo.getKeyState());
        COUNTRY.setText(userInfo.getKeyCountry());
    }

    private void accountUpdate() {
        final String address = ADDRESS.getText().toString().trim();
        final String addressTwo = ADDRESSTWO.getText().toString().trim();
        final String state = STATE.getText().toString();
        final String country = COUNTRY.getText().toString();

        if (TextUtils.isEmpty(address)) {
            ADDRESS.setError("Please enter your address");
            ADDRESS.requestFocus();
            return;
        } else if(TextUtils.isEmpty(state)){
            STATE.setError("Please enter your state");
            STATE.requestFocus();
            return;
        } else if(TextUtils.isEmpty(country)){
            COUNTRY.setError("Please enter your country");
            COUNTRY.requestFocus();
            return;
        } else {

            progressDialog.setMessage("updating data...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.UPDATE_ADDRESS_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "server Response: " + response.toString());
                            progressDialog.hide();

                            if (response.equalsIgnoreCase("success")) {

                                userInfo.setKeyAddress(address);
                                userInfo.setKeyAddressTwo(addressTwo);
                                userInfo.setKeyState(state);
                                userInfo.setKeyCountry(country);
                                AlertDialog.Builder builder = new AlertDialog.Builder(Address.this);
                                builder.setMessage("Account updated successful");
                                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();

                            } else {
                                //error report
                                String errorMsg = response.toLowerCase();
                                AlertDialog.Builder builder = new AlertDialog.Builder(Address.this);
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
                            Toast.makeText(Address.this, "Connection error", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("address", address);
                    params.put("addressTwo", addressTwo);
                    params.put("state", state);
                    params.put("country", country);
                    params.put("email", userInfo.getKeyEmail());

                    return params;
                }
            };

            VolleySingleton.getInstance(Address.this).addToRequestQueue(stringRequest);
        }
    }
}
