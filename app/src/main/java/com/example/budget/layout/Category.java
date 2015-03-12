package com.example.budget.layout;

import java.text.NumberFormat;

public class Category {
    public static final String NEW_CATEGORY = "*New Category*";
    public static int TRUE = 0;
    public static int FALSE = 1;
    private long id;
    private String name;
    private double amount;
    private boolean isActive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getIsActiveAsInt() {
        return isActive ? TRUE : FALSE;
    }

    public void setIsActiveFromInt(int isActive) {
        if (isActive == TRUE) {
            this.isActive = true;
        } else {
            this.isActive = false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public static Category getNewCategory() {
        Category newCategory = new Category();
        newCategory.setName(NEW_CATEGORY);
        newCategory.setActive(false);
        newCategory.setAmount(0.0);
        return newCategory;
    }

    public CharSequence getAmountAsString() {
        return NumberFormat.getCurrencyInstance().format(amount);
    }
}