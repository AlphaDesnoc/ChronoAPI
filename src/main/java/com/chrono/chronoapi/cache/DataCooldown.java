package com.chrono.chronoapi.cache;

public final class DataCooldown
{
    private final long cooldown;
    private final int messages;

    public DataCooldown(final long cooldown, final int messages) {
        this.cooldown = cooldown;
        this.messages = messages;
    }

    public long getCooldown() {
        return cooldown;
    }

    public int getMessages() {
        return messages;
    }
}
