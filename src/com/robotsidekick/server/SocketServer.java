/**
 * 
 */
package com.robotsidekick.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author baker
 *
 */
public class SocketServer
{

    private final int port;
    private SocketServerConnectionListener listener;

    private ServerSocket server;

    public SocketServer(final int iPort)
    {
        port = iPort;
        listener = null;

        shutdown();
    }

    public final void setListener(final SocketServerConnectionListener iListener)
    {
        listener = iListener;
    }

    public final void startup()
    {
        try
        {
            server = new ServerSocket(port);
        }
        catch (final IOException e)
        {
            // Could not listen on port
            // TODO: log error

            e.printStackTrace();
            shutdown();
        }
    }

    public final void listen() throws IOException
    {
        while (true)
        {
            Socket socket;
            try
            {
                socket = server.accept();
                if (listener != null)
                {
                    listener.connected(socket);
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
            catch (final IOException e)
            {
                // Failed to listen...?
                // TODO: log error

                e.printStackTrace();
                shutdown();

                throw e;
            }
        }
    }

    public final void shutdown()
    {
        if (server != null)
        {
            try
            {
                server.close();
            }
            catch (final IOException e)
            {
                // ignore
            }
        }
        server = null;
    }

    public static void writeString(final String data, final Socket socket)
    {
        try
        {
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.write(data);
        }
        catch (final IOException e)
        {
            // Failed to write data (on connecting to output stream?)
            // TODO: log error
            e.printStackTrace();
        }
    }

    public static void writeObject(final Serializable object, final Socket socket)
    {
        try
        {
            final BufferedOutputStream buf = new BufferedOutputStream(socket.getOutputStream());
            final ObjectOutputStream out = new ObjectOutputStream(buf);
            out.writeObject(object);
        }
        catch (final IOException e)
        {
            // Failed to write data (on connecting to output stream?)
            // TODO: log error
        }
    }

    public static String readLine(final Socket socket)
    {
        String ret;
        try
        {
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ret = in.readLine();
        }
        catch (final IOException e)
        {
            ret = "";
            // Failed to write data (on connecting to output stream?)
            // TODO: log error
            e.printStackTrace();
        }

        return ret;
    }

    public static String readString(final Socket socket)
    {
        final StringBuilder ret = new StringBuilder();
        try
        {
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            final int bufferSize = 256;
            final char buf[] = new char[bufferSize];
            while (in.ready())
            {
                in.read(buf, 0, bufferSize);
                ret.append(buf);
            }
        }
        catch (final IOException e)
        {
            // Failed to read data (on connecting to output stream?)
            // TODO: log error
            e.printStackTrace();
        }

        return ret.toString();
    }

    public static <T extends Serializable> T readObject(final Socket socket, final Class<T> clazz)
    {
        final Object ret = readObject(socket);

        if (ret != null)
        {
            try
            {
                return clazz.cast(ret);
            }
            catch (final ClassCastException e)
            {
                // Failed to read expected object
                // TODO: log error
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Object readObject(final Socket socket)
    {
        Object ret = null;
        try
        {
            final BufferedInputStream buf = new BufferedInputStream(socket.getInputStream());
            final ObjectInputStream in = new ObjectInputStream(buf);
            ret = in.readObject();
        }
        catch (final IOException e)
        {
            // Failed to read data (on connecting to output stream?)
            // TODO: log error
            e.printStackTrace();
        }
        catch (final ClassNotFoundException e)
        {
            // Failed to read known object
            // TODO: log error
            e.printStackTrace();
        }
        return ret;
    }

}
