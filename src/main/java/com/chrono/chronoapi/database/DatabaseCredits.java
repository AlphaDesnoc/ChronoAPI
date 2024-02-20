package com.chrono.chronoapi.database;

public final class DatabaseCredits
{
    private final String hostname;
    private final String user;
    private final String password;
    private final String bdd;
    private final int port;

    public DatabaseCredits(final String hostname, final String user, final String password, final String bdd, final int port)
    {
        this.hostname = hostname;
        this.user = user;
        this.password = password;
        this.bdd = bdd;
        this.port = port;
    }

    public String getHostname()
    {
        return hostname;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }

    public String getBDD()
    {
        return bdd;
    }

    public int getPort()
    {
        return port;
    }
}
