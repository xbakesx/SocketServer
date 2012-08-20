/**
 * 
 */
package com.robotsidekick.example;

import java.io.IOException;
import java.net.Socket;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.SocketServerConnectionListener;

/**
 * @author alex
 *
 */
public final class TestServer
{

    public static void main(final String[] args)
    {
        final SocketServer server = new SocketServer(4444);
        server.startup();
        server.setListener(new SocketServerConnectionListener()
        {
            @Override
            public void connected(final Socket socket)
            {
                System.out.println(SocketServer.readString(socket));
            }
        });

        try
        {
            server.listen();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

}
