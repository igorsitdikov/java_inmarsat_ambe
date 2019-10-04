import java.io.*;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        File file = new File(currentDirectory+"/src/test.bit");
        byte[] ar = Files.readAllBytes(file.toPath());

        Inmarsat a = new Inmarsat();
        byte[] ress = new byte[ar.length/12*320];
        a.to_decode_all(ar, ar.length, ress);
        String[] cmd = {"ffmpeg", "-f", "s16le", "-ar", "8000", "-ac", "1", "-i", "pipe:0", "-f", "wav", "-ar", "8000", "-ac", "1", "pipe:1"};
        try (FileOutputStream foss = new FileOutputStream(currentDirectory+"/src/test.wav")) {
            foss.write(wav8kToWavNk(cmd,ress));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static byte[] wav8kToWavNk ( String[] cmd, byte[] input ) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.redirectInput();
        builder.redirectOutput();
        Process process = builder.start();
        OutputStream stdin = process.getOutputStream();
        byteArrayToOs(stdin, input);

        InputStream is = process.getInputStream();
        byte[] outputArray = isToByteAray(is);
        process.waitFor();
        return outputArray;
    }

    public static void byteArrayToOs ( OutputStream os, byte[] array ) {
        Thread t = new Thread(() -> {
            BufferedOutputStream writer = new BufferedOutputStream(os);
            try {
                writer.write(array);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public static byte[] isToByteAray ( InputStream is ) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = bis.read();
        while (c != -1) {
            baos.write(c);
            c = bis.read();
        }
        is.close();
        return baos.toByteArray();
    }
}
