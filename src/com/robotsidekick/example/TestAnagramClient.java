/**
 * 
 */
package com.robotsidekick.example;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.anagram.AnagramList;

/**
 * @author alex
 *
 */
public final class TestAnagramClient
{

    public static void main(final String[] args)
    {
        try
        {
            final Socket socket = new Socket("localhost", 54321);

            //            SocketServer.writeString("5eemrrt\n", socket);
            //            SocketServer.writeString("5deelmmnrrstz\n", socket);
            //            SocketServer.writeString("5beeehijmrrtw\n", socket);
            SocketServer.writeString("5cdskmru\n", socket);

            //            socket.getOutputStream().close();

            final AnagramList result = SocketServer.readObject(socket, AnagramList.class);
            if (result != null)
            {
                System.out.println("CLIENT: " + result.toString());
            }
            else
            {
                System.out.println("CLIENT: sad");
            }
        }
        catch (final UnknownHostException e)
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

}
