/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;

/**
 *
 * @author Bayebane Michele
 */
public class MainServer {

    public static void main(String[] args) {

        Server myServer = new Server();
        myServer.startRunning();
    }
}
