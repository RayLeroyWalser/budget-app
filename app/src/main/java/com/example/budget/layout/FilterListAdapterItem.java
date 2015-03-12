package com.example.budget.layout;

public class FilterListAdapterItem {
    private Category category;

    private boolean useFilter;

    public String toString() {
        return category.getName();
    }

    public void toggle() {
        if (useFilter) {
            useFilter = false;
        } else {
            useFilter = true;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }
}
