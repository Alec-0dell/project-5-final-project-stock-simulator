package org.overlake.mat803.finalproject;

import com.google.auto.value.AutoValue;
import com.google.auto.value.AutoValue.CopyAnnotations;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@AutoValue
@Entity
public abstract class Stock {
        @CopyAnnotations
        @PrimaryKey
        @NonNull
        public abstract String symbol();

        @CopyAnnotations
        @ColumnInfo(name = "current_price")
        public abstract float currentPrice();

        @CopyAnnotations
        @ColumnInfo(name = "percentage_change")
        public abstract float percentageChange();

        @CopyAnnotations
        @ColumnInfo(name = "bought")
        @Nullable
        public abstract Boolean bought();

        @CopyAnnotations
        @ColumnInfo(name = "cost_basis")
        @Nullable
        public abstract Float costBasis();

        @CopyAnnotations
        @ColumnInfo(name = "amount_bought")
        @Nullable
        public abstract Float amountBought();

        public static Stock create(String symbol, float currentPrice, float percentageChange, Boolean bought, Float costBasis, Float amountBought) {
                return new AutoValue_Stock(symbol, currentPrice, percentageChange, bought, costBasis, amountBought);
        }
}
