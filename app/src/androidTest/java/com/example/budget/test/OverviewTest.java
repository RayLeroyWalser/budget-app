package com.example.budget.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budget.R;
import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.view.Overview;

public class OverviewTest extends ActivityInstrumentationTestCase2<Overview> {

    private Overview overview;
    private TextView title;
    private ListView list;
    private BudgetDbAdapter placeholder;

    Context context;

    public OverviewTest() {
        super(Overview.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        overview = getActivity();

        title = (TextView) overview.findViewById(R.id.overviewTitle);
        list = (ListView) overview.findViewById(R.id.byCategoryList);
    }
    
    public void testTitle(){
        assert(true);
    }
    
    public void testSomethingElse(){
        assert(true);
    }

}
