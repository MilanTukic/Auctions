package com.example.tukicmilan.auctions.milan.activities;

import android.content.Context;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;

public class AddBidActivity extends AppCompatActivity {

    Context context;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);

        editText = (EditText) findViewById(R.id.ed1);
        editText = (EditText) findViewById(R.id.ed2);
        editText = (EditText) findViewById(R.id.ed3);

        button = (Button) findViewById(R.id.btnAdd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("add", "kliknuo si");
                Toast toast = Toast.makeText(getApplicationContext(), "AAAbUUUU" ,Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
