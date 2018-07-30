package com.example.mostafaeisam.apicars.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mostafaeisam.apicars.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortPopUp extends Activity {
    @BindView(R.id.sr_date)
    Spinner mSrDate;
    @BindView(R.id.sr_price)
    Spinner mSrPrice;
    @BindView(R.id.sr_year)
    Spinner mSrYear;

    private ArrayList<String> mArrayListSrDate = new ArrayList<>();
    private ArrayList<String> mArrayListSrPrice = new ArrayList<>();
    private ArrayList<String> mArrayListSrYear = new ArrayList<>();
    int num;
    boolean isFirstTime = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_popup_window);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        mArrayListSrDate.add(getString(R.string.Choose));
        mArrayListSrDate.add(getString(R.string.Descending));
        mArrayListSrDate.add(getString(R.string.Ascending));
        ArrayAdapter<String> mAdapterDate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mArrayListSrDate);
        mSrDate.setAdapter(mAdapterDate);

        mSrDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //num = position;
                Intent mIntentNumModel = new Intent();

                if (isFirstTime){
                    isFirstTime=false;
                }else {
                    switch (position){
                        case 1:
                            //Toast.makeText(SortPopUp.this, "1", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",1);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                        case 2:
                            //Toast.makeText(SortPopUp.this, "2", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",2);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mArrayListSrPrice.add(getString(R.string.Choose));
        mArrayListSrPrice.add(getString(R.string.FromHighToLow));
        mArrayListSrPrice.add(getString(R.string.FromLowToHigh));
        ArrayAdapter<String> mAdapterPrice = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mArrayListSrPrice);
        mSrPrice.setAdapter(mAdapterPrice);

        mSrPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //num = position;
                Intent mIntentNumModel = new Intent();

                if (isFirstTime){
                    isFirstTime=false;
                }else {
                    switch (position) {
                        case 1:
                            //Toast.makeText(SortPopUp.this, "1", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",3);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                        case 2:
                            //Toast.makeText(SortPopUp.this, "2", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",4);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mArrayListSrYear.add(getString(R.string.Choose));
        mArrayListSrYear.add(getString(R.string.FromOldestToNewest));
        mArrayListSrYear.add(getString(R.string.FromNewestToOldest));
        ArrayAdapter<String> mAdapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mArrayListSrYear);
        mSrYear.setAdapter(mAdapterYear);

        mSrYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //num = position;
                Intent mIntentNumModel = new Intent();

                if (isFirstTime){
                    isFirstTime=false;
                }else {
                    switch (position){
                        case 1:
                            //Toast.makeText(SortPopUp.this, "1", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",5);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                        case 2:
                            //Toast.makeText(SortPopUp.this, "2", Toast.LENGTH_SHORT).show();
                            mIntentNumModel.putExtra("Sort",6);
                            setResult(RESULT_OK,mIntentNumModel);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
