package org.overlake.mat803.finalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StockDAO {
    @Query("SELECT * FROM Stock ORDER BY symbol")
    LiveData<List<Stock>> getAll();

    @Query("SELECT * FROM Stock WHERE bought = :requested ORDER BY symbol")
    LiveData<List<Stock>> getBought(boolean requested);

    @Query("SELECT * FROM Stock ORDER BY symbol")
    List<Stock> getSymbols();

    @Query("SELECT * FROM Stock WHERE symbol = :requested")
    LiveData<Stock> getStock(String requested);

    @Query("SELECT * FROM Stock WHERE bought = :requested")
    List<Stock> boughtStocks(boolean requested);

    @Query("UPDATE Stock SET current_price = :dollar, percentage_change = :percent WHERE symbol = :requested")
    void updateQuote(String requested, float dollar, float percent);

    @Query("UPDATE Stock SET bought = :buy, amount_bought = :amount, cost_basis =:basis WHERE symbol = :requested")
    void buy(String requested, boolean buy, float amount, float basis);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock symbol);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Stock symbol);

    @Delete
    void delete(Stock symbol);
}

