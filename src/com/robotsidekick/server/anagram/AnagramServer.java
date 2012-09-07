/**
 * 
 */
package com.robotsidekick.server.anagram;

import java.io.IOException;

import com.robotsidekick.server.unscramble.UnscrambleServer;

/**
 * @author baker
 *
 */
public final class AnagramServer extends UnscrambleServer
{
    public static void main(final String[] args)
    {
        final AnagramServer server = new AnagramServer();

        try
        {
            while (true)
            {
                server.listen();
            }
        }
        catch (final IOException ex)
        {
            System.out.println("Unknown error.  Server stopped.");
            ex.printStackTrace();
        }
    }

    public AnagramServer()
    {
        super();

        setListener(new AnagramListener(this));
    }


    public AnagramList findAnagrams(final int length, final String letters)
    {
        if (!ready())
        {
            return null;
        }

        System.out.println("Finding Anagrams of " + letters + " that are " + length + " letters long.");

        final AnagramList results = new AnagramList();

        permute(length, "", letters, results);

        if (results.size() == 0)
        {
            return null;
        }
        else
        {
            return results;
        }
    }

    private void permute(final int length, final String prefix, final String letters, final AnagramList results)
    {
        final int m = prefix.length();
        if (m > 5)
        {
            return;
        }
        if (m == length)
        {
            results.addAll(getWords(prefix));
        }
        for (int i = 0, n = letters.length(); i < n; ++i)
        {
            permute(length, prefix + letters.charAt(i), letters.substring(0, i) + letters.substring(i + 1, n), results);
        }
    }

}
