package l2m.decoder.inmarsat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("output.wav");

        DataOutputStream dos = new DataOutputStream(fos);
        File file = new File("test.bit");
        byte[] input = Files.readAllBytes(file.toPath());

        int outSize = input.length / 12 * 320;
        byte[] result = new byte[outSize];
        Inmarsat inmarsat = new Inmarsat();
        inmarsat.decode(input, input.length, result);
        byte[] bytes = PCMHelper.PCM2Wave(result, 1, 8000, 16);
        dos.write(bytes);
        dos.close();

        System.out.println("converted");
    }
}
