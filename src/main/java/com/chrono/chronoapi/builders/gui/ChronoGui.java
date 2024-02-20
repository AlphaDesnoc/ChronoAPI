package com.chrono.chronoapi.builders.gui;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.builders.gui.content.GuiContents;
import com.chrono.chronoapi.builders.gui.content.GuiProvider;
import com.chrono.chronoapi.builders.gui.opener.GuiOpener;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public final class ChronoGui
{
    private static final ChronoAPI api = ChronoAPI.getAPI();
    private String id;
    private String title;
    private InventoryType type;
    private int rows, columns;
    private boolean closeable;
    private Player target;

    private GuiProvider provider;
    private ChronoGui parent;

    private List<GuiListener<? extends Event>> listeners;
    private final GuiManager manager;

    private ChronoGui(GuiManager manager) {
        this.manager = manager;
    }

    public Inventory open(Player player) { return open(player, 0); }
    public Inventory open(Player player, int page) {

        final Optional<ChronoGui> oldInv = this.manager.getInventory(player);

        oldInv.ifPresent(inv -> {
            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((GuiListener<InventoryCloseEvent>) listener)
                            .accept(new InventoryCloseEvent(player.getOpenInventory())));

            this.manager.setInventory(player, null);
        });

        GuiContents contents = new GuiContents.Impl(this, player.getUniqueId());
        contents.pagination().page(page);

        this.manager.setContents(player, contents);

        try {
            this.provider.init(player, contents);

            if (!this.manager.getContents(player).equals(Optional.of(contents))) {
                return null;
            }

            final GuiOpener opener = this.manager.findOpener(type)
                    .orElseThrow(() -> new IllegalStateException("No opener found for the inventory type " + type.name()));
            final Inventory handle = opener.open(this, player);

            this.manager.setInventory(player, this);

            return handle;
        } catch (Exception e) {
            this.manager.handleInventoryOpenError(this, player, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void close(Player player)
    {
        listeners.stream()
                .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                .forEach(listener -> ((GuiListener<InventoryCloseEvent>) listener)
                        .accept(new InventoryCloseEvent(player.getOpenInventory())));

        this.manager.setInventory(player, null);
        player.closeInventory();

        this.manager.setContents(player, null);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public InventoryType getType() { return type; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }

    public boolean isCloseable() { return closeable; }
    public void setCloseable(boolean closeable) { this.closeable = closeable; }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget()
    {
        return target;
    }

    public GuiProvider getProvider() {
        return provider;
    }

    public Optional<ChronoGui> getParent() { return Optional.ofNullable(parent); }

    public GuiManager getManager() {
        return manager;
    }

    List<GuiListener<? extends Event>> getListeners() { return listeners; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder
    {

        private String id = "unknown";
        private String title = "";
        private InventoryType type = InventoryType.CHEST;
        private int rows = 6, columns = 9;
        private boolean closeable = true;
        private Player target;
        private GuiManager manager;
        private GuiProvider provider;
        private ChronoGui parent;

        private final List<GuiListener<? extends Event>> listeners = new ArrayList<>();

        private Builder() {}

        public Builder id(String id)
        {
            this.id = id;
            return this;
        }

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type)
        {
            this.type = type;
            return this;
        }

        public Builder size(int rows, int columns)
        {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public Builder closeable(boolean closeable)
        {
            this.closeable = closeable;
            return this;
        }

        public Builder provider(GuiProvider provider)
        {
            this.provider = provider;
            return this;
        }

        public Builder parent(ChronoGui parent)
        {
            this.parent = parent;
            return this;
        }

        public Builder listener(GuiListener<? extends Event> listener)
        {
            this.listeners.add(listener);
            return this;
        }

        public Builder manager(GuiManager manager)
        {
            this.manager = manager;
            return this;
        }

        public ChronoGui build()
        {
            if(this.provider == null)
                throw new IllegalStateException("The provider of the ChronoGui.Builder must be set.");

            final GuiManager manager = this.manager != null ? this.manager : api.getGuiManager();

            if(manager == null)
                throw new IllegalStateException("The manager of the SmartInventory.Builder must be set, "
                        + "or the SmartInvs should be loaded as a plugin.");

            final ChronoGui inv = new ChronoGui(manager);
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.target = this.target;
            inv.provider = this.provider;
            inv.parent = this.parent;
            inv.listeners = this.listeners;

            return inv;
        }
    }

}