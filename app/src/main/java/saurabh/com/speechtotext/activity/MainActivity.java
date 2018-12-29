package saurabh.com.speechtotext.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import saurabh.com.speechtotext.Data.DictionaryData;
import saurabh.com.speechtotext.R;
import saurabh.com.speechtotext.Service.ResponseCallback;
import saurabh.com.speechtotext.Service.ServiceRequest;
import saurabh.com.speechtotext.Utils.SortByFrequency;
import saurabh.com.speechtotext.adapter.AdapterRvCell;

public class MainActivity extends AppCompatActivity {

    private TextView tv_text;
    private ImageView iv_image;
    private RecyclerView rv_list;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text=findViewById(R.id.tv_text);
        iv_image=findViewById(R.id.iv_image);
        rv_list=findViewById(R.id.rv_list);
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDictionaryData();

    }

    private void getDictionaryData() {

        new ServiceRequest(MainActivity.this, new ResponseCallback() {
            @Override
            public void onSuccess(String json) {
                Log.e("onSuccess",json);
                Gson gson = new GsonBuilder().create();
                DictionaryData dictionaryData = gson.fromJson(json,DictionaryData.class);
                Collections.sort(dictionaryData.getDictionary(),new SortByFrequency());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                rv_list.setLayoutManager(mLayoutManager);
                rv_list.setItemAnimator(new DefaultItemAnimator());
                AdapterRvCell adapterRvCell = new AdapterRvCell(MainActivity.this,dictionaryData.getDictionary());
                rv_list.setAdapter(adapterRvCell);
            }

            @Override
            public void onError(String cause) {
                Log.e("onError",cause);
                Toast.makeText(MainActivity.this,cause,Toast.LENGTH_SHORT).show();
            }
        }).execute();

    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speech_prompt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Speech not cupported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_text.setText(result.get(0));
                }
                break;
            }
        }
    }
}
