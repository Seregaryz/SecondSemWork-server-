package packet;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PicturePacket extends OPacket {

    private double x, y;
    private short id = 2;

    public PicturePacket(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public short getId() {
        return id;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeShort(id);
        dos.writeDouble(x);
        dos.writeDouble(y);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
    }

}
