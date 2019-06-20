package com.carrillo.urwodlog.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carri on 4/9/2018.
 */

public class WorkoutSvcSQLiteImpl extends SQLiteOpenHelper{
    private final static String DBNAME = "workouts.db";
    private final static int DBVERSION = 1;
    private final String TAG = "WorkoutSvcSQLiteImpl";

    //create SQLite Table Creation for firebase in the form of a String.
    private final String CREATE_WORKOUTS_TABLE = "CREATE TABLE workout(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, durationTime TEXT, workoutDetails TEXT, rounds TEXT, reps TEXT);";
    private final String DROP_WORKOUTS_TABLE = "DROPTABLE IF EXISTS workout;";

    private WorkoutSvcSQLiteImpl(Context context){
        super(context, DBNAME, null, DBVERSION);

    }

    private static WorkoutSvcSQLiteImpl instance  = null;

    public static WorkoutSvcSQLiteImpl getInstance(Context context){
        if(instance == null){
            instance = new WorkoutSvcSQLiteImpl(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
       Log.i(TAG, "onCreate");
       db.execSQL(CREATE_WORKOUTS_TABLE);   //create Database table.

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(TAG, "onUpgrade");
        db.execSQL(DROP_WORKOUTS_TABLE);
    }

    public Workout create(Workout workout){
        Log.i(TAG, "Create Workout");
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("date", workout.getWorkoutDate());
            values.put("durationTime",workout.getWorkoutDurationTime());
            values.put("workoutDetails",workout.getWorkoutDetails());
            values.put("rounds",workout.getWorkoutRounds());
            values.put("reps",workout.getWorkoutReps());

            db.insert("workout", null, values);             //out get a new row in the database
            Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            //SQL id must be datatype INT workout.setId(id);

            cursor.close();
            db.close();


        }catch (Exception e){
            Log.i(TAG, "***Exception " + e.getMessage());
        }
        return workout;

    }

    public List<Workout> retrieveAll(){
        List<Workout> workoutList = new ArrayList<Workout>();
        String columns [] = {"id","date", "durationTime", "workoutDetails", "rounds", "reps"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("workout",columns, null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Workout workout = getWorkout(cursor);
            workoutList.add(workout);
            cursor.moveToNext();
        }
        db.close();

        return workoutList;
    }

    public Workout getWorkout(Cursor cursor){
        Workout workout = new Workout();
        //SQL id must be datatype INT workout.setId(cursor.getInt(0));
        workout.setWorkoutDate(cursor.getString(1));
        workout.setWorkoutDurationTime(cursor.getString(2));
        workout.setWorkoutDetails(cursor.getString(3));
        workout.setWorkoutRounds(cursor.getString(4));
        workout.setWorkoutReps(cursor.getString(5));


        return workout;
    }

    public Workout upDate(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", workout.getWorkoutDate());
        values.put("durationTime",workout.getWorkoutDurationTime());
        values.put("workoutDetails",workout.getWorkoutDetails());
        values.put("rounds", workout.getWorkoutRounds());
        values.put("reps",workout.getWorkoutReps());
        db.update("workout",values, "id=" + workout.getId(), null);

        db.close();
        return workout;

    }

    public Workout delete(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("workout", "id=" + workout.getId(),null);

        db.close();
        return workout;
    }





}
