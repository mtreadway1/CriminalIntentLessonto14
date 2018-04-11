package com.bignerdranch.android.criminalintentlesson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment{
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private LinearLayout mLinearLayout;
    private Button mAddButton;

    private static final int REQUEST_CRIME = 1;
    private UUID mUpdatedCrimeId;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural,
                crimeCount, crimeCount);
        if(!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.new_crime: Crime crime = new Crime();
                            CrimeLab.get(getActivity()).addCrime(crime);
                            Intent intent = CrimePagerActivity.newIntent(getActivity(),
                                    crime.getId());
                            startActivity(intent);
                            return true;
            case R.id.show_subtitle:
                            mSubtitleVisible = !mSubtitleVisible;
                            getActivity().invalidateOptionsMenu();
                            updateSubtitle();
                            return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        mLinearLayout = (LinearLayout) view.findViewById(R.id.empty_crime_list);
        mAddButton = (Button) view.findViewById(R.id.add_crime_button);
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setCrimes(crimes);
            if(mUpdatedCrimeId != null){
                mAdapter.notifyItemChanged(crimes.indexOf(crimeLab.getCrime(mUpdatedCrimeId)));
            }
            mAdapter.notifyDataSetChanged();
        }
        if(crimes.size() > 0) {
            mLinearLayout.setVisibility(View.GONE);
        }else{
            mLinearLayout.setVisibility(View.VISIBLE);
            mAddButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Crime crime = new Crime();
                    CrimeLab.get(getActivity()).addCrime(crime);
                    Intent intent = CrimePagerActivity
                            .newIntent(getActivity(), crime.getId());
                    startActivity(intent);
                }
            });
        }
        updateSubtitle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_CANCELED && requestCode == REQUEST_CRIME && data != null){
            mUpdatedCrimeId = CrimeFragment.getUUID(data);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{


        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layout){
            super(inflater.inflate(layout, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView)itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            DateFormat formatter = new SimpleDateFormat("EEE, MMM d, yyyy");
            String dateStr = formatter.format(mCrime.getDate());
            mDateTextView.setText(dateStr);
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view){
            Intent intent  = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        public static final int DOESNT_REQUIRE_POLICE = 0;
        public static final int REQUIRES_POLICE = 1;

        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if(viewType == REQUIRES_POLICE)
                return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime_police);
            return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bind(crime);

        }

        @Override
        public int getItemCount(){
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position){
            Crime crime  = mCrimes.get(position);
            if(crime.requiresPolice())
                return REQUIRES_POLICE;
            else
                return DOESNT_REQUIRE_POLICE;
        }

        public void setCrimes(List<Crime> crimes){
            mCrimes = crimes;
        }



    }
}
