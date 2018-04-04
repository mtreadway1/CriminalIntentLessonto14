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

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<Crime>();
       /* for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            //all odds will be false, all evens will be true
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 2 == 0);
            mCrimes.add(crime);
        }*/

    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        Crime crime = new Crime();
        crime.setId(id);
        int index = mCrimes.indexOf(crime);
        if(index >= 0)
            return mCrimes.get(index);
        return null;
    }
}
