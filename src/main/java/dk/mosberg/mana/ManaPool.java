package dk.mosberg.mana;

/**
 * Represents a single mana pool with current and maximum values.
 */
public class ManaPool {
    private final ManaPoolType type;
    private double current;
    private double max;

    public ManaPool(ManaPoolType type) {
        this.type = type;
        this.max = type.getMaxPool();
        this.current = this.max; // Start full
    }

    /**
     * Add mana to this pool.
     * 
     * @param amount Amount to add
     * @return Actual amount added (may be less than requested if pool is near full)
     */
    public double add(double amount) {
        double oldCurrent = current;
        current = Math.min(current + amount, max);
        return current - oldCurrent;
    }

    /**
     * Consume mana from this pool.
     * 
     * @param amount Amount to consume
     * @return true if sufficient mana was available and consumed, false otherwise
     */
    public boolean consume(double amount) {
        if (current >= amount) {
            current -= amount;
            return true;
        }
        return false;
    }

    /**
     * Check if this pool has at least the specified amount.
     */
    public boolean has(double amount) {
        return current >= amount;
    }

    /**
     * Regenerate mana based on the pool's regen rate.
     */
    public void regenerate() {
        add(type.getRegenRate());
    }

    /**
     * Set the current mana to a specific value.
     */
    public void set(double amount) {
        current = Math.max(0, Math.min(amount, max));
    }

    /**
     * Set the maximum mana capacity.
     */
    public void setMax(double max) {
        this.max = Math.max(0, max);
        // Clamp current to new max
        if (current > this.max) {
            current = this.max;
        }
    }

    // Getters
    public ManaPoolType getType() {
        return type;
    }

    public double getCurrent() {
        return current;
    }

    public double getMax() {
        return max;
    }

    public double getPercentage() {
        return max > 0 ? (current / max) * 100.0 : 0.0;
    }

    public boolean isFull() {
        return current >= max;
    }

    public boolean isEmpty() {
        return current <= 0;
    }
}
