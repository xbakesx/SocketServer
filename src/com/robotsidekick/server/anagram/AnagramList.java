/**
 * 
 */
package com.robotsidekick.server.anagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.robotsidekick.server.unscramble.Word;

/**
 * @author baker
 *
 */
public final class AnagramList extends ArrayList<Word> implements Serializable
{

    private static final long serialVersionUID = 7121270346383737271L;

    public void addAll(final List<String> words)
    {
        add(new Word(words));
    }

    @Override
    public String toString()
    {
        final StringBuilder buf = new StringBuilder();
        String sep = "";
        for (final Word word : this)
        {
            for (final String letters : word)
            {
                buf.append(sep).append(letters);
                sep = ",";
            }
        }

        return buf.toString();
    }

}
