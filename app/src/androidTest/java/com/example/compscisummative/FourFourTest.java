package com.example.compscisummative;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FourFourTest {

    @Rule
    public ActivityScenarioRule<FourFour> activityRule =
            new ActivityScenarioRule<>(FourFour.class);

    private int getBackgroundColor(View view) {
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable) {
            return ((ColorDrawable) background).getColor();
        }
        return Color.TRANSPARENT;
    }

    @Test
    public void testInitialUIState_IsCorrectlyConfigured() {
        activityRule.getScenario().onActivity(activity -> {
            Button startButton = activity.findViewById(R.id.BStart);
            Button stopButton = activity.findViewById(R.id.BStop);
            Button gridButton00 = activity.findViewById(R.id.B00);
            Button gridButton33 = activity.findViewById(R.id.B33);

            Assert.assertEquals(Color.RED, getBackgroundColor(startButton));
            Assert.assertEquals(Color.BLACK, getBackgroundColor(stopButton));
            Assert.assertEquals(Color.BLACK, getBackgroundColor(gridButton00));
            Assert.assertEquals(Color.BLACK, getBackgroundColor(gridButton33));
        });
    }

    @Test
    public void testGridButton_TogglesColorAndStateOnClick() {
        activityRule.getScenario().onActivity(activity -> {
            Button gridButton = activity.findViewById(R.id.B11);
            // Verify starting state
            Assert.assertEquals(Color.BLACK, getBackgroundColor(gridButton));

            // Verify that it changes color when clicked
            gridButton.performClick();
            Assert.assertEquals(Color.RED, getBackgroundColor(gridButton));

            // Verify it reverts when clicked again
            gridButton.performClick();
            Assert.assertEquals(Color.BLACK, getBackgroundColor(gridButton));
        });
    }

    @Test
    public void testInvalidBpm_DoesNotCrashApp() {
        activityRule.getScenario().onActivity(activity -> {
            EditText bpmInput = activity.findViewById(R.id.BIn);
            Button startButton = activity.findViewById(R.id.BStart);

            bpmInput.setText("");
            startButton.performClick();

            Assert.assertNotNull(activity);
        });
    }
}