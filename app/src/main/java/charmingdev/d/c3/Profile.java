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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import charmingdev.d.c3.prefs.UserInfo;

public class Profile extends AppCompatActivity {

    private ImageView BACK;
    private EditText FNAME,LNAME,EMAIL,PHONE;
    private RadioButton FEMALE,MALE;
    private Button SAVE;
    private RadioGroup GENDER;

    private UserInfo userInfo;
    private ProgressDialog progressDialog;

    private String TAG = Profile.class.getSimpleName();
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userInfo = new UserInfo(this);
        progressDialog = new ProgressDialog(this);

        BACK = findViewById(R.id.back);
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FNAME = findViewById(R.id.fname);
        LNAME = findViewById(R.id.lname);
        EMAIL = findViewById(R.id.email);
        PHONE = findViewById(R.id.phone);
        FEMALE = findViewById(R.id.female);
        MALE = findViewById(R.id.male);
        SAVE = findViewById(R.id.save);

        GENDER = findViewById(R.id.gender);

        returnValue();

        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdate();
            }
        });
    }

    private void returnValue(){

        String gender = userInfo.getKeyGender();

        FNAME.setText(userInfo.getKeyFirstname());
        LNAME.setText(userInfo.getKeyLastname());
        EMAIL.setText(userInfo.getKeyEmail());
        PHONE.setText(userInfo.getKeyPhone());

        if(gender.equalsIgnoreCase("male")){
            MALE.setChecked(true);
        }else if(gender.equalsIgnoreCase("female")){
            FEMALE.setChecked(true);
        }else {
            MALE.setChecked(true);
        }
    }

    private void userUpdate() {
        final String firstname = FNAME.getText().toString().trim();
        final String lastname = LNAME.getText().toString().trim();
        final String email = EMAIL.getText().toString();
        final String phone = PHONE.getText().toString();
        int selectedId = GENDER.getCheckedRadioButtonId();
        RadioButton CHECKED = findViewById(selectedId);
        final String gender = CHECKED.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            FNAME.setError("Please enter first name");
            FNAME.requestFocus();
            return;
        } else if (TextUtils.isEmpty(lastname)) {
            LNAME.setError("Please enter last name");
            LNAME.requestFocus();
            return;
        } else if(TextUtils.isEmpty(phone)){
            PHONE.setError("Please enter a phone number");
            PHONE.requestFocus();
            return;
        } else {

            progressDialog.setMessage("updating data...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.UPDATE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "server Response: " + response.toString());
                            progressDialog.hide();

                            if (response.equalsIgnoreCase("success")) {

                                userInfo.setKeyFirstname(firstname);
                                userInfo.setKeyLastname(lastname);
                                userInfo.setKeyPhone(phone);
                                userInfo.setKeyGender(gender);

                                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
                            Toast.makeText(Profile.this, "Connection error", Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("firstname", firstname);
                    params.put("lastname", lastname);
                    params.put("email", email);
                    params.put("phone", phone);
                    params.put("gender", gender);

                    return params;
                }
            };

            VolleySingleton.getInstance(Profile.this).addToRequestQueue(stringRequest);
        }
    }


}
