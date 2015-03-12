package com.example.budget.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.example.budget.R;
import com.example.budget.view.ListHistoryView;

public class ListHistoryViewTest extends ActivityInstrumentationTestCase2<ListHistoryView> {

    private ListHistoryView listHistory;

    Context context;

    public ListHistoryViewTest() {
        super(ListHistoryView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        listHistory = getActivity();

    }
    
    public void testTitle(){
        assert(true);
    }
    
    public void testListView(){
        ListView list;
        list = (ListView) listHistory.findViewById(R.id.itemsListView);
        assert(true);
    }

}
