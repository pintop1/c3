package charmingdev.d.c3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private ImageView BACK;
    TextView PROFILE,ADDRESS,RATINGS;
    Switch NOTIFICATIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BACK = findViewById(R.id.back);
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PROFILE = findViewById(R.id.myProfile);
        ADDRESS = findViewById(R.id.myAddress);
        RATINGS = findViewById(R.id.myRatings);
        NOTIFICATIONS = findViewById(R.id.notification);

        PROFILE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Profile.class));
            }
        });

        ADDRESS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Address.class));
            }
        });

        RATINGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Ratings.class));
            }
        });
    }
}
