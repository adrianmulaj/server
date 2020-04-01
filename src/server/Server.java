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
public class Server {

    private DataOutputStream outVersoClient;
    private BufferedReader inDalClient;
    private BufferedReader tastiera;
    private Socket connection;
    private ServerSocket server;
    private String invio = "";
    private final int totalClients = 100;
    private final int port = 6789;
    InputStream inputStream;
    DataInputStream dataInputStream;

    public void startRunning() {
        try {
            server = new ServerSocket(port, totalClients);
            while (true) {
                try {
                    System.out.println("SERVER");
                    tastiera = new BufferedReader(new InputStreamReader(System.in)); //Oggetto per l'input da tastiera
                    System.out.println("In attesa di connessione");
                    connection = server.accept();
                    System.out.println("Connessione avvenuta con successo; collegati con: " + connection.getInetAddress().getHostName());
                    outVersoClient = new DataOutputStream(connection.getOutputStream()); //Stream di output
                    outVersoClient.flush();
                    inDalClient = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Stream di input

                    inputStream = connection.getInputStream();
                    // create a DataInputStream so we can read data from it.
                    dataInputStream = new DataInputStream(inputStream);



                    do {
                        System.out.println("Scrivi 'leggi' per leggere il mess del server 'send' per inviare o 'EXIT' per chiudere la connessione!!!");
                        invio = tastiera.readLine(); //Acquisisco la stringa grazie all'oggetto tastiera
                        if (invio.equals("leggi")) {
                            System.out.println("Ora verifico i messaggi inviati dal Server");
                            whileChatting();
                        } else {
                            if (invio.equals("send")) {
                                System.out.println("Scrivi il messaggio.");
                                invio = tastiera.readLine(); //Acquisisco la stringa grazie all'oggetto tastiera
                                sendMessage(invio);
                            }
                        }
                    } while (!invio.equals("EXIT"));
                    outVersoClient.flush();
                    connection.close();
                } catch (EOFException eofException) {
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void whileChatting() throws IOException {
        String message = "";
        message = dataInputStream.readUTF();
        System.out.println("The message sent from the socket was: " + message);
    }

    private void sendMessage(String invio) {
        try {
            outVersoClient.writeBytes("Server - " + invio);
            System.out.println("\nServer:" + invio);
            outVersoClient.flush();
        } catch (IOException ioException) {
            System.err.println("\nImpossibile inviare il messaggio!");
        }
    }
}
