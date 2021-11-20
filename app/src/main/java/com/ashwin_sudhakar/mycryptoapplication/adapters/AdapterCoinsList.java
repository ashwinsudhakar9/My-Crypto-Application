package com.ashwin_sudhakar.mycryptoapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin_sudhakar.mycryptoapplication.R;
import com.ashwin_sudhakar.mycryptoapplication.model.ModelCoinDetails;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCoinsList extends RecyclerView.Adapter<AdapterCoinsList.CoinListViewHolder> {

    public interface CoinsListItemInterface {

        void onItemClickListener(View v, List<ModelCoinDetails> model, int position);
    }

    List<ModelCoinDetails> modelCoinDetails;
    Context context;

    CoinsListItemInterface coinsListItemInterface;

    public static class CoinListViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView img_Coin;
        private final TextView txt_CoinName;
        private final TextView txt_Coin_Symbol;
        private final TextView txt_Current_Price;
        private final TextView txt_Price_Change;
        private final TextView txt_Updated_Date;
        private final ImageView img_Price_Change;


        public CoinListViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Coin = itemView.findViewById(R.id.img_coin);
            txt_CoinName = itemView.findViewById(R.id.txt_CoinName);
            txt_Coin_Symbol = itemView.findViewById(R.id.txt_Coin_Symbol);
            txt_Current_Price = itemView.findViewById(R.id.txt_Current_Price);
            txt_Price_Change = itemView.findViewById(R.id.txt_Price_Change);
            txt_Updated_Date = itemView.findViewById(R.id.txt_Updated_Date);
            img_Price_Change = itemView.findViewById(R.id.img_Price_Change);

        }
    }

    public AdapterCoinsList(Context context, List<ModelCoinDetails> modelCoinDetails, CoinsListItemInterface listener) {
        this.modelCoinDetails = modelCoinDetails;
        this.context = context;
        this.coinsListItemInterface = listener;
    }

    @NonNull
    @Override
    public CoinListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item, parent, false);

        return new CoinListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CoinListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ModelCoinDetails coinDetails = modelCoinDetails.get(position);

        holder.txt_CoinName.setText(coinDetails.getName());
        holder.txt_Coin_Symbol.setText(coinDetails.getSymbol());
        if (coinDetails.getCurrency()=="INR"){
            double inr = 74.31;
            double currentPrice = inr * Double.parseDouble(coinDetails.getCurrent_price().toString());
            holder.txt_Current_Price.setText("₹" + currentPrice);
        }
        else if (coinDetails.getCurrency()=="EUR"){
            double eur = 0.89;
            double currentPrice = eur * Double.parseDouble(coinDetails.getCurrent_price().toString());
            holder.txt_Current_Price.setText("€" + currentPrice);
        }
        else{
            holder.txt_Current_Price.setText("$" + coinDetails.getCurrent_price().toString());
        }

        if (coinDetails.getPrice_change_24h() < 0) {
            holder.img_Price_Change.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_down));
            holder.img_Price_Change.setColorFilter(ContextCompat.getColor(context, R.color.red));
            holder.txt_Price_Change.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.txt_Price_Change.setText(String.format("%.2f", Double.parseDouble(coinDetails.getMarket_cap_change_percentage_24h().toString())) + "%");
        } else {
            holder.img_Price_Change.setColorFilter(ContextCompat.getColor(context, R.color.green));
            holder.txt_Price_Change.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.txt_Price_Change.setText(String.format("%.2f", Double.parseDouble(coinDetails.getMarket_cap_change_percentage_24h().toString())) + "%");
        }
        holder.txt_Updated_Date.setText("Updated at " + coinDetails.getLast_updated_Date());
        Glide.with(context).load(coinDetails.getImageUrl()).error(R.drawable.ic_error_image).into(holder.img_Coin);


        holder.itemView.setOnClickListener(v -> coinsListItemInterface.onItemClickListener(v, modelCoinDetails, position));
    }

    @Override
    public int getItemCount() {
        return modelCoinDetails.size();
    }

    public void filterList(ArrayList<ModelCoinDetails> filteredList) {
        modelCoinDetails = filteredList;
        notifyDataSetChanged();
    }

}