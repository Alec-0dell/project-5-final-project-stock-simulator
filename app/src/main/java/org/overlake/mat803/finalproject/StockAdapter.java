package org.overlake.mat803.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.overlake.mat803.finalproject.databinding.ListItemBinding;

import java.util.List;


class StockAdapter extends ListAdapter<Stock, StockAdapter.WordListViewHolder>
        implements StockPriceHelper.UpdateListener{

    public static final DiffUtil.ItemCallback<Stock> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Stock>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Stock oldItem, @NonNull Stock newItem) {
                    return oldItem.symbol() == newItem.symbol();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Stock oldItem, @NonNull Stock newItem) {
                    return oldItem.equals(newItem);
                }
            };
    LayoutInflater mLayoutInflater;
    StockPriceHelper mStockPriceHelper;
    NavController mController;
    int holderCount;
    boolean mSummary;

    protected StockAdapter(Fragment owner, LayoutInflater inflater, NavController controller, boolean summary) {
        super(DIFF_CALLBACK);
        mLayoutInflater = inflater;
        mController = controller;
        mStockPriceHelper = new StockPriceHelper(owner, this, summary);
        mSummary = summary;
    }

    void insertDefaultStocks() {
        mStockPriceHelper.insertDefault();
    }

    @NonNull
    @Override
    public WordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(mLayoutInflater);
        Log.d("WordListAdapter", "Holder created: " + ++holderCount);
        return new WordListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListViewHolder holder, int position) {
        Log.d("WordListAdapter", "Binding at: " + holderCount);
        Stock item = getItem(position);
        if(mSummary){
            if(Boolean.TRUE.equals(item.bought()) && item.amountBought() != null && item.costBasis() != null) {
                float price = item.currentPrice();
                float amount = item.amountBought();
                float costBasis = item.costBasis();
                float percentChange = price/costBasis - 1;
                float profit = (amount * price) - (costBasis * amount);
                if(percentChange > 0){
                    holder.setTextColor(Color.parseColor("#00FF00"));
                } else if(percentChange < 0) {
                    holder.setTextColor(Color.parseColor("#FF0000"));
                }
                holder.setText(item.symbol() + "  " + price + "  " + percentChange + "  " + profit);
            }else{
                holder.setText("err");
            }
        } else {
            float price = item.currentPrice();
            float percentChange = item.percentageChange();
            if(percentChange > 0){
                holder.setTextColor(Color.parseColor("#00FF00"));
            } else if(percentChange < 0) {
                holder.setTextColor(Color.parseColor("#FF0000"));
            }
            holder.setText(item.symbol() + "  " + price + "  " + percentChange);
        }
    }

    @Override
    public synchronized void onUpdate(List<Stock> stocks) {
        submitList(stocks);
    }

    public void searchStock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mLayoutInflater.getContext());
        builder.setTitle("Enter stock symbol:");
        final EditText input = new EditText(mLayoutInflater.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String symbol = input.getText().toString();
                Log.d("Wordlistadapter", "onClick: " + symbol);
                mStockPriceHelper.search(symbol);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    class WordListViewHolder extends RecyclerView.ViewHolder {

        private TextView mWord;
        private Float mUserInput;

        public WordListViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            mWord = binding.item;
            mWord.setOnClickListener(v-> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mLayoutInflater.getContext());
                builder.setTitle("Enter amount to buy or sell:");

                final EditText input = new EditText(mLayoutInflater.getContext());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            mUserInput = Float.parseFloat(input.getText().toString());
                            String stockData = mWord.getText().toString();
                            String arr[] = stockData.split(" ", 2);
                            String symbol = arr[0];
                            mStockPriceHelper.buy(symbol,mUserInput);
                            mController.navigate(R.id.action_listFragment_to_summaryFragment);
                        } catch (Exception NumberFormatException){
                            Toast.makeText(itemView.getContext(), "Please input a valid response", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            });
        }
        public void setText(String word) {
            mWord.setText(word);
        }
        public void setTextColor(int color){mWord.setTextColor(color);}
    }



}
