package com.carrillo.urwodlog.Model;

import java.io.Serializable;

/**
 * Created by carri on 4/9/2018.
 */

public class Workout implements Serializable {
    private String workoutDate = "";
    private String workoutDurationTime = "";
    private String workoutDetails = "";
    private String workoutRounds = "";
    private String workoutReps = "";
    private String id = "";


    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }


    public String getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(String workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    public String getWorkoutRounds() {
        return workoutRounds;
    }

    public void setWorkoutRounds(String workoutRounds) {
        this.workoutRounds = workoutRounds;
    }




    public String getWorkoutDurationTime() {
        return workoutDurationTime;
    }

    public void setWorkoutDurationTime(String workoutDurationTime) {
        this.workoutDurationTime = workoutDurationTime;
    }

    public String getWorkoutReps() {
        return workoutReps;
    }

    public void setWorkoutReps(String workoutReps) {
        this.workoutReps = workoutReps;
    }

    @Override
    public String toString(){
        return workoutDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
