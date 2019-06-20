package com.carrillo.urwodlog.Model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.carrillo.urwodlog.Model.WorkoutSvcSQLiteImpl;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WorkoutSvcSQLiteImplTest {
    @Test
    public void testSQLiteCRUD() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.carrillo.urwodlog", appContext.getPackageName());

        WorkoutSvcSQLiteImpl impl = WorkoutSvcSQLiteImpl.getInstance(appContext);
        assertNotNull(impl);

        Workout workout = new Workout();
        workout.setWorkoutDate("January 31, 2018");
        workout.setWorkoutDurationTime("10:00");
        workout.setWorkoutDetails("New Details");
        workout.setWorkoutRounds("3");
        workout.setWorkoutReps("30");

        List<Workout> workoutList = impl.retrieveAll();
        assertNotNull(workoutList);

        int size = workoutList.size();
        workout = impl.create(workout);

        assertNotEquals(0, size);

        assertNotNull(workout);

        int id = workout.getId();
        assertNotEquals(0,id);

        workout.setWorkoutDetails("Newer Details");
        workout = impl.upDate(workout);
        assertNotNull(workout);

        workout = impl.delete(workout);
        assertNotNull(workout);

        workoutList = impl.retrieveAll();
        int newSize = workoutList.size();
        assertEquals(size, newSize);

    }
}
