package edu.neu.madcourse.shlokdixit1.communications;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class RemoteClient {

    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String FIREBASE_DB = "https://shining-inferno-2019.firebaseio.com";
    private static final String TAG = "RemoteClient";
    private static boolean isDataChanged = false;
    private Context mContext;
    private HashMap<String, String> fireBaseData = new HashMap<String, String>();
    String storage;
    String[] parts;
    String[] playerlist;
    String[] gcmid;


    public RemoteClient(Context mContext)
    {
        this.mContext = mContext;
        Firebase.setAndroidContext(mContext);

    }


    public void saveValue(String key, String value) {
        Firebase ref = new Firebase(FIREBASE_DB);
        Firebase usersRef = ref.child(key);
        usersRef.setValue(value, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.d(TAG, "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d(TAG, "Data saved successfully.");
                }
            }
        });
    }

    public boolean isDataFetched()
    {
        return isDataChanged;
    }

    public String getValue(String key)
    {
        return fireBaseData.get(key);
    }
/*
    public void fetchValue(String key) {

        Log.d(TAG, "Get Value for Key - " + key);
        Firebase ref = new Firebase(FIREBASE_DB + key);
        Query queryRef = ref.orderByKey();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // snapshot contains the key and value
                isDataChanged = true;
                if(snapshot.getValue() != null)
                {
                    Log.d(TAG, "Data Received" + snapshot.getValue().toString());

                    // Adding the data to the HashMap
                    fireBaseData.put(snapshot.getKey(), snapshot.getValue().toString());

                }
                else {
                    Log.d(TAG, "Data Not Received");
                    fireBaseData.put(snapshot.getKey(), null);

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, firebaseError.getMessage());
                Log.e(TAG, firebaseError.getDetails());
            }
        });
    }*/

    public void fetchValue( ) {

        //Log.d(TAG, "Get Value for Key - " + key);
        Firebase ref = new Firebase(FIREBASE_DB );

        Query queryRef = ref.orderByKey();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // snapshot contains the key and value
                //snapshot.getKey();
                storage=snapshot.getValue().toString();
                parts = storage.split("[,=]");
                //System.out.println(parts);
              //  StringBuilder sb = new StringBuilder();
                ArrayList<String> players = new ArrayList();
                for(int i=0;i<parts.length;i++){
                  //  playerlist = parts;
                   // sb.append(parts[i]);
                   if(i%2==0){
                       System.out.println(parts[i]);
                       players.add(parts[i]);
                   }

                }
                //System.out.print(sb);
           /*for (String after_split: parts) {
                System.out.println(after_split);
            }*/
                isDataChanged = true;
                if(snapshot.getValue() != null)
                {
                    //Log.d(TAG, "Data Received" + snapshot.getValue().toString());

                    // Adding the data to the HashMap
                   // fireBaseData.put(snapshot.getKey(), snapshot.getValue().toString());
                    fireBaseData.put(snapshot.getKey(), snapshot.getValue().toString());
                }

                else {
                    Log.d(TAG, "Data Not Received");
                    fireBaseData.put(snapshot.getKey(), null);

                }


            }



            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, firebaseError.getMessage());
                Log.e(TAG, firebaseError.getDetails());
            }
        });
    }
}
