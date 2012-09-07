/**
 * 
 */
package com.robotsidekick.example.datastructures;

import java.io.Serializable;

/**
 * @author baker
 *
 */
public class Person implements Serializable
{

    private static final long serialVersionUID = 5366440346917889006L;

    public Person(final String iFirstName, final String iLastName)
    {
        firstName = iFirstName;
        lastName = iLastName;
    }

    private final String firstName;
    private final String lastName;

    /**
     * @return the first Name
     */
    public String getFirstName()
    {
        return firstName;
    }
    /**
     * @return the last Name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return getFirstName() + " " + getLastName();
    }

}
