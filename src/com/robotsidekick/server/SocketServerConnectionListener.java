/**
 * 
 */
package com.robotsidekick.server;

import java.net.Socket;

/**
 * @author baker
 *
 */
public interface SocketServerConnectionListener
{
    void connected(final Socket socket);
}
