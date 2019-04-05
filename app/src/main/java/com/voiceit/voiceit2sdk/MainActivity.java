package com.voiceit.voiceit2sdk;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

import com.voiceit.voiceit2.VoiceItAPI2;

public class MainActivity extends AppCompatActivity {

    private VoiceItAPI2 myVoiceIt;
    private String [] userId = {"usr_f3011352753749bab180f9498e0bdfff", "usr_a54b0d37fdb549c8977f821ccdcd2512","usr_2c01a553ac5b4f5a8259dd9469975e04","usr_06d1f74995d24a0fb5fc44e268e2f1dc"};
    private int userIdIndex = 0;
    private String groupId = "GROUP_ID";
    private String phrase = "Never forget tomorrow is a new day";
    private String contentLanguage = "en-US";
    private boolean doLivenessCheck = true; // Liveness detection is not used for enrollment views

    private Switch userIdSwitch;
    private Switch livenessSwitch;
    private Button b1,b2,b3;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If using user tokens, replace API_KEY below with the user token,
        // and leave the second argument as an empty string
        myVoiceIt = new VoiceItAPI2("key_a452294434c84f3a8457f92eecf70916","tok_6eb82d59e931484fafb82c3db8abe5f3");


    }



    public void displayIdentifiedUser(JSONObject response) {
        try {
            String id = response.getString("userId");
            if(userId[0].equals(id)) {
                Toast.makeText(mContext, "User 1 Identified", Toast.LENGTH_LONG).show();
            } else if (userId[1].equals(id)) {
                Toast.makeText(mContext, "User 2 Identified", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            System.out.println("JSONException: " + e.getMessage());
        }
    }

    public void encapsulatedFaceEnrollment(View view) {
        myVoiceIt.encapsulatedFaceEnrollment(this, userId[userIdIndex], new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedFaceEnrollment Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    System.out.println("encapsulatedFaceEnrollment Result : " + errorResponse.toString());
                }
            }
        });
    }


    public void encapsulatedFaceVerification(View view) {
        myVoiceIt.encapsulatedFaceVerification(this, userId[userIdIndex], doLivenessCheck, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedFaceIdentification Result : " + response.toString());
                displayIdentifiedUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    System.out.println("encapsulatedFaceIdentification Result : " + errorResponse.toString());
                }
            }
        });
    }

}
