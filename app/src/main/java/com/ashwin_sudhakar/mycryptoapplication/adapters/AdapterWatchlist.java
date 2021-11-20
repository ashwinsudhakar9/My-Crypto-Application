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
import com.ashwin_sudhakar.mycryptoapplication.model.ModelCoins;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterWatchlist extends RecyclerView.Adapter<AdapterWatchlist.ViewHolder> {

    private ArrayList<ModelCoins> modelCoinsList;

    private Context context;

    public AdapterWatchlist(ArrayList<ModelCoins> modelCoinsList, Context context) {

        this.modelCoinsList = modelCoinsList;

        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView img_Coin;
        private final TextView txt_CoinName;
        private final TextView txt_Coin_Symbol;
        private final TextView txt_Current_Price;
        private final TextView txt_Price_Change;
        private final TextView txt_Updated_Date;
        private final ImageView img_Price_Change;


        public ViewHolder(@NonNull View itemView) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ModelCoins coinDetails = modelCoinsList.get(position);

        holder.txt_CoinName.setText(coinDetails.getName());
        holder.txt_Coin_Symbol.setText(coinDetails.getSymbol());
        holder.txt_Current_Price.setText("$" + coinDetails.getCurrentPrice().toString());
        if (coinDetails.getPriceChange() < 0) {
            holder.img_Price_Change.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_down));
            holder.img_Price_Change.setColorFilter(ContextCompat.getColor(context, R.color.red));
            holder.txt_Price_Change.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.txt_Price_Change.setText(String.format("%.2f", Double.parseDouble(coinDetails.getPriceChange().toString())) + "%");
        } else {
            holder.img_Price_Change.setColorFilter(ContextCompat.getColor(context, R.color.green));
            holder.txt_Price_Change.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.txt_Price_Change.setText(String.format("%.2f", Double.parseDouble(coinDetails.getPriceChange().toString())) + "%");
        }
        holder.txt_Updated_Date.setText("Updated at " + coinDetails.getDate());
        Glide.with(context).load(coinDetails.getImage()).error(R.drawable.ic_error_image).into(holder.img_Coin);

    }

    @Override
    public int getItemCount() {
        return modelCoinsList.size();
    }
}
