import java.net.*;
import java.util.Scanner;

class ClientDemo {
    public static void main(String args[]) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte data[] = null;

        while (true) {
            String input = new Scanner(System.in).next();
            data = input.getBytes();
            DatagramPacket dp = new DatagramPacket(data, data.length, ip, 1234);
            ds.send(dp);
        }
    }
}