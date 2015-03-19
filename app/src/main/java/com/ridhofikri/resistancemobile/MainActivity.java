package com.ridhofikri.resistancemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button startButton;
    private Button exitButton;

    public NumberPicker numberPicker;
    public TextView jumlahDepan;
    public int spy = 2;
    public int resistance = 3;
    public int total = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        jumlahDepan = (TextView) findViewById(R.id.jumlahDepan);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == 5){
                    spy = 2;
                    resistance = 3;
                } else if(newVal == 6){
                    spy = 2;
                    resistance = 4;
                } else if(newVal == 7){
                    spy = 3;
                    resistance = 4;
                } else if(newVal == 8){
                    spy = 3;
                    resistance = 5;
                } else if(newVal == 9){
                    spy = 3;
                    resistance = 6;
                } else if(newVal == 10){
                    spy = 4;
                    resistance = 6;
                }
                total = newVal;
                jumlahDepan.setText("Resistance: " + resistance + "\nSpy: " + spy);
            }
        });

        startButton = (Button) findViewById(R.id.startButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("Number1", spy);
                intent.putExtra("Number2", resistance);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
    }
}
