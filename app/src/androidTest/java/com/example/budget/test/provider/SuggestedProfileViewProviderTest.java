package com.example.budget.test.provider;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.budget.adapter.BudgetDbAdapter;
import com.example.budget.layout.SuggestionItem;
import com.example.budget.provider.SuggestedProfileViewProvider;

public class SuggestedProfileViewProviderTest extends AbstractDataProviderTest {

    private static HashMap<String, Double> expectedSuggestionAmounts;
    private SuggestedProfileViewProvider suggestedProfileViewProvider;
    private static int entryRepeatCount = 3;

    static {
        expectedSuggestionAmounts = new HashMap<String, Double>();
        expectedSuggestionAmounts.put("Paycheck", 2.3 * entryRepeatCount
                * SuggestedProfileViewProvider.SUGGESTION_INCREASE_FACTOR);
        expectedSuggestionAmounts.put("Dividend", 3.2 * entryRepeatCount
                * SuggestedProfileViewProvider.SUGGESTION_INCREASE_FACTOR);
        expectedSuggestionAmounts.put("Food", -2.2 * entryRepeatCount
                * SuggestedProfileViewProvider.SUGGESTION_INCREASE_FACTOR);
        expectedSuggestionAmounts.put("Gas", -2.2 * entryRepeatCount
                * SuggestedProfileViewProvider.SUGGESTION_INCREASE_FACTOR);
    }

    @Override
    public void setUp() {
        suggestedProfileViewProvider = new SuggestedProfileViewProvider(getContext());
        suggestedProfileViewProvider.open();

        suggestedProfileViewProvider.truncateTables();
        insertTestCategories();
        insertTestEntries(-130);
        for (int i = 0; i < entryRepeatCount; i++) {
            insertTestEntries(-40);
        }
    }

    public void testCreateProvider() {
        assertNotNull(suggestedProfileViewProvider);
    }

    public void testGetSuggestionList() {
        ArrayList<SuggestionItem> suggestionList = new ArrayList<SuggestionItem>();
        suggestionList.addAll(suggestedProfileViewProvider.getSuggestionList());
        assertEquals("should be four entries in the suggestion list", 4, suggestionList.size());
        for (SuggestionItem item : suggestionList) {
            assertEquals(expectedSuggestionAmounts.get(item.getCategory().getName()), item.getSuggested());
        }
    }

    @Override
    public BudgetDbAdapter getProvider() {
        return suggestedProfileViewProvider;
    }

}
