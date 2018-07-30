package com.example.mostafaeisam.apicars.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mostafaeisam.apicars.R;
import com.example.mostafaeisam.apicars.fragments.HomeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterPopUp extends Activity {

    ArrayList<String> modelsList = new ArrayList<>();
    @BindView(R.id.sr_Models)
    Spinner mSrModels;
    @BindView(R.id.bt_showAll)
    Button mBtShowAll;
    boolean isFirstTime = true;
    int mNumModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_popup_window);

        ButterKnife.bind(this);

        Intent i = getIntent();
        modelsList.add(getString(R.string.Choose));
        modelsList.addAll(i.getStringArrayListExtra("spinnerList"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modelsList);
        mSrModels.setAdapter(adapter);

        mSrModels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                mNumModel = position - 1;
                //Toast.makeText(FilterPopUp.this, ""+mNumModel, Toast.LENGTH_SHORT).show();
                if (isFirstTime) {
                    isFirstTime = false;
                } else {
                    Intent mIntentNumModel = new Intent();
                    mIntentNumModel.putExtra("NumModelOnList", mNumModel);
                    setResult(RESULT_OK, mIntentNumModel);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        showAllButton();
    }

    private void showAllButton() {
        mBtShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumModel = 10000;
                Intent mIntentNumModel = new Intent();
                mIntentNumModel.putExtra("NumModelOnList", mNumModel);
                setResult(RESULT_OK, mIntentNumModel);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
    }
}
