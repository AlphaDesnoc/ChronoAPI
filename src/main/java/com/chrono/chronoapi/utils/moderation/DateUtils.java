package com.chrono.chronoapi.utils.moderation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public final class DateUtils
{
    public static String getStartDate()
    {
        final LocalDateTime now = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return now.format(formatter);
    }

    public static String getEndDate(final String duration)
    {
        LocalDateTime now = LocalDateTime.now();

        final Pattern pattern = Pattern.compile("(\\d+)([wdhms])");
        final Matcher matcher = pattern.matcher(duration);

        while (matcher.find()) {
            final int value = Integer.parseInt(matcher.group(1));
            final String type = matcher.group(2);

            switch (type) {
                case "w" -> now = now.plusWeeks(value);
                case "d" -> now = now.plusDays(value);
                case "h" -> now = now.plusHours(value);
                case "m" -> now = now.plusMinutes(value);
                case "s" -> now = now.plusSeconds(value);
            }
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return now.format(formatter);
    }
}
