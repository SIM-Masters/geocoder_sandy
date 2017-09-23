package com.example.sandr.maps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtLatitud;
    EditText txtLongitud;
    Button btnGetAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLatitud =(EditText)findViewById(R.id.txtLatitud);
        txtLongitud =(EditText)findViewById(R.id.txtLongitud);
        btnGetAddress = (Button)findViewById(R.id.btnGetAddress);
    }

    public void goToMap (View view){
        Intent i= new Intent(this, MapsActivity.class);
        i.putExtra(String.valueOf(R.string.sendLatitud), txtLatitud.getText().toString());
        i.putExtra(String.valueOf(R.string.sendLongitud), txtLongitud.getText().toString());
        startActivity(i);
    }
}
