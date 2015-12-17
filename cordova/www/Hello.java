package com.example.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.List;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.StringWriter;
import java.util.ResourceBundle;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Context;
import android.app.Activity;

import com.JavaHello;
import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.io.File;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
//import com.java-sdk-2;





//import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
//import com.ibm.watson.developer_cloud.text_to_speech.v1.model.VoiceSet;
//import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.*;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.dto.SpeechConfiguration;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.ISpeechDelegate;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.android.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.android.speech_common.v1.TokenProvider;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.*;
//import com.ibm.watson.developer_cloud.android.http.HttpMediaType;
public class Hello extends CordovaPlugin implements  ISpeechDelegate {
    String mRecognitionResults;
    CallbackContext callback;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {


         if (action.equals("greet")) {
             String mFileName = "file:/storage/emulated/0/SoundRecorder/recording20151202180857.amr";
             String username = "4b896367-304e-4a9f-afae-b7c11c8719cf";
             String password = "ouMK0r5TyNMx";
             String serviceURL = "https://stream.watsonplatform.net/speech-to-text/api";
             SpeechToText service = new SpeechToText();
             SpeechConfiguration sConfig = new SpeechConfiguration(SpeechConfiguration.AUDIO_FORMAT_OGGOPUS);
             SpeechToText.sharedInstance().initWithContext(this.getHost(serviceURL),  this.cordova.getActivity().getApplicationContext(), sConfig);
             SpeechToText.sharedInstance().setCredentials(username, password);
             SpeechToText.sharedInstance().setDelegate(this);
             SpeechToText.sharedInstance().setModel("en-US_BroadbandModel");
             callback = callbackContext;
             mRecognitionResults = "bijay ";
             //SpeechToText.sharedInstance().recognize();
              new AsyncTask<Void, Void, Void>(){
                            @Override
                            protected Void doInBackground(Void... none) {
                                SpeechToText.sharedInstance().recognize();
                                return null;
                            }
                        }.execute();
             new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SpeechToText.sharedInstance().stopRecognition();
                        callbackContext.success(mRecognitionResults);
                    }
                }, 
                30000);
             
             //callbackContext.success( "bijay kanshi" );



            /*SpeechToText service = new SpeechToText();
            //service.setUsernameAndPassword("4b896367-304e-4a9f-afae-b7c11c8719cf", "ouMK0r5TyNMx");
            SpeechToText.sharedInstance().setCredentials(username, password);
            File audio = new File(mFileName);
            String transcript = SpeechToText.sharedInstance().recognize();
            callbackContext.success(transcript);*/
             return true;
         } else {
            return false;

        }
    }
    public void onMessage(String message) {
            try {
                JSONObject jObj = new JSONObject(message);
                if (jObj.has("results")) {
                    
                    JSONArray jArr = jObj.getJSONArray("results");
                    for (int i=0; i < jArr.length(); i++) {
                        JSONObject obj = jArr.getJSONObject(i);
                        JSONArray jArr1 = obj.getJSONArray("alternatives");
                        String str = jArr1.getJSONObject(0).getString("transcript");
                        String model = "en-US_BroadbandModel";
                        if (model.startsWith("ja-JP") || model.startsWith("zh-CN")) {
                            str = str.replaceAll("\\s+","");
                        }
                        String strFormatted = Character.toUpperCase(str.charAt(0)) + str.substring(1);
                        if (obj.getString("final").equals("true")) {
                            String stopMarker = (model.startsWith("ja-JP") || model.startsWith("zh-CN")) ? "。" : ". ";
                            mRecognitionResults += strFormatted.substring(0,strFormatted.length()-1) + stopMarker;
                        }
                        break;
                    }
                } 

            } catch (JSONException e) {
                e.printStackTrace();
            }
        
    }

    public void onAmplitude(double amplitude, double volume) {
    }
    public void onOpen() {
        
    }

    public void onError(String error) {
        callback.success(error);
       
    }

    public void onClose(int code, String reason, boolean remote) {
        
    }
    public URI getHost(String url){
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
