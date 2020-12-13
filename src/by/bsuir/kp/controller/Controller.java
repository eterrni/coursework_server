package by.bsuir.kp.controller;

import by.bsuir.kp.controller.command.Command;
import by.bsuir.kp.controller.command.CommandProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller implements Runnable {
    private Socket clientSocket = null;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private CommandProvider commandProvider = CommandProvider.getInstance();

    public Controller(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            String currentCommand;
            Command command;

            while (true) {

                currentCommand = inputStream.readObject().toString();
                command = commandProvider.getCommand(currentCommand);
                command.execute(inputStream, outputStream);

            }

        } catch (Exception e) {
            // TO DO
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TO DO
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TO DO
                    e.printStackTrace();
                }
            }
        }
    }
}
