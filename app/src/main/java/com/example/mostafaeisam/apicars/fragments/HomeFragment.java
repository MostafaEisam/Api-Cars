package com.example.mostafaeisam.apicars.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafaeisam.apicars.Adapters.ItemsAdapter;
import com.example.mostafaeisam.apicars.PopUps.FilterPopUp;
import com.example.mostafaeisam.apicars.R;
import com.example.mostafaeisam.apicars.Services.RequestListener;
import com.example.mostafaeisam.apicars.Services.ServiceFactory;
import com.example.mostafaeisam.apicars.PopUps.SortPopUp;
import com.example.mostafaeisam.apicars.utilities.SortValues;
import com.example.mostafaeisam.apicars.classes.Items;
import com.example.mostafaeisam.apicars.responses.GetCarsListResponse;
import com.example.mostafaeisam.apicars.utilities.InternetConnection;
import com.example.mostafaeisam.apicars.utilities.Utilites;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements RequestListener {
    @BindView(R.id.rv_cars)
    RecyclerView mRvCars;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mPullToRefresh;
    @BindView(R.id.ib_toolbar_backArrow)
    ImageButton toolbar_backArrow;
    @BindView(R.id.tv_gridViewText)
    TextView mTvGridViewText;
    @BindView(R.id.cl_ConstraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.ll_gridView)
    LinearLayout mGridViewButton;
    @BindView(R.id.ll_sort)
    LinearLayout mSortButton;
    @BindView(R.id.ll_filterList)
    LinearLayout mFilterButton;

    private ArrayList<String> mModelsList;
    private ArrayList<Items> mFilterList;
    private ItemsAdapter mRvCarsAdapter;
    private ArrayList<Items> mCarsList;
    private ArrayList<Items> mCarsListRefresh;
    private String mTicks;
    private int mRefreshInterval;
    private String mUrl;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        toolbarBackArrowView();
        initView(view);
        getCarsData();
        gridViewButton();
        sortButton();
        filterButton();
        pullToRefresh();
        linearLayoutRecyclerView();
        return view;
    }


    private void linearLayoutRecyclerView() {
        mRvCars.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCarsAdapter = new ItemsAdapter(getActivity(), mCarsList);
        mRvCars.setAdapter(mRvCarsAdapter);
    }

    private void pullToRefresh() {
        mPullToRefresh.setColorSchemeColors(Color.parseColor("#EA5454"), Color.parseColor("#5db25d"));
        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnection.checkConnection(getActivity())) {
                    getCarsData();
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    if (mPullToRefresh.isRefreshing()) {
                        mPullToRefresh.setRefreshing(false);
                    }
                }
            }

        });
    }

    private void gridViewButton() {
        mGridViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mFilterList.isEmpty()){
                    gridView(mFilterList);
                }else {
                    gridView(mCarsList);
                }
            }
        });
    }

    private void sortButton() {
        mSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "Sort Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), SortPopUp.class);
                startActivityForResult(i, 2);
            }
        });
    }

    private void filterButton() {
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Filter Clicked", Toast.LENGTH_SHORT).show();

                String mCarsListModel;

                for (int i = 0; i < mCarsList.size(); i++) {
                    mCarsListModel = mCarsList.get(i).getModelEn();
                    //modelsList.add(mCarsListModel);
                    if (!mModelsList.contains(mCarsListModel)) {
                        mModelsList.add(mCarsListModel);
                    }
                }

                Intent i = new Intent(getActivity(), FilterPopUp.class);
                i.putStringArrayListExtra("spinnerList", mModelsList);
                startActivityForResult(i, 1);

            }
        });
    }

    private void gridView(ArrayList<Items> list) {
        //convert to LinearlayoutView and change text to GridView
        if (mRvCarsAdapter.getNumLinearlayoutView() != 0) {
            mRvCars.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRvCarsAdapter = new ItemsAdapter(getActivity(), list);
            mRvCarsAdapter.setNumLinearlayoutView(0);
            mRvCars.setAdapter(mRvCarsAdapter);
            mTvGridViewText.setText(getString(R.string.GridView));
        } else {
            //convert to GridLayoutView and change text to ListView
            mRvCars.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRvCarsAdapter = new ItemsAdapter(getActivity(), list);
            mRvCarsAdapter.setNumLinearlayoutView(1);
            mRvCars.setAdapter(mRvCarsAdapter);
            mTvGridViewText.setText(getString(R.string.ListView));
        }
        mRvCarsAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        mCarsList = new ArrayList<>();
        mCarsListRefresh = new ArrayList<>();
        mFilterList = new ArrayList<>();
        mModelsList = new ArrayList<>();
    }

    public void getCarsData() {
        if (mCarsList.isEmpty()) {
            //to get original Data From Server
            ServiceFactory.getData(getActivity(), "http://api.emiratesauction.com/v2/carsonline", this);
        } else {
            //to get Updated Data From Server
            ServiceFactory.getData(getActivity(), mUrl, this);
        }
    }

    @Override
    public void onSuccess(final Object object) {
        if (mCarsList.isEmpty()) {
            //Bind Original Data in List
            mCarsList.clear();
            GetCarsListResponse mAllCarsListResponse = new Gson().fromJson((String) object, GetCarsListResponse.class);
            mCarsList.addAll(mAllCarsListResponse.getCarsList());
            mTicks = mAllCarsListResponse.getTicks();
            mRefreshInterval = mAllCarsListResponse.getRefreshInterval();
            updateUrl();

            getActivity().runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    mRvCarsAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    mTvLoading.setVisibility(View.INVISIBLE);
                }
            }));

        } else {
            //Bind Updated Data in List and Comparison it with Orginal List
            getActivity().runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

                    //your codes here
                    GetCarsListResponse mAllCarsListResponse = new Gson().fromJson((String) object, GetCarsListResponse.class);
                    //Toast.makeText(getActivity(), "" + mAllCarsListResponse.getCount(), Toast.LENGTH_SHORT).show();
                    mCarsListRefresh.addAll(mAllCarsListResponse.getCarsList());
                    updateUrl();

                    for (int i = 0; i < mCarsListRefresh.size(); i++) {
                        int carIdRefreshed = mCarsListRefresh.get(i).getCarID();
                        for (int n = 0; n < mCarsList.size(); n++) {
                            int carId = mCarsList.get(n).getCarID();
                            if (carIdRefreshed == carId) {
                                mCarsListRefresh.get(i).setUpdated(true);
                                mCarsList.set(n, mCarsListRefresh.get(i));
                            }
                        }
                    }
                    mRvCarsAdapter.notifyDataSetChanged();
                    if (mPullToRefresh.isRefreshing()) {
                        mPullToRefresh.setRefreshing(false);
                    }
                }
            }));
        }
    }

    private void updateUrl() {
        mUrl = "http://api.emiratesauction.com/v2/carsonline?Ticks=" + mTicks + "&RefreshInterval=" + mRefreshInterval + "";
    }

    @Override
    public void onFailure(int errorCode) {
        Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
    }

    private void toolbarBackArrowView() {
        if (Utilites.isArabicLocale() == true) {
            toolbar_backArrow.setBackgroundResource(R.drawable.ic_toolbar_back_arrow_ar);
        } else {
            toolbar_backArrow.setBackgroundResource(R.drawable.ic_toolbar_back_arrow);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            int mNumModelOnList = data.getIntExtra("NumModelOnList", 201);
            if (mNumModelOnList==10000){
                gridView(mCarsList);
                mFilterList.clear();

            }else {
                String mModelSelected = mModelsList.get(mNumModelOnList);
                mFilterList.clear();
                for (int i = 0; i < mCarsList.size(); i++) {
                    String mModelOnCarsList = mCarsList.get(i).getModelEn();
                    if (mModelSelected.equals(mModelOnCarsList)) {
                        mFilterList.add(mCarsList.get(i));
                    }
                }
                gridView(mFilterList);
            }

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            int i = data.getIntExtra("Sort", 301);
            //Toast.makeText(getActivity(), ""+ i, Toast.LENGTH_SHORT).show();

            if (SortValues.sortDateDscending().equals(String.valueOf(i))) {
                //Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (String.valueOf(o2.getGetAuctionInfo().getEndDate()).compareTo(String.valueOf(o1.getGetAuctionInfo().getEndDate())));
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (String.valueOf(o2.getGetAuctionInfo().getEndDate()).compareTo(String.valueOf(o1.getGetAuctionInfo().getEndDate())));
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }

            } else if (SortValues.sortDateAscending().equals(String.valueOf(i))) {
               // Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (String.valueOf( o1.getGetAuctionInfo().getEndDate()).compareTo(String.valueOf(o2.getGetAuctionInfo().getEndDate())));
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (String.valueOf( o1.getGetAuctionInfo().getEndDate()).compareTo(String.valueOf(o2.getGetAuctionInfo().getEndDate())));
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }


            } else if (SortValues.sortPriceDscending().equals(String.valueOf(i))) {
                //Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o2.getGetAuctionInfo().getCurrentPrice()- o1.getGetAuctionInfo().getCurrentPrice());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o2.getGetAuctionInfo().getCurrentPrice()- o1.getGetAuctionInfo().getCurrentPrice());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }


            } else if (SortValues.sortPriceAscending().equals(String.valueOf(i))) {
                //Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o1.getGetAuctionInfo().getCurrentPrice()- o2.getGetAuctionInfo().getCurrentPrice());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o1.getGetAuctionInfo().getCurrentPrice()- o2.getGetAuctionInfo().getCurrentPrice());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }



            } else if (SortValues.sortYearDscending().equals(String.valueOf(i))) {
               // Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o1.getYear()) - (o2.getYear());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o1.getYear()) - (o2.getYear());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }

            } else if (SortValues.sortYearAscending().equals(String.valueOf(i))) {
               // Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                if (!mFilterList.isEmpty()){
                    Collections.sort(mFilterList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o2.getYear()) - (o1.getYear());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }else {
                    Collections.sort(mCarsList, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return (o2.getYear()) - (o1.getYear());
                        }
                    });
                    mRvCarsAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
