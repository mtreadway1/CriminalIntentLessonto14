package com.bignerdranch.android.criminalintentlesson;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimelab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if(sCrimelab == null)
            sCrimelab = new CrimeLab(context);
        return sCrimelab;
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<Crime>();
        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #");
            //all odds will be false, all evens will be true
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 2 == 0);
            mCrimes.add(crime);
        }

    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id))
                return crime;
        }
        return null;
    }
}
