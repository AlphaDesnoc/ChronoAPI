package com.chrono.chronoapi.utils.gradient;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegExpUtils
{
    public static Iterable<MatchResult> allMatches(final Pattern pattern, final CharSequence input)
    {
        final Matcher matcher = pattern.matcher(input);
        return () -> matcher.results().iterator();
    }
}
