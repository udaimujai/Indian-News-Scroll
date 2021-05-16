package com.example.trial_1;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_home_ok_button) Button mOkButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
/*        Button mOkButton = (Button) findViewById(R.id.gotit);
               mOkButton.setOnClickListener(v -> onBackPressed());*/
        mOkButton.setOnClickListener(v -> onBackPressed());
    }
}
