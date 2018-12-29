package saurabh.com.speechtotext.Service;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import saurabh.com.speechtotext.R;

public class ServiceRequest extends AsyncTask<Void,Void,Object> {

    private AppCompatActivity context;
    private AlertDialog alertDialog;
    private static final String URL="http://a.galactio.com/interview/dictionary-v2.json";
    private String error;
    private ResponseCallback responseCallback;


    public ServiceRequest(AppCompatActivity context,ResponseCallback responseCallback) {
        this.context= context;
        this.responseCallback=responseCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LayoutInflater layoutInflater = context.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layoutInflater.inflate(R.layout.dialog_view,null));
        alertDialog=builder.create();
        alertDialog.show();

    }

    @Override
    protected Object doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .get()
                .build();

        Response response = null;
        try{
            response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                return error=response.body().string();
            }else{
                return response.body().string();
            }
        }catch (IOException e){
            return error = e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        alertDialog.dismiss();
        if(null!=error){
            responseCallback.onError(error);
        }else{
            responseCallback.onSuccess(o.toString());
        }
    }
}
