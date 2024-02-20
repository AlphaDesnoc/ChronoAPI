package com.chrono.chronoapi.builders.gui;

import org.bukkit.event.Event;

import java.util.function.Consumer;

public final class GuiListener<T extends Event>
{

    private final Class<T> type;
    private final Consumer<T> consumer;

    public GuiListener(Class<T> type, Consumer<T> consumer)
    {
        this.type = type;
        this.consumer = consumer;
    }

    public void accept(T t) { consumer.accept(t); }

    public Class<T> getType() { return type; }

}