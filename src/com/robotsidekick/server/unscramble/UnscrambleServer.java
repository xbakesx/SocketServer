/**
 * 
 */
package com.robotsidekick.server.unscramble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.robotsidekick.server.SocketServer;

/**
 * @author baker
 *
 */
public class UnscrambleServer extends SocketServer
{
    public static void main(final String[] args)
    {
        final UnscrambleServer server = new UnscrambleServer();

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

    private static final int DEFAULT_PORT = 54321;

    public UnscrambleServer()
    {
        super(DEFAULT_PORT);
        ready = false;
        words = new HashMap<String, List<String>>();
        this.startup();
        this.setListener(new UnscrambleListener(this));
        this.init();
    }

    private boolean ready;
    private final Map<String, List<String>> words;

    public final boolean ready()
    {
        return ready;
    }

    public final Word unscramble(final String letters)
    {
        if (!ready())
        {
            return null;
        }
        final List<String> results = getWords(letters);

        System.out.println("Unscrambled: " + letters + " to " + toString(results));

        if (results != null)
        {
            return new Word(results);
        }
        else
        {
            return null;
        }
    }

    protected final List<String> getWords(final String letters)
    {
        return words.get(alphabetize(letters));
    }

    private void init()
    {
        System.out.println("Loading...");
        try
        {
            final FileInputStream fis = new FileInputStream(new File("lib/dictionary.txt"));
            final InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8"));
            final BufferedReader buf = new BufferedReader(in);

            while (buf.ready())
            {
                final String word = buf.readLine().trim();
                final String letters = alphabetize(word);
                if (words.containsKey(letters))
                {
                    words.get(letters).add(word);
                }
                else
                {
                    final List<String> list = new ArrayList<String>();
                    list.add(word);
                    words.put(letters, list);
                }
            }
            System.out.println("Ready: " + words.keySet().size() + " distinct letter combinations can be solved");
            ready = true;
        }
        catch (final FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String alphabetize(final String letters)
    {
        final char[] lettersArray = letters.trim().toUpperCase().toCharArray();
        Arrays.sort(lettersArray);
        return new String(lettersArray);
    }

    private String toString(final List<String> list)
    {
        final StringBuilder output = new StringBuilder();
        if (list == null)
        {
            output.append("-null-");
        }
        else
        {
            for (final String word : list)
            {
                output.append(word).append(", ");
            }
        }
        return output.toString();
    }

}
