package com.example.budget.view;

import android.app.Activity;

import com.example.budget.adapter.BudgetDbAdapter;

public abstract class AbstractBudgetView extends Activity {

    @Override
    public void onDestroy() {
        super.onDestroy();
        getProvider().close();
    }

    public abstract BudgetDbAdapter getProvider();
}
