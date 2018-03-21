package com.bignerdranch.android.criminalintentlesson;




public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected android.support.v4.app.Fragment createFragment() {

        return new CrimeFragment();
    }
}
