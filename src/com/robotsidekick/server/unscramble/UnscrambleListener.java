/**
 * 
 */
package com.robotsidekick.server.unscramble;

import java.io.IOException;
import java.net.Socket;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.SocketServerConnectionListener;

/**
 * @author baker
 *
 */
public final class UnscrambleListener implements SocketServerConnectionListener
{

    public UnscrambleListener(final UnscrambleServer iServer)
    {
        server = iServer;
    }

    private final UnscrambleServer server;

    /**
     * {@inheritDoc}
     */
    @Override
    public void connected(final Socket socket)
    {
        final String letters = SocketServer.readLine(socket);
        if (letters != null)
        {
            new UnscrambleThread(letters, socket).run();
        }
        else
        {
            try
            {
                System.out.println("closing socket");
                socket.close();
            }
            catch (final IOException e)
            {
                // ignore
            }
        }
    }

    private class UnscrambleThread extends Thread
    {

        private UnscrambleThread(final String iLetters, final Socket iSocket)
        {
            letters = iLetters;
            socket = iSocket;
        }

        private final String letters;
        private final Socket socket;

        @Override
        public void run()
        {
            super.run();

            while (!server.ready())
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

            if (server.ready())
            {
                final Word word = server.unscramble(letters);
                if (word != null)
                {
                    System.out.println(letters + " -> " + word.toString());
                    SocketServer.writeObject(word, socket);
                }
                else
                {
                    System.out.println("Failed to unscramble " + letters);
                    SocketServer.writeObject(Word.NONE, socket);
                }
            }

            try
            {
                System.out.println("closing socket");
                socket.close();
            }
            catch (final IOException e)
            {
                // ignore
            }
        }

    }

}
