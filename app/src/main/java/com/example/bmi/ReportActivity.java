package com.example.bmi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;


public class ReportActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Bundle bundle = getIntent().getExtras();
        //double height = Double.parseDouble(bundle.getString("height"))/100;
        int feet = (bundle.getInt("feet"));
        int inch = (bundle.getInt("inch"));
        double height = (double) (feet * 12 + inch) * 0.0254;
        double weight = Double.parseDouble(bundle.getString("weight"));
        weight = weight * 0.45359;


        double bmi = weight / (height * height);
        DecimalFormat nf = new DecimalFormat("0.00");
        TextView result = (TextView) findViewById((R.id.report_result));
        result.setText(getString(R.string.bmi_result) + " " + nf.format(bmi));


        ImageView image = (ImageView) findViewById(R.id.report_image);
        TextView advice = (TextView) findViewById(R.id.report_advice);

        if (bmi > 25) {
            image.setImageResource(R.drawable.fat);
            advice.setText(R.string.advice_heavy);
        } else if (bmi < 20) {
            image.setImageResource(R.drawable.thin);
            advice.setText(R.string.advice_light);
        } else {
            image.setImageResource(R.drawable.fit);
            advice.setText(R.string.advice_average);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
