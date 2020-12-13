package by.bsuir.kp.main;

public class Main {
    public static final int PORT_WORK = 8080;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();
    }
}
