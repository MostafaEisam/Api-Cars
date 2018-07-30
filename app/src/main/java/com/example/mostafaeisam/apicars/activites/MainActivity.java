package com.example.mostafaeisam.apicars.activites;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.mostafaeisam.apicars.utilities.InternetConnection;
import com.example.mostafaeisam.apicars.R;
import com.example.mostafaeisam.apicars.helpers.BottomNavigationViewHelper;
import com.example.mostafaeisam.apicars.fragments.EditFragment;
import com.example.mostafaeisam.apicars.fragments.FavoriteFragment;
import com.example.mostafaeisam.apicars.fragments.HomeFragment;
import com.example.mostafaeisam.apicars.fragments.ListFragment;
import com.example.mostafaeisam.apicars.fragments.BidsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btv_buttons)          BottomNavigationView bottomNavigationView;
    @BindView(R.id.bt_retry)             Button bt_retry;
    @BindView(R.id.fragment_container)   FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        CheckingInternetConnection();

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        setHomeFragment();
                        break;
                    case R.id.action_edit:
                        Toast.makeText(MainActivity.this, "edit Clicked", Toast.LENGTH_SHORT).show();
                        setEditFragment();
                        break;
                    case R.id.action_favorite:
                        Toast.makeText(MainActivity.this, "favorite Clicked", Toast.LENGTH_SHORT).show();
                        setFavoriteFragment();
                        break;
                    case R.id.action_mybids:
                        Toast.makeText(MainActivity.this, "My Bids Clicked", Toast.LENGTH_SHORT).show();
                        setMybidsFragment();
                        break;
                    case R.id.action_list:
                        Toast.makeText(MainActivity.this, "List Clicked", Toast.LENGTH_SHORT).show();
                        setListFragment();
                        break;
                }
                return true;
            }
        });

    }

    private void initView() {
        //used to hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
    }

    private void setListFragment() {
        android.support.v4.app.FragmentManager fragmentManagerList = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransactionList = fragmentManagerList.beginTransaction();
        ListFragment listFragment = new ListFragment();
        fragmentTransactionList.add(R.id.fragment_container, listFragment);
        fragmentTransactionList.commit();
    }

    private void setMybidsFragment() {
        android.support.v4.app.FragmentManager fragmentManagerMybids = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransactionMybids = fragmentManagerMybids.beginTransaction();
        BidsFragment bidsFragment = new BidsFragment();
        fragmentTransactionMybids.add(R.id.fragment_container, bidsFragment);
        fragmentTransactionMybids.commit();
    }

    private void setFavoriteFragment() {
        android.support.v4.app.FragmentManager fragmentManagerFavorite = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransactionFavorite = fragmentManagerFavorite.beginTransaction();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        fragmentTransactionFavorite.add(R.id.fragment_container, favoriteFragment);
        fragmentTransactionFavorite.commit();
    }

    private void setEditFragment() {
        android.support.v4.app.FragmentManager fragmentManagerEdit = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManagerEdit.beginTransaction();
        EditFragment editFragment = new EditFragment();
        fragmentTransaction.add(R.id.fragment_container, editFragment);
        fragmentTransaction.commit();
    }

    private void setHomeFragment() {
        android.support.v4.app.FragmentManager fragmentManagerHome = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransactionHome = fragmentManagerHome.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransactionHome.add(R.id.fragment_container, homeFragment);
        fragmentTransactionHome.commit();
    }

    private void CheckingInternetConnection() {
        if (InternetConnection.checkConnection(this)) {
            fragment_container.setVisibility(View.VISIBLE);
            bt_retry.setVisibility(View.GONE);
            setHomeFragment();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            fragment_container.setVisibility(View.GONE);
            bt_retry.setVisibility(View.VISIBLE);
        }
    }

    public void goBackButton(View view) {
        onBackPressed();
    }

    public void bt_Retry(View view) {
        CheckingInternetConnection();
    }
}
