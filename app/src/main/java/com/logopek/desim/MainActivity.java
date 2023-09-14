package com.logopek.desim;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public void dialogOK(String codename){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(String.format("%s", codename))
                .setTitle("Successfully");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void updateCurrent(){
        TextView cur = findViewById(R.id.current_region);
        cur.setText(String.format("Your current region: %s", Checker.getCommandOutput("getprop gsm.sim.operator.iso-country")));
    }

    public void dialogError(String codename){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(String.format("%s", codename))
                .setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Checker.hasRootAccess()){
            dialogError("No root access");
        }

        setContentView(R.layout.activity_main);
        updateCurrent();
    }




    public void setUs(View view) {

        try{
            Runtime.getRuntime().exec("su -c resetprop gsm.sim.operator.iso-country us,us");
            dialogOK("Successfully set us,us");
        }
        catch (java.io.IOException e){
            dialogError("Can not set us,us");
        }
        updateCurrent();
    }

    public void setRu(View view){
        try{
            Runtime.getRuntime().exec("su -c resetprop gsm.sim.operator.iso-country ru,ru");
            dialogOK("Successfully set ru,ru");}
        catch (java.io.IOException e){
            dialogError("Can not set ru,ru");
        }
        updateCurrent();
    }
    public void setDe(View view) {
        try{
            Runtime.getRuntime().exec("su -c resetprop gsm.sim.operator.iso-country de,de");
            dialogOK("Successfully set de,de");}
        catch (java.io.IOException e){
            dialogError("Can not set de,de");
        }
        updateCurrent();
    }


    public void setCustom(View view){
        final EditText txt = new EditText(this);
        final Button de = new Button(this);
        new AlertDialog.Builder(this)
                .setTitle("Custom region")
                .setView(txt)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String region = txt.getText().toString();
                        try{
                            Runtime.getRuntime().exec(String.format("su -c resetprop gsm.sim.operator.iso-country %s", region));

                            dialogOK(String.format("Successfully set %s", region));
                            updateCurrent();}
                        catch (java.io.IOException e){
                            dialogError(String.format("Can not set %s", region));
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                })
                .show();

    }


}