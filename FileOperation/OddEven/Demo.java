import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("numbers.txt");
        FileWriter fileOdd = new FileWriter("odd.txt");
        FileWriter fileEven = new FileWriter("even.txt");

        int j;
        while ((j = fileReader.read()) != -1) {
            if ((char) j % 2 == 0) {
                fileEven.write((char) j + "\n");
            } else {
                fileOdd.write((char) j + "\n");
            }
        }

        fileEven.close();
        fileOdd.close();
        fileReader.close();
    }
}
