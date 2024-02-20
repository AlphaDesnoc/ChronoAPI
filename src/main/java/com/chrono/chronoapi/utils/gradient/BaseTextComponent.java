package com.chrono.chronoapi.utils.gradient;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTextComponent
{
    protected List<BaseTextComponent> components = new ArrayList<>();

    public TextComponent renderComponent()
    {
        final TextComponent txt = new TextComponent();
        components.forEach(c -> txt.addExtra(c.renderComponent()));
        return txt;
    }

    public String getRenderedText()
    {
        return renderComponent().toLegacyText();
    }
}
