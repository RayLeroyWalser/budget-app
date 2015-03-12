package com.example.budget.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.example.budget.view.AddSingleEntryView;

public class AddSingleEntryViewTest extends ActivityInstrumentationTestCase2<AddSingleEntryView> {

    private AddSingleEntryView addSingleEntryView;

    Context context;

    public AddSingleEntryViewTest() {
        super(AddSingleEntryView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        addSingleEntryView = getActivity();
    }
    
    public void testGetEntryFromIntent_noEntryInIntent(){
        assertNull(addSingleEntryView.getEntry());
    }
    
    public void testCategorySpinner_hasFourEntries(){
        assertEquals(5, addSingleEntryView.getCategories().size());
    }

}
