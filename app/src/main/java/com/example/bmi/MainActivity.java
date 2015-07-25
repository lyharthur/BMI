package com.example.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.RetentionPolicy;


public class MainActivity extends ActionBarActivity {

    String[] feetArray, inchArray;
    EditText vWeight;
    //EditText vHeight;
    int feet, inch;
    Spinner spinnerFeet, spinnerInch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registerForContextMenu(vWeight);
        //registerForContextMenu(vHeight);
        spinnerFeet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feet = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerInch.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inch = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void initView() {


        //vHeight =(EditText) findViewById(R.id.height);
        vWeight = (EditText) findViewById(R.id.weight);
        feetArray = getResources().getStringArray(R.array.feet);
        inchArray = getResources().getStringArray(R.array.inch);
        spinnerFeet = (Spinner) findViewById(R.id.spinner_feet);
        ArrayAdapter<String> adapterFeet = new ArrayAdapter<String>(this, R.layout.dropdown_item, feetArray);
        spinnerFeet.setAdapter(adapterFeet);

        spinnerInch = (Spinner) findViewById(R.id.spinner_inch);
        ArrayAdapter<String> adapterInch = new ArrayAdapter<String>(this, R.layout.dropdown_item, inchArray);
        spinnerInch.setAdapter(adapterInch);


    }

    public void savePreferences(int f, int i, String w) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        pref.edit().putInt("feet", f).commit();
        pref.edit().putInt("inch", i).commit();
        pref.edit().putString("weight", w).commit();
    }

    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI",MODE_PRIVATE);
        spinnerFeet.setSelection(pref.getInt("feet",0));
        spinnerInch.setSelection(pref.getInt("inch",0));
        vWeight.setText(pref.getString("weight","0"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_about:
                openOptionsDialog();
                return true;
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                openOptionsDialog();
                return true;
            case R.id.menuItemWiki:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);


        }

    }

    private void openOptionsDialog() {
        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.menu_about).setMessage(R.string.aboutmsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    public void calcBMI(View view) {
        //String height=vHeight.getText().toString();
        String weight = vWeight.getText().toString();

        if (weight.equals("")) {
            Toast.makeText(MainActivity.this, R.string.bmi_waring, Toast.LENGTH_LONG).show();

        } else if (weight.equals("0")) {

            Toast.makeText(MainActivity.this, R.string.bmi_waring0, Toast.LENGTH_LONG).show();

        } else {
            savePreferences(feet-1,inch,weight);
            Intent intent = new Intent(this, ReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("feet", feet);
            bundle.putInt("inch", inch);
            bundle.putString("weight", weight);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        savePreferences(feet-1,inch,weight);
    }

    public void aboutBMI(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.aboutbtn)
                .setMessage(R.string.aboutmsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


    }


}
