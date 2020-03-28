package com.example.covid_19gr;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/*
Sms app for the greek government.
@Author Stelios Restemis
@version 1.0.0
@since 25/03/2020


 */

import static android.os.SystemClock.sleep;
public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener  {
    public String temp;
    String[] choices =
    {
        "Μετάβαση σε φαρμακείο ή επίσκεψη στον γιατρό.",
                "Μετάβαση σε σούπερ μάρκετ, μίνι μάρκετ.",
                "Μετάβαση στην τράπεζα.",
                "Κίνηση για παροχή βοήθειας σε ανθρώπους που βρίσκονται σε ανάγκη.",
                "Μετάβαση σε τελετή (π.χ. κηδεία) ή μετάβαση διαζευγμένων γονέων.",
                "Σωματική άσκηση σε εξωτερικό χώρο ή κίνηση με κατοικίδιο ζώο."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        Button send = (Button) findViewById(R.id.button2);

        //set listener for changing reasons
        send.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItemText = (String) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),"Επιλέξατε: "  + selectedItemText , Toast.LENGTH_LONG).show();
        temp = selectedItemText.toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //nothing needed to be added for now
    }


    @Override
    public void onClick(View v) {
        final EditText name =  (EditText) findViewById(R.id.editText4);
        final EditText address =  (EditText) findViewById(R.id.editText5);
        final String name_f = name.getText().toString();
        final String address_f = address.getText().toString();
        int number=0;
        for (int i=0;i<6;i++){
            if(choices[i].equals(temp)){
                number = i+1;
            }
        }
        final String message = Integer.toString(number)+" "+ name_f + " " + address_f;

        //Send sms
        try{
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(Integer.toString(13033),null,message,null,null);
            Toast.makeText(MainActivity.this, "Το sms σταλθηκε επιτυχώς", Toast.LENGTH_SHORT).show();
            sleep(2000); //wait 2 seconds for effect
            finish();
            System.exit(0);
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Ωχ, κατι πηγε στραβά", Toast.LENGTH_SHORT).show();
        }
    }
}