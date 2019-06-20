package com.carrillo.urwodlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carrillo.urwodlog.Model.Workout;
import com.carrillo.urwodlog.Model.WorkoutSvcSQLiteImpl;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkoutDetailsActivity extends AppCompatActivity {

                                        //fields that will corresponds to text boxes or buttons in the XML files of the WorkoutDetails activity.
    private EditText dateFld;
    private EditText durationTimeFld;
    private EditText workoutDetailsFld;
    private EditText roundsFld;
    private EditText repsFld;
    private Button createSaveBtn = null;
    private Button cancelDeleteBtn = null;
    private WorkoutSvcSQLiteImpl svc = WorkoutSvcSQLiteImpl.getInstance(this);
    private Workout workout = null;
    private DatabaseReference firebaseDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        firebaseDB = FirebaseDatabase.getInstance().getReference();                             //create Firebase instance
        dateFld = (EditText)findViewById(R.id.dateEditText);
        durationTimeFld = (EditText)findViewById(R.id.durationTimeEditText);
        workoutDetailsFld = (EditText)findViewById(R.id.workoutDetailsEditText);
        roundsFld = (EditText)findViewById(R.id.roundsEditText);
        repsFld = (EditText)findViewById(R.id.repsEditText);
        createSaveBtn = (Button)findViewById(R.id.createSaveButton);
        cancelDeleteBtn = (Button)findViewById(R.id.cancelDeletebutton);

        Intent intent = getIntent();
        workout = (Workout)intent.getSerializableExtra("workout");                  //keyword "workout" is used to populate the fields with the correct workout object data

        if(workout != null){
            dateFld.setText(workout.getWorkoutDate());
            durationTimeFld.setText(workout.getWorkoutDurationTime());
            workoutDetailsFld.setText(workout.getWorkoutDetails());
            roundsFld.setText(workout.getWorkoutRounds());
            repsFld.setText(workout.getWorkoutReps());
            createSaveBtn.setText("Update");
            cancelDeleteBtn.setText("Delete");

        }


    }

    public void cancelDelete(View view){
        if(workout == null){
            finish();

            //svc.delete(workout);  used of database in context of the phone.
        }else{
            firebaseDB.child("workouts").child(workout.getId()).removeValue();
            Toast.makeText(this, "SUCCESS",Toast.LENGTH_LONG).show();
            finish();;
        }
    }

    public void createUpdate(View view){
        if(validFields()){
            if(workout == null){
                Workout workout = new Workout();
                workout.setId(firebaseDB.child("workouts").push().getKey());
                workout.setWorkoutDate(dateFld.getText().toString());
                workout.setWorkoutDurationTime(durationTimeFld.getText().toString());
                workout.setWorkoutDetails(workoutDetailsFld.getText().toString());
                workout.setWorkoutRounds(roundsFld.getText().toString());
                workout.setWorkoutReps(repsFld.getText().toString());
                firebaseDB.child("workouts").child(workout.getId()).setValue(workout);


                //SQL workout = svc.create(workout);

            }
            else{
                workout.setWorkoutDate(dateFld.getText().toString());
                workout.setWorkoutDurationTime(durationTimeFld.getText().toString());
                workout.setWorkoutDetails(workoutDetailsFld.getText().toString());
                workout.setWorkoutRounds(roundsFld.getText().toString());
                workout.setWorkoutReps(repsFld.getText().toString());
                firebaseDB.child("workouts").child(workout.getId()).setValue(workout);

                //SQL workout = svc.upDate(workout);
            }
        }

        Toast.makeText(this,"SUCCESS", Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * validFields()- will be used to validate that all fields are populated. Toast message is shown if fields is missing.
     * @return
     */
    private boolean validFields(){
        if(dateFld.getText().toString().equals("")){
            Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            return false;
        }
        if(durationTimeFld.getText().toString().equals("")){
            Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            return false;
        }
        if(workoutDetailsFld.getText().toString().equals("")){
            Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            return false;
        }
        if(roundsFld.getText().toString().equals("")){
            Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            return false;
        }
        if(repsFld.getText().toString().equals("")){
            Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            return false;
        }
        finish();

        return true;
    }


    /**
     * onCreateOptionsMenu()- menu inflater is used. This creates a menu on the top right corner of our WorkoutDetails Activity (screen)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemSelected() - changes Activity (screen) based on what is choosen form the corner menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_share_workout){
            Toast.makeText(this,"Share Workout", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);

    }




}
