/**
 * 
 */
package com.robotsidekick.example;

import com.robotsidekick.example.datastructures.Car;
import com.robotsidekick.example.datastructures.Person;
import com.robotsidekick.server.AbstractSocketServer;

/**
 * @author baker
 *
 */
public final class TestServer extends AbstractSocketServer<Person, Car>
{

    public TestServer()
    {
        super(12345);
    }

    public static void main(final String[] args)
    {
        new TestServer().start();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup()
    {
        System.out.println("Startup");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onShutdown()
    {
        System.out.println("Shutdown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car run(final Person input)
    {
        return new Car("Chevy", "Aveo");
    }

}
