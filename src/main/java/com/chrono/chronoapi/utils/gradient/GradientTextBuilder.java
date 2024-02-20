package com.chrono.chronoapi.utils.gradient;

import java.util.ArrayList;
import java.util.List;

public final class GradientTextBuilder
{
    private final List<AdvancedColor> colors = new ArrayList<>();
    private String text;
    private double blur;

    public GradientTextBuilder text(final String text)
    {
        this.text = text;
        return this;
    }

    public GradientTextBuilder blur(final double x)
    {
        this.blur = x;
        return this;
    }

    public GradientTextBuilder addColor(final String hex)
    {
        this.colors.add(new AdvancedColor(hex));
        return this;
    }

    public GradientText build()
    {
        return new GradientText(this.text, this.colors, this.blur);
    }
}
