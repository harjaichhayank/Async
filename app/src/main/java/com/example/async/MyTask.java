package com.example.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

public class MyTask extends AsyncTask<Void,Integer,String> {
    private Context context;
    private ProgressDialog progressDialog;
    private TextView textView;
    private Button button;

    MyTask(Context context, TextView textView, Button button){
        this.context = context;
        this.textView = textView;
        this.button = button;
    }

    @Override
    protected String doInBackground(Void... voids) {
        int i=0;
        synchronized (this){
            while(i<10){
                try{
                    wait(1500);
                    i++;
                    publishProgress(i);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        return "Download completed";
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading is in progress");
        progressDialog.setMax(10);
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
        button.setEnabled(true);
        progressDialog.hide();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        progressDialog.setProgress(progress);
        String message = "download is in progress bro";
        textView.setText(message);
    }
}
