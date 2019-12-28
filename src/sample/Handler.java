package sample;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Handler extends Thread  {

    private ServerSocket server;
    public static CopyOnWriteArrayList<Room> rooms = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        final int PORT = 1234;
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket client = null;
            try {
                client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean isCreator = false;
            try {
                isCreator = dis.readBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(isCreator);
            String nickname = null;
            try {
                nickname = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Connection connection = new Connection(client, nickname);
            connection.start();
            System.out.println("connected");
            if (isCreator) {
                System.out.println(nickname + " is creator");
                Room room = new Room(connection);
                connection.setRoom(room);
                room.clients.add(connection);
                rooms.add(room);
                System.out.println("new room");
            } else {
                String roomCreator = null;
                try {
                    roomCreator = dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Room room : rooms) {
                    if (roomCreator.equals(room.getCreator().getNickname())) {
                        connection.setRoom(room);
                        room.clients.add(connection);
                        System.out.println(nickname + " connect to " + roomCreator + " room");
                    }
                }
            }
        }
    }
}
