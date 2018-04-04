package com.bignerdranch.android.criminalintentlesson;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;


public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button firstBtn;
    private Button lastBtn;

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    public void goToFirst(View v){
        mViewPager.setCurrentItem(0);

    }

    public void goToLast(View v){
        mViewPager.setCurrentItem(mCrimes.size()-1);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);


        mViewPager = (ViewPager)findViewById(R.id.crime_view_pager);
        firstBtn = (Button)findViewById(R.id.button_first);
        lastBtn = (Button)findViewById(R.id.button_last);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                firstBtn.setEnabled(true);
                lastBtn.setEnabled(true);
                if(position == 0)
                    firstBtn.setEnabled(false);
                else if(position == mCrimes.size() - 1)
                    lastBtn.setEnabled(false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for(int i = 0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                if(i == 0)
                    firstBtn.setEnabled(false);
                else if(i == mCrimes.size() - 1)
                    lastBtn.setEnabled(false);
                break;
            }
        }


    }
}
