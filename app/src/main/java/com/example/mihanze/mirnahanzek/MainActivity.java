package com.example.mihanze.mirnahanzek;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;
        DBAdapter db = new DBAdapter(this);
        db.open();

        new DownloadTextTask().execute("https://web.math.pmf.unizg.hr/~karaga/android/sportovidata.txt");

        // citanje iz datoteke i spremanje u bazu
       /* Scanner input=new Scanner(path+ "textfile.txt");
        input.useDelimiter(" +"); //delimitor is one or more spaces
        while(input.hasNext()){
            String a = input.next();
            String b = input.next();
            String d = input.next();
            String e = input.next();
            db.insertContact(a,b,d,e);

        }

        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
               //dodavanje texta u textview
                TextView textElement = (TextView) findViewById(R.id.sport);
                // i olimpijskog sporta u edittext
                EditText ol = (EditText) findViewById(R.id.olimpijski);
                String modifyText;
                modifyText = textElement.getText().toString();
                modifyText = modifyText + "\n" + c.getString(0) + " " + c.getString(1) + " "
                        + c.getString(2) + " " + c.getString(3);
                textElement.setText(modifyText);

                modifyText = ol.getText().toString();
                if( modifyText.equals("1")) {
                    modifyText = modifyText + "\n" + c.getString(1);
                    ol.setText(modifyText);
                }
            } while (c.moveToNext());
        }

        new DownloadTextTask2().execute("https://web.math.pmf.unizg.hr/~karaga/android/sportasidata.txt");

        // citanje iz datoteke i spremanje u bazu
        input=new Scanner(path+ "textfile2.txt");
        input.useDelimiter(" +"); //delimitor is one or more spaces
        while(input.hasNext()){
            String a = input.next();
            String b = input.next();
            String d = input.next();
            db.insertContact2(a,b,d);

        }

        c = db.getAllContacts2();
        if (c.moveToFirst())
        {
            do {
                //dodavanje texta u textview
                TextView textElement = (TextView) findViewById(R.id.sport);
                String modifyText;
                modifyText = textElement.getText().toString();
                modifyText = modifyText + "\n" + c.getString(0) + " " + c.getString(1) + " "
                        + c.getString(2);
                textElement.setText(modifyText);
            } while (c.moveToNext());
        }

        */

        db.close();
    }


    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }


    private String DownloadText(String URL)
        {
            int BUFFER_SIZE = 2000;
            InputStream in = null;
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;

            try {
                in = OpenHttpConnection(URL);
            } catch (IOException e) {
                Log.d("NetworkingActivity", e.getLocalizedMessage());
                return "";
            }


            InputStreamReader isr = new InputStreamReader(in);
            int charRead;
            String str = "";
            char[] inputBuffer = new char[BUFFER_SIZE];
            try {
                while ((charRead = isr.read(inputBuffer))>0) {
                    //---convert the chars to a String---
                    String readString =
                            String.copyValueOf(inputBuffer, 0, charRead);
                    str += readString;
                    inputBuffer = new char[BUFFER_SIZE];
                }
                in.close();
            } catch (IOException e) {
                Log.d("NetworkingActivity", e.getLocalizedMessage());
                return "";
            }

            try
            {

                new File(path  ).mkdir();
                File file = new File(path+ "textfile.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);

                fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            }  catch(FileNotFoundException ex) {

            }  catch(IOException ex) {

            }

            return str;
        }

    private String DownloadText2(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;


        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }

        try
        {

            new File(path  ).mkdir();
            File file = new File(path+ "textfile2.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);

            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());


        }  catch(FileNotFoundException ex) {

        }  catch(IOException ex) {

        }
        return str;
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {


            protected String doInBackground(String... urls) {

                return DownloadText(urls[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            }
        }

    private class DownloadTextTask2 extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {

            return DownloadText2(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }


}

