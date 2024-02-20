package com.chrono.chronoapi.cache;

import com.chrono.chronoapi.enums.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DataGuiChat
{
    private UUID target;
    private DataIsland island;
    private final Actions action;
    private int step;
    private final List<String> data;

    public DataGuiChat(UUID target, Actions action)
    {
        this.target = target;
        this.action = action;
        this.step = 0;
        this.data = new ArrayList<>();
    }

    public DataGuiChat(DataIsland island, Actions action)
    {
        this.island = island;
        this.action = action;
        this.step = 0;
        this.data = new ArrayList<>();
    }


    public UUID getTarget()
    {
        return target;
    }

    public DataIsland getIsland() {
        return island;
    }

    public Actions getAction()
    {
        return action;
    }

    public void nextStep()
    {
        this.step += 1;
    }

    public int getStep()
    {
        return step;
    }

    public void addData(final String data)
    {
        this.data.add(data);
    }

    public List<String> getData()
    {
        return data;
    }
}
