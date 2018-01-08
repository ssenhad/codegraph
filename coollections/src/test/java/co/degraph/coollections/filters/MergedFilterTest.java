package co.degraph.coollections.filters;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MergedFilterTest {

    @Test
    public void shouldMergeTwoFiltersAndReturnWhetherOneOfItMatcherAValue() {
        assertTrue(new MergedFilter<String>(value -> true, value -> false).matches("value"));
        assertTrue(new MergedFilter<String>(value -> false, value -> true).matches("value"));
        assertTrue(new MergedFilter<String>(value -> true, value -> true).matches("value"));
        assertFalse(new MergedFilter<String>(value -> false, value -> false).matches("value"));
    }

}
