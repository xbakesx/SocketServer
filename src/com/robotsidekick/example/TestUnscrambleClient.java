/**
 * 
 */
package com.robotsidekick.example;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.unscramble.Word;

/**
 * @author alex
 *
 */
public final class TestUnscrambleClient
{

    public static void main(final String[] args)
    {
        try
        {
            final Socket socket = new Socket("localhost", 54321);

            SocketServer.writeString("racebomn\n", socket);

            //            socket.getOutputStream().close();

            final Word result = SocketServer.readObject(socket, Word.class);
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
