package com.chrono.chronoapi.utils.handlers;

import com.google.common.reflect.ClassPath;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class CommandHandler
{
    public void register(final JavaPlugin plugin, final String path)
    {
        try {
            final ClassPath classPath = ClassPath.from(plugin.getClass().getClassLoader());

            classPath.getTopLevelClassesRecursive(path).forEach((classInfo -> {
                try {
                    final Class<?> c = Class.forName(classInfo.getName());
                    final Object obj = c.getDeclaredConstructor().newInstance();

                    if (obj instanceof Command command) {
                        final SimpleCommandMap commandMap = ((CraftServer) plugin.getServer()).getCommandMap();
                        commandMap.register(plugin.getDescription().getName(), command);
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
