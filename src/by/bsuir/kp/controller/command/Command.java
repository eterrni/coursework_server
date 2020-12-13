package by.bsuir.kp.controller.command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Command {
    void execute(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException;
}
