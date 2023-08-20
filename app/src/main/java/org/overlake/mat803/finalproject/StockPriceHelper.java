package org.overlake.mat803.finalproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockPriceHelper {


    public interface UpdateListener {
        void onUpdate(List<Stock> stocks);
    }


    ExecutorService executor = Executors.newSingleThreadExecutor();
    StockApiHelper api = new StockApiHelper();
    private final UpdateListener listener;
    private final AppDatabase db;

    private final Observer<List<Stock>> stockChangedObserver = new Observer<List<Stock>>() {
        @Override
        public void onChanged(List<Stock> stocks) {
            Log.d("StockPriceHelper", "Changed " + stocks);
            listener.onUpdate(stocks);

        }
    };

    public StockPriceHelper(Fragment owner, UpdateListener listener, boolean summary) {
        this.listener = listener;
        this.db = Room.databaseBuilder(owner.getContext().getApplicationContext(),
                AppDatabase.class, "stock-database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        if(summary){
            this.db.stockDAO().getBought(true)
                    .observe(owner, stockChangedObserver);
        }else{
            this.db.stockDAO().getAll()
                    .observe(owner, stockChangedObserver);
        }
        updateAll();

    }

    public void insertDefault(){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> tickers = new ArrayList<>(Arrays.asList("AAPL","NVDA", "GOOG", "DIS", "NFLX", "META", "AMZN", "WMT", "MCD", "MSFT", "AMD", "INTC", "BA" , "DAL" , "UBER", "F", "BAC", "LYFT", "AAL", "SOFI"));
                    insertList(tickers);
                }
                catch(Throwable error) {
                    Log.e("StockPriceHelper", "Exception", error);
                }
            }
        });

    }

    public void search(String symbol) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    insertStock(symbol);
                }
                catch(Throwable error) {
                    //doessnt work ImeBackDispatcher???//Toast.makeText(context, "Please input a valid response", Toast.LENGTH_LONG).show();
                    Log.e("StockPriceHelper", "Exception", error);
                }
            }
        });
    }

    private void insertList(ArrayList<String> tickers) {
        for (String stock : tickers) {
            insertStock(stock);
        }
    }

    private void insertStock(String symbol){
        Stock quote = api.getStockQuote(symbol);
        db.stockDAO().insert(quote);
    }


    public void updateAll() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    getPricesAndUpdateAll();
                }
                catch(Throwable error) {
                    Log.e("StockPriceHelper", "Exception", error);
                }
            }
        });
    }

    public void buy(String symbol, Float amount){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    buyStock(symbol,amount);
                }
                catch(Throwable error) {
                    Log.e("StockPriceHelper", "Exception", error);
                }
            }
        });
    }

    private void buyStock(String symbol, Float amount){
        Stock quote = api.getStockQuote(symbol);
        db.stockDAO().buy(symbol, true, amount, quote.currentPrice());
    }


    private void getPricesAndUpdateAll() {
        // Make a copy of the set of keys so that we don't get exception if that changes
        List<Stock> stocks = db.stockDAO().getSymbols();
        for (Stock stock: stocks) {
            String symbol = stock.symbol();
            Stock quote = api.getStockQuote(symbol);
            db.stockDAO().updateQuote(quote.symbol(), quote.currentPrice(), quote.percentageChange());
            Log.d("StockPriceHelper", "Update " );
        }
    }
}