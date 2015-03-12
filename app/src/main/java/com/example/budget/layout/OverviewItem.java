package com.example.budget.layout;

import java.text.NumberFormat;

public class OverviewItem implements Comparable<OverviewItem> {
    public static OverviewItem defaultItem = new OverviewItem();

    public String categoryName;
    public int categoryId;
    public double actual;
    public double projection;

    public OverviewItem() {
        categoryName = "Default";
        actual = 0.0;
        projection = 0.0;
    }

    public OverviewItem(String cat, double act, double est) {
        categoryName = cat;
        actual = act;
        projection = est;
    }

    public String getActualAmountString() {
        return NumberFormat.getCurrencyInstance().format(actual);
    }

    public String getEstimateAmountString() {
        return NumberFormat.getCurrencyInstance().format(projection);
    }

    public String toString() {
        return categoryName + " | " + NumberFormat.getCurrencyInstance().format(actual) + " | "
                + NumberFormat.getCurrencyInstance().format(projection);
    }

    public boolean hasZeroEstimate() {
        if (projection == 0.0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOverspent() {
        if (!hasZeroEstimate()) {
            if (actual > projection) {
                return true;
            } else {
                return false;
            }
        } else {
            if (actual > 0) {
                return true;

            } else {
                return false;
            }
        }
    }

    @Override
    public int compareTo(OverviewItem another) {
        int result = 0;
        if (another.equals(defaultItem)) {
            result = 1;
        } else if (this.equals(defaultItem)) {
            result = 0;
        } else {
            result = another.getProgress() - getProgress();
            if (result == 0) {
                result = (int) (another.actual - actual);
            }
        }
        return result;
    }

    public int getProgress() {
        int progress = 0;
        if (!hasZeroEstimate()) {
            progress = Math.abs((int) (100 * actual / projection));
        }
        return progress;
    }
}