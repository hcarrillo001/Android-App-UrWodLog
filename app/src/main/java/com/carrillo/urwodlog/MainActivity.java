package com.carrillo.urwodlog;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.carrillo.urwodlog.Model.IWorkoutSvc;
import com.carrillo.urwodlog.Model.Workout;
import com.carrillo.urwodlog.Model.WorkoutSvcSQLiteImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";          //TAG used for debugging.
    private ListView listView;
    private DatabaseReference firebaseDB = null;
    private FirebaseAuth auth = null;
    private boolean logIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.myWorkoutList);              //find the listContainer in the activity
        firebaseDB = FirebaseDatabase.getInstance().getReference();     //create a Firebase instance

        logInFirebase();

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");
        if(logIn){
            inItWithFirebase();
        }

        //inItWithSQLite();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    private void logInFirebase(){
        Log.i(TAG, "***logInFirebase");
        auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"Log in is successful");
                    logIn = true;
                    inItWithFirebase();
                }else{
                    Log.i(TAG,"***Log in failed");
                }
            }
        });
    }


    private void inItWithFirebase(){
        firebaseDB.child("workouts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Workout> workoutList = new ArrayList<Workout>();
                for(DataSnapshot workoutDataSnapShot : dataSnapshot.getChildren()){
                    Workout workout = workoutDataSnapShot.getValue(Workout.class);
                    workoutList.add(workout);
                }
                //Code bellow wires the List with listView
                ArrayAdapter arrayAdapter = new ArrayAdapter<Workout>(getApplicationContext(),R.layout.listview_row,workoutList);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Log.i(TAG, "***onItemClickListner");
                        Workout workout = workoutList.get(position);                                                     //returns the workout that is clicked on
                        Intent intent = new Intent(MainActivity.this,WorkoutDetailsActivity.class);         // intent will be used to change screen (activity)
                        intent.putExtra("workout",workout);                                                         //put extra is a keyword that will be used to send the correct workout object data that will be used to populate the text boxes
                        startActivity(intent);                                                                          //change screen

                    }
                });





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "***Error: ");
            }
        });
    }


    private void inItWithSQLite(){
        WorkoutSvcSQLiteImpl workoutSvc = WorkoutSvcSQLiteImpl.getInstance(this);
        final List<Workout> workoutList = workoutSvc.retrieveAll();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,workoutList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("onClickListner", "position: " + position);
                Intent intent = new Intent(MainActivity.this,WorkoutDetailsActivity.class);
                intent.putExtra("workout",workoutList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_add_workout){
            Toast.makeText(this,"Add Workout", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, WorkoutDetailsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_AboutUs){
            Toast.makeText(this,"About Us", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,AboutUsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_ContactUs){
            Toast.makeText(this,"Contact Us", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,ContactUsActivity.class);
            startActivity(intent);
        }

        //activities for contact us and about us


        return super.onOptionsItemSelected(item);

    }




}
