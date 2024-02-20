package com.chrono.chronoapi.cache;

import org.bukkit.inventory.ItemStack;

public final class DataStaff
{
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private final ItemStack[] inventory;

    public DataStaff(final ItemStack helmet, final ItemStack chestplate, final ItemStack leggings, final ItemStack boots, final ItemStack[] inventory) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.inventory = inventory;
    }

    public ItemStack getHelmet()
    {
        return helmet;
    }

    public void setHelmet(final ItemStack helmet)
    {
        this.helmet = helmet;
    }

    public ItemStack getChestplate()
    {
        return chestplate;
    }

    public void setChestplate(final ItemStack chestplate)
    {
        this.chestplate = chestplate;
    }

    public ItemStack getLeggings()
    {
        return leggings;
    }

    public void setLeggings(final ItemStack leggings)
    {
        this.leggings = leggings;
    }

    public ItemStack getBoots()
    {
        return boots;
    }

    public void setBoots(final ItemStack boots)
    {
        this.boots = boots;
    }

    public ItemStack[] getInventory()
    {
        return inventory;
    }
}