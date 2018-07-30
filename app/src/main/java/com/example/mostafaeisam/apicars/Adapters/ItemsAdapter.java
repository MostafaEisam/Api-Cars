package com.example.mostafaeisam.apicars.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mostafaeisam.apicars.classes.CustomRunnable;
import com.example.mostafaeisam.apicars.R;
import com.example.mostafaeisam.apicars.classes.Items;
import com.example.mostafaeisam.apicars.utilities.Utilites;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.TimeZone;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Items> mitemsList;
    private Context Context;
    private int mWidth = 0;
    private int mHeight = 0;
    private long redTimer;
    private int mGMTOffset;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Handler handler = new Handler();
    private int numLinearlayoutView ;

    public ItemsAdapter(Context context, List<Items> itemsList) {
        this.mitemsList = itemsList;
        this.Context = context;
        this.onItemClickListener = onItemClickListener;
        this.mGMTOffset = getTimeOffset();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from ( viewGroup.getContext () );

        if (getNumLinearlayoutView() == 0)
        {
            ViewGroup linearlayoutView = ( ViewGroup ) mInflater.inflate ( R.layout.row_items, viewGroup, false );
            return new CarHolderLinearLayout(linearlayoutView);
        }
        else
        {
            ViewGroup gridView = ( ViewGroup ) mInflater.inflate ( R.layout.grid_view_items, viewGroup, false );
            return new CarHolderLinearLayout(gridView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {

        final Items car = mitemsList.get(position);

        final CarHolderLinearLayout carHolderLinearLayout = (CarHolderLinearLayout) holder;
        redTimer = mitemsList.get(position).getGetAuctionInfo().getEndDate();

        //get Width and height for imageView
        if (mWidth == 0 && mHeight == 0) {
            carHolderLinearLayout.mIvCarImage.post(new Runnable() {
                @Override
                public void run() {
                    mWidth = carHolderLinearLayout.mIvCarImage.getMeasuredWidth();
                    mHeight = carHolderLinearLayout.mIvCarImage.getMeasuredHeight();
                }
            });

            Picasso.get()
                    .load(mitemsList.get(position).getImage().replace("[w]", "300").replace("[h]", "200"))
                    .fit()
                    .into(carHolderLinearLayout.mIvCarImage);
        } else {
            Picasso.get()
                    .load(mitemsList.get(position).getImage().replace("[w]", mWidth + "").replace("[h]", mHeight + ""))
                    .fit()
                    .into(carHolderLinearLayout.mIvCarImage);
        }

        //Parse Data to Views
        if (Utilites.isArabicLocale()) {
            carHolderLinearLayout.mTvCarModel.setText(car.getMakeAr());
            carHolderLinearLayout.mTvPrice.setText(String.valueOf(car.getGetAuctionInfo().getCurrentPrice()));
            carHolderLinearLayout.mTvCurrency.setText(car.getGetAuctionInfo().getCurrencyAr());
            carHolderLinearLayout.mTvLot.setText(String.valueOf(car.getGetAuctionInfo().getLot()));
            carHolderLinearLayout.mTvBid.setText(String.valueOf(car.getGetAuctionInfo().getBids()));
            carHolderLinearLayout.customRunnable.holder = carHolderLinearLayout.mTvTimeLeft;

            handler.removeCallbacks(carHolderLinearLayout.customRunnable);
            carHolderLinearLayout.customRunnable.millisUntilFinished = (1000 * mitemsList.get(position).getGetAuctionInfo().getEndDate()) + mGMTOffset; //Current time - received time
            handler.postDelayed(carHolderLinearLayout.customRunnable, 100);

        } else {
            carHolderLinearLayout.mTvCarModel.setText(car.getMakeEn());
            carHolderLinearLayout.mTvPrice.setText(String.valueOf(car.getGetAuctionInfo().getCurrentPrice()));
            carHolderLinearLayout.mTvCurrency.setText(car.getGetAuctionInfo().getCurrencyEn());
            carHolderLinearLayout.mTvLot.setText(String.valueOf(car.getGetAuctionInfo().getLot()));
            carHolderLinearLayout.mTvBid.setText(String.valueOf(car.getGetAuctionInfo().getBids()));
            carHolderLinearLayout.customRunnable.holder = carHolderLinearLayout.mTvTimeLeft;

            handler.removeCallbacks(carHolderLinearLayout.customRunnable);
            carHolderLinearLayout.customRunnable.millisUntilFinished = 1000 * car.getGetAuctionInfo().getEndDate() + mGMTOffset; //Current time - received time
            handler.postDelayed(carHolderLinearLayout.customRunnable, 100);
        }

        if (car.isUpdated()) {
            addFlashToUpdatedCars(carHolderLinearLayout.mCvRowItems);
        } else {
            removeFlashFromUpdatedCars(carHolderLinearLayout.mCvRowItems);
        }

        if (!mitemsList.get(position).isFavorite()){
            carHolderLinearLayout.mIbFavoriteCar.setBackgroundResource(R.drawable.ic_favorite_negative);
        }else{
            carHolderLinearLayout.mIbFavoriteCar.setBackgroundResource(R.drawable.ic_favorite_positive);
        }

        carHolderLinearLayout.mIbFavoriteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mitemsList.get(position).isFavorite()){
                    carHolderLinearLayout.mIbFavoriteCar.setBackgroundResource(R.drawable.ic_favorite_negative);
                    mitemsList.get(position).setFavorite(false);
                }else{
                    carHolderLinearLayout.mIbFavoriteCar.setBackgroundResource(R.drawable.ic_favorite_positive);
                    mitemsList.get(position).setFavorite(true);
                }
                Toast.makeText(Context, "Favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFlashToUpdatedCars(CardView cv_rowItems) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        cv_rowItems.startAnimation(anim);
    }

    private void removeFlashFromUpdatedCars(CardView cv_rowItems) {
        if (cv_rowItems.getAnimation() != null)
            cv_rowItems.getAnimation().cancel();
    }

    private int getTimeOffset() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getRawOffset();
    }

    @Override
    public int getItemCount() {
        return mitemsList.size();
    }

    public int getNumLinearlayoutView() {
        return numLinearlayoutView;
    }

    public void setNumLinearlayoutView(int numLinearlayoutView) {
        this.numLinearlayoutView = numLinearlayoutView;
    }

    public class CarHolderLinearLayout extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_carModel)
        TextView mTvCarModel;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_lot)
        TextView mTvLot;
        @BindView(R.id.tv_bids)
        TextView mTvBid;
        @BindView(R.id.tv_timeLeft)
        TextView mTvTimeLeft;
        @BindView(R.id.tv_currency)
        TextView mTvCurrency;
        @BindView(R.id.iv_carImage)
        ImageView mIvCarImage;
        @BindView(R.id.cv_rowItems)
        CardView mCvRowItems;
        @BindView(R.id.ib_favoriteCar)
        ImageButton mIbFavoriteCar;
        public CustomRunnable customRunnable;

        public CarHolderLinearLayout(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            customRunnable = new CustomRunnable(1000, mTvTimeLeft, handler);
        }
    }



}
