package com.chrono.chronoapi.utils.gradient;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class GradientText extends BaseTextComponent
{
    private final List<TextComponent> gradientComponents  = new ArrayList<>();
    private final String originalText;
    private final List<AdvancedColor> gradientColors;
    private final double value;

    public TextComponent renderComponent() {
        TextComponent m = new TextComponent();
        gradientComponents.forEach(m::addExtra);
        m.addExtra(super.renderComponent());
        return m;
    }

    public void generate() {
        final List<String> split = new ArrayList<>();

        for (final MatchResult match : RegExpUtils.allMatches(Pattern.compile("&\\w|."), this.originalText)) {
            split.add(match.group());
        }

        boolean bold = false, underline = false, strikethrough = false, italic = false;

        for (int i = 0; i < split.size(); ++i) {
            if (split.get(i).startsWith("&")) {
                char formatCode = split.get(i).charAt(1);
                bold = underline = strikethrough = italic = false;

                switch (formatCode) {
                    case 'l' -> bold = true;
                    case 'm' -> strikethrough = true;
                    case 'n' -> underline = true;
                    case 'o' -> italic = true;
                }
            } else {
                final float percent = (float) i / (float) split.size() * 100.0F;
                final int pr = Math.round(percent);
                final AdvancedColor ic = InterpolateColor(this.gradientColors, (double) pr / 100.0, this.value);
                final TextComponent comp = new TextComponent(split.get(i));
                comp.setBold(bold);
                comp.setUnderlined(underline);
                comp.setStrikethrough(strikethrough);
                comp.setItalic(italic);
                comp.setObfuscated(false);
                comp.setColor(ChatColor.of("#" + ic.getHex()));
                gradientComponents.add(comp);
            }
        }
    }

    public GradientText(final String text, final List<AdvancedColor> colors, final double X) {
        this.originalText = text;
        this.gradientColors = colors;
        this.value = X;
        if (!colors.isEmpty()) {
            this.generate();
        }
    }

    public static AdvancedColor InterpolateColor(final List<AdvancedColor> colors, final double x, final double c) {
        double r = 0.0, g = 0.0, b = 0.0;
        double total = 0.0;
        final double step = 1.0 / (double) (colors.size() - 1);
        double mu = 0.0;

        for (final AdvancedColor ignored : colors) {
            total += Math.exp(-(x - mu) * (x - mu) / (2.0 * c)) / Math.sqrt(6.283185307179586 * c);
            mu += step;
        }

        mu = 0.0;

        for (final AdvancedColor color : colors) {
            double percent = Math.exp(-(x - mu) * (x - mu) / (2.0 * c)) / Math.sqrt(6.283185307179586 * c);
            mu += step;
            r += (double) color.color.getRed() * percent / total;
            g += (double) color.color.getGreen() * percent / total;
            b += (double) color.color.getBlue() * percent / total;
        }

        return new AdvancedColor((int) r, (int) g, (int) b);
    }
}