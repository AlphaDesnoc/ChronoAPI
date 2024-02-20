package com.chrono.chronoapi.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DataIsland
{

    private List<UUID> members;
    private BigDecimal money;

    public DataIsland(UUID owner)
    {
        members = new ArrayList<>();
        money = BigDecimal.valueOf(1000);
    }

    public List<UUID> getMembers() {
        return members;
    }

    public BigDecimal getMoney() {
        return money;
    }

}
