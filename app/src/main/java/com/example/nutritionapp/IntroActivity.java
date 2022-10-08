package com.example.nutritionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.createInstance("Welcome!",
                "This is Nutrition App!",
                R.drawable.icon_food,
                R.color.blue_100
        ));

        addSlide(AppIntroFragment.createInstance(
                "Simple Calorie Calculator",
                "Manage your calorie intake in a simple way!",
                R.drawable.icon_food_2,
                R.color.purple_200
        ));

        addSlide(AppIntroFragment.createInstance(
                "Control Your Nutrition",
                "Control the amount of protein, carbohydrates and fats you eat!",
                R.drawable.food_icon_3,
                R.color.purple_300
        ));

        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

        // Show/hide status bar
        showStatusBar(true);

        //Speed up or down scrolling
        setScrollDurationFactor(2);

        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        setColorTransitionsEnabled(true);

        //Prevent the back button from exiting the slides
        setSystemBackButtonLocked(true);

        //Activate wizard mode (Some aesthetic changes)
        setWizardMode(true);

        //Show/hide skip button
        setSkipButtonEnabled(false);

        //Enable immersive mode (no status and nav bar)
        setImmersiveMode();

        //Enable/disable page indicators
        setIndicatorEnabled(true);

        //Dhow/hide ALL buttons
        setButtonsEnabled(true);

    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}