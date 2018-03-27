package com.example.khadija.khadii;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public TextView textView2;
    double calc;
    double resultcalc;
    boolean flagstorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calc=0;
        resultcalc=0;
        textView = (TextView) findViewById(R.id.textView);
        textView2= (TextView) findViewById(R.id.textView2);
        flagstorage=false;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    ArrayList<String> arrayList =new ArrayList<String>();
    ArrayList<String> arrayList2 =new ArrayList<String>();
    String string= "";
    String string1= "";

    //this function accepts the equation and sorts it
    public void onClick1(View v)
    {
        Button button = (Button) v;
        string= (String) button.getText().toString();
        if ( !string.contains("+") && !string.contains("-") && !string.contains("*") && !string.contains("/"))
        {
            string1= string1+ string;
            if(arrayList.size()>0)
            {
                arrayList.remove(arrayList.size()-1);

            }
            arrayList.add(string1);
        }
        else
        {
            arrayList.add(string);
            arrayList.add(string);
            string1="";
        }
        textView2.setText(textView2.getText().toString()+ string);
        arrayList2=new ArrayList<String>(arrayList);
        //textView2.setText(arrayList.toString());
    }


    //this function performs the actual calculation
    public void onClick (View v)
    {

        //int calc=0;
        int c= arrayList.size();
        while(c!=1)
        {
            if (c>3)
            {
                if(arrayList.get(3).contains("*")||arrayList.get(3).contains("/"))
                {
                    if(arrayList.get(3).contains("*")){ calc= Double.parseDouble(arrayList.get(2)) * Double.parseDouble(arrayList.get(4));}
                    if(arrayList.get(3).contains("/")){ calc= Double.parseDouble(arrayList.get(2)) / Double.parseDouble(arrayList.get(4));}

                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.add(2, Double.toString(calc));
                    c=arrayList.size();
                }
                else
                {
                    if (arrayList.get(1).contains("+")){calc= Double.parseDouble(arrayList.get(0)) +Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("-")){calc= Double.parseDouble(arrayList.get(0)) - Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("*")){calc= Double.parseDouble(arrayList.get(0)) * Double.parseDouble(arrayList.get(2));}
                    if (arrayList.get(1).contains("/")){calc= Double.parseDouble(arrayList.get(0)) / Double.parseDouble(arrayList.get(2));}

                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.add(0, Double.toString(calc));
                    c=arrayList.size();

                }

            }
            else
            {
                if (arrayList.get(1).contains("+")){calc= Double.parseDouble(arrayList.get(0)) +Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("-")){calc= Double.parseDouble(arrayList.get(0)) - Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("*")){calc= Double.parseDouble(arrayList.get(0)) * Double.parseDouble(arrayList.get(2));}
                if (arrayList.get(1).contains("/")){calc= Double.parseDouble(arrayList.get(0)) / Double.parseDouble(arrayList.get(2));}

                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.add(0, Double.toString(calc));
                c=arrayList.size();
            }


        }
        if(arrayList.size()==1)
        {
            calc= Double.parseDouble(arrayList.get(0));
        }
        textView.setText(""+calc);
    }
    public void sinFunct(View v)
    {

        textView.setText("" + Math.sin(calc));

    }

    public void sqrtfn(View v)
    {

        textView.setText("" + (Math.sqrt(calc)));

    }

    public void cosfn(View v) {

        textView.setText("" + Math.cos(calc));
    }

    public void storeres(View v)
    {
           resultcalc=calc;
           textView.setText("");
           flagstorage=true;
            //f.close();
    }

    public void secfn(View v)
    {
            textView.setText(""+1/Math.cos(calc));

    }

    public void cosecfn(View v) {
            textView.setText(""+1/Math.sin(calc));
    }

    public void memrec(View v) {
            if(flagstorage){
                if(arrayList2.size()==0)
                {
                    arrayList2.add((""+resultcalc));
                }
                else{
                        String prevexp=arrayList2.get(arrayList2.size()-1);
                        prevexp=prevexp+resultcalc;
                        arrayList2.set(arrayList2.size() - 1, prevexp);
                        arrayList.clear();
                        arrayList=new ArrayList<String>(arrayList2);

                }
                textView2.setText(textView2.getText().toString()+resultcalc);
        flagstorage=false; }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //this function is used whith the clear key to clear the text view and result tab
    public void clear(View v)
    {
       // TextView textView = (TextView) findViewById(R.id.textView);
       // TextView textView2 =(TextView) findViewById(R.id.textView2);

        string1="";
        string="";
        textView.setText("0");
        textView2.setText("");
        arrayList.clear();
    }
}
