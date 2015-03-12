package com.example.budget.layout;

import java.text.NumberFormat;

public class SuggestionItem {
    private Category category;
    private double suggested;
    private boolean useNew;

    private int id;

    public SuggestionItem() {
        setSuggested(0.0);
        useNew = false;
    }

    public SuggestionItem(BudgetEntry source, double suggestion, boolean useSuggestion) {
        setCategory(source.getCategory());
        setSuggested(suggestion);
        useNew = useSuggestion;
        setId(source.getId());
    }

    public void toggle() {
        useNew = !useNew;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUseNew() {
        return useNew;
    }

    public void setUseNew(boolean useNew) {
        this.useNew = useNew;
    }

    public double getSuggested() {
        return suggested;
    }

    public void setSuggested(double suggested) {
        this.suggested = suggested;
    }

    public CharSequence getSuggestedAmountString() {
        return NumberFormat.getCurrencyInstance().format(suggested);
    }

}