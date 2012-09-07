/**
 * 
 */
package com.robotsidekick.server.anagram;

import java.io.IOException;
import java.net.Socket;

import com.robotsidekick.server.SocketServer;
import com.robotsidekick.server.SocketServerConnectionListener;
import com.robotsidekick.server.unscramble.Word;

/**
 * @author baker
 *
 */
public final class AnagramListener implements SocketServerConnectionListener
{

    public AnagramListener(final AnagramServer iServer)
    {
        server = iServer;
    }

    private final AnagramServer server;

    /**
     * {@inheritDoc}
     */
    @Override
    public void connected(final Socket socket)
    {
        String letters = SocketServer.readLine(socket);
        if (letters != null)
        {
            final int length = Integer.parseInt(letters.substring(0, 1));
            letters = letters.substring(1);
            new UnscrambleThread(length, letters, socket).run();
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

        private UnscrambleThread(final int iLength, final String iLetters, final Socket iSocket)
        {
            length = iLength;
            letters = iLetters;
            socket = iSocket;
        }

        private final int length;
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
                final AnagramList answer = server.findAnagrams(length, letters);
                if (answer != null)
                {
                    System.out.println(letters + " -> " + answer.toString());
                    SocketServer.writeObject(answer, socket);
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
