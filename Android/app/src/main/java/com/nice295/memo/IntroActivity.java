package com.nice295.memo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getString(R.string.intro1_title), "1", getString(R.string.intro1_desc), "2",
                R.drawable.intro1,
                Color.parseColor("#F06292")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro2_title), getString(R.string.intro2_desc),
                R.drawable.intro2,
                Color.parseColor("#BA68C8")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro3_title), getString(R.string.intro3_desc),
                R.drawable.intro3,
                Color.parseColor("#9575CD")));

        //setWizardMode(true);
        setSkipTextTypeface(getResources().getString(R.string.skip));
        setDoneTextTypeface(getResources().getString(R.string.done));
        //show back with done button
        //setBackButtonVisibilityWithDone(false);
        //showSkipButton(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
