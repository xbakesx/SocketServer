/**
 * 
 */
package com.robotsidekick.server.unscramble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author baker
 *
 */
public final class Word implements Iterable<String>, Serializable
{
    private static final long serialVersionUID = -69253859292705243L;
    public static Word NONE = new Word();

    private Word()
    {
        this((List<String>) null);
    }

    public Word(final String iWord)
    {
        this(iWord == null ? null : Collections.singletonList(iWord));
    }

    public Word(final List<String> iWords)
    {
        if (iWords != null)
        {
            words = Collections.unmodifiableList(iWords);
        }
        else
        {
            words = new ArrayList<String>();
        }
    }

    private final List<String> words;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<String> iterator()
    {
        return words.iterator();
    }

    public List<String> getWords()
    {
        return words;
    }

    @Override
    public String toString()
    {
        return words.isEmpty() ? "-No Words-" : (words.get(0) + " (" + words.size() + ")");
    }

}
