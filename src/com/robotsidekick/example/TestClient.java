/**
 * 
 */
package com.robotsidekick.example;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.unscramble.Word;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author alex
 *
 */
public final class TestClient
{

    public static void main(final String[] args)
    {
        try
        {
            final Socket socket = new Socket("localhost", 54321);

            SocketServer.writeString("clarinets\n", socket);

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
