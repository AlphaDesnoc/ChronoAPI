package com.chrono.chronoapi.builders.item;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ItemBuilder
{
    private final ItemStack itemStack;

    public ItemBuilder(final Material material)
    {
        this(material, 1);
    }

    public ItemBuilder(final ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public ItemBuilder(final Material material, final int amount)
    {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(final Material material, final int amount, final byte durability)
    {
        itemStack = new ItemStack(material, amount, durability);
    }

    public ItemBuilder clone()
    {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder setDurability(final short durability)
    {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setName(final String name)
    {
        final ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(final Enchantment enchantment, final int level)
    {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(final Enchantment enchantment)
    {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setSkullOwner(final CraftPlayer player)
    {
        try {
            final SkullMeta im = (SkullMeta) itemStack.getItemMeta();
            im.setOwnerProfile(player.getPlayerProfile());
            itemStack.setItemMeta(im);
        } catch(ClassCastException expected){}
        return this;
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level)
    {
        final ItemMeta im = itemStack.getItemMeta();
        im.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments)
    {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setInfinityDurability()
    {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLore(final String... lore)
    {
        final ItemMeta im = itemStack.getItemMeta();
        im.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore)
    {
        final ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(final String line)
    {
        final ItemMeta im = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>(im.getLore());
        if(!lore.contains(line))return this;
        lore.remove(line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(final int index)
    {
        final ItemMeta im = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>(im.getLore());
        if(index<0||index>lore.size())return this;
        lore.remove(index);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(final String line)
    {
        final ItemMeta im = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(im.hasLore())lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(final String line, final int pos)
    {
        final ItemMeta im = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(final Color color)
    {
        try{
            final LeatherArmorMeta im = (LeatherArmorMeta) itemStack.getItemMeta();
            im.setColor(color);
            itemStack.setItemMeta(im);
        } catch(ClassCastException expected){}
        return this;
    }

    // TODO: FAIRE DES EVENTS NOMINATIFS

    public ItemBuilder setRightClickListener(final JavaPlugin plugin, final RightClickListener rightClickListener) {
        if (rightClickListener != null) {
            plugin.getServer().getPluginManager().registerEvents(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerInteract(PlayerInteractEvent event) {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        final Player player = event.getPlayer();

                        if (player.getInventory().getItemInMainHand().isSimilar(itemStack)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    rightClickListener.onRightClick(player);
                                }
                            }.runTask(plugin);
                        }
                    }
                }
            }, plugin);
        }

        return this;
    }

    public ItemStack toItemStack()
    {
        return itemStack;
    }

    public interface RightClickListener {
        void onRightClick(Player player);
    }
}
