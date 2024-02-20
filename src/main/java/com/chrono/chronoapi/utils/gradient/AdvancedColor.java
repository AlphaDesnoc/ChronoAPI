package com.chrono.chronoapi.utils.gradient;

import java.awt.*;

public final class AdvancedColor
{
    public final Color color;

    public AdvancedColor(final int r, final int g, final int b)
    {
        this.color = hex2Rgb(rgb2Hex(r, g, b));
    }

    public AdvancedColor(final String hex)
    {
        this.color = hex2Rgb(hex.replaceAll("#", ""));
    }

    public String getHex()
    {
        return rgb2Hex(color.getRed(), color.getGreen(), color.getBlue());
    }

    private static String rgb2Hex(final int r, final int g, final int b)
    {
        return String.format("%02x%02x%02x", r, g, b);
    }

    private static Color hex2Rgb(final String colorStr)
    {
        return new Color(Integer.valueOf(colorStr, 16));
    }
}
