package com.chrono.chronoapi.utils.handlers;

import com.google.common.reflect.ClassPath;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class EventHandler
{
    public void register(final JavaPlugin plugin, final String path)
    {
        final PluginManager pm = plugin.getServer().getPluginManager();

        try {
            final ClassPath classPath = ClassPath.from(plugin.getClass().getClassLoader());

            classPath.getTopLevelClassesRecursive(path).forEach((classInfo -> {
                try {
                    final Class<?> c = Class.forName(classInfo.getName());
                    final Object obj = c.getDeclaredConstructor().newInstance();

                    if (obj instanceof Listener listener) {
                        pm.registerEvents(listener, plugin);
                    }
                } catch (IllegalAccessException | ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
