package mastery3.sharpness6.core;

/**
 * Policy calculations for anvil repair cost uncapping across all supported Minecraft versions.
 *
 * Semantics:
 * - {@code bypassTooExpensive} is the master switch. When false, vanilla behavior applies
 *   (operations costing 40+ levels are blocked with "Too Expensive!").
 * - {@code anvilMaxCost} is only consulted when {@code bypassTooExpensive} is true. A value
 *   in [1, 39] clamps the displayed and charged cost to that value. A value of 0 means no
 *   clamp: the operation is allowed but the client may still display "Too Expensive!"
 *   (cosmetic, since the result is available). Values >= 40 are treated as "no clamp".
 * - Stacked inputs are handled by vanilla's own item-count validation; this policy does not
 *   impose an additional stack-size restriction.
 */
public final class AnvilPolicy {

    /** Vanilla survival anvil "Too Expensive!" cutoff level. */
    public static final int VANILLA_ANVIL_CAP = 40;

    private AnvilPolicy() {
    }

    /**
     * Adjusts a computed anvil level cost when {@code bypassTooExpensive} is enabled and
     * {@code anvilMaxCost} is in [1, 39], clamping the cost so unmodified vanilla and
     * Bedrock/Geyser clients can claim the result without desync.
     */
    public static int adjustAnvilCost(int computedCost, int inputStackCount, SharpnessConfig config) {
        if (config == null || !config.bypassTooExpensive) {
            return computedCost;
        }
        if (computedCost < VANILLA_ANVIL_CAP) {
            return computedCost;
        }
        if (config.anvilMaxCost > 0 && config.anvilMaxCost < VANILLA_ANVIL_CAP) {
            return config.anvilMaxCost;
        }
        return computedCost;
    }

    /**
     * Returns {@code true} when the vanilla {@code >= 40} anvil failure check should be bypassed.
     */
    public static boolean shouldBypassExpensiveCheck(int inputStackCount, SharpnessConfig config) {
        return config != null && config.bypassTooExpensive;
    }
}
