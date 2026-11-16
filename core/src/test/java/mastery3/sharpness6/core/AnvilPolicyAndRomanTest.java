package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AnvilPolicyAndRomanTest {

    @Test
    void adjustsAnvilCostWhenEnabledAndAboveCap() {
        SharpnessConfig config = new SharpnessConfig();
        config.bypassTooExpensive = true;
        config.anvilMaxCost = 39;

        assertEquals(15, AnvilPolicy.adjustAnvilCost(15, 1, config));
        assertEquals(39, AnvilPolicy.adjustAnvilCost(52, 1, config));
        // No stack-size restriction: stacked inputs are handled by vanilla validation.
        assertEquals(39, AnvilPolicy.adjustAnvilCost(40, 2, config));

        config.anvilMaxCost = 0;
        assertEquals(75, AnvilPolicy.adjustAnvilCost(75, 1, config));
        assertTrue(AnvilPolicy.shouldBypassExpensiveCheck(1, config));
        assertTrue(AnvilPolicy.shouldBypassExpensiveCheck(2, config));

        // Disabled: vanilla behavior (cost unchanged).
        config.bypassTooExpensive = false;
        assertEquals(75, AnvilPolicy.adjustAnvilCost(75, 1, config));
        assertFalse(AnvilPolicy.shouldBypassExpensiveCheck(1, config));
    }

    @Test
    void formatsRomanNumeralsThrough255Accurately() {
        assertEquals("VI", RomanNumerals.toRoman(6));
        assertEquals("X", RomanNumerals.toRoman(10));
        assertEquals("XI", RomanNumerals.toRoman(11));
        assertEquals("XL", RomanNumerals.toRoman(40));
        assertEquals("XCIV", RomanNumerals.toRoman(94));
        assertEquals("C", RomanNumerals.toRoman(100));
        assertEquals("CCLV", RomanNumerals.toRoman(255));
    }
}
