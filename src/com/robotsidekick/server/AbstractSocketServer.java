/**
 * 
 */
package com.robotsidekick.server;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/**
 * @author baker
 *
 */
public abstract class AbstractSocketServer<INPUT extends Serializable, OUTPUT extends Serializable> extends SocketServer
{

    public AbstractSocketServer(final int port)
    {
        super(port);
    }

    private boolean ready = false;

    public void start()
    {
        try
        {
            startup();
            onStartup();
            ready = true;

            setListener(new AbstractSocketServerListener());

            while (true)
            {
                listen();
            }
        }
        catch (final IOException ex)
        {
            System.out.println("Unknown error.  Server stopped: " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (final Throwable t)
        {
            System.out.println("Unknown error.  Server stopped: " + t.getMessage());
            t.printStackTrace();
        }

        onShutdown();
        shutdown();
    }

    public OUTPUT servePurpose(final INPUT input)
    {
        if (!ready)
        {
            return null;
        }

        return run(input);
    }

    public abstract void onStartup();

    public abstract void onShutdown();

    public abstract OUTPUT run(final INPUT input);

    private class AbstractSocketServerListener implements SocketServerConnectionListener
    {
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public void connected(final Socket socket)
        {
            final Object input = SocketServer.readObject(socket);
            if (input != null)
            {
                try
                {
                    new AbstractSocketServerThread((INPUT) input, socket).run();
                }
                catch (final ClassCastException ex)
                {
                    System.out.println("Unsupported input " + input.getClass());
                }
            }
            else
            {
                try
                {
                    socket.close();
                }
                catch (final IOException e)
                {
                    // ignore
                }
            }
        }
    }

    private class AbstractSocketServerThread extends Thread
    {
        private AbstractSocketServerThread(final INPUT iInput, final Socket iSocket)
        {
            input = iInput;
            socket = iSocket;
        }

        private final INPUT input;
        private final Socket socket;

        @Override
        public void run()
        {
            super.run();

            while (!AbstractSocketServer.this.ready)
            {
                try
                {
                    System.out.println("Server not ready...");
                    Thread.sleep(500);
                }
                catch (final InterruptedException e)
                {
                    // TODO: Log message
                    break;
                }
            }

            if (AbstractSocketServer.this.ready)
            {
                final OUTPUT output = AbstractSocketServer.this.servePurpose(input);
                if (output != null)
                {
                    System.out.println(input.toString() + " -> " + output.toString());
                    SocketServer.writeObject(output, socket);
                }
                else
                {
                    SocketServer.writeObject(null, socket);
                }
            }

            try
            {
                socket.close();
            }
            catch (final IOException e)
            {
                // ignore
            }
        }
    }
}
