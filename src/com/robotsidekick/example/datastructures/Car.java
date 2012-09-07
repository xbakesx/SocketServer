/**
 * 
 */
package com.robotsidekick.example.datastructures;

import java.io.Serializable;

/**
 * @author alex
 *
 */
public class Car implements Serializable
{
    private static final long serialVersionUID = 8013746177274446438L;

    public Car(final String iMake, final String iModel)
    {
        make = iMake;
        model = iModel;
    }

    private final String make;
    private final String model;

    /**
     * @return the make
     */
    public String getMake()
    {
        return make;
    }
    /**
     * @return the model
     */
    public String getModel()
    {
        return model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return getMake() + " " + getModel();
    }


}
