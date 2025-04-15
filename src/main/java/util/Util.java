package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Util {
    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void downloadImage(String imageUrl, String path) throws IOException {
        URL url = new URL(imageUrl);

        //InputStream in = url.openStream();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
        connection.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8");

        connection.setRequestProperty("Referer", "https://weebcentral.com/");

        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(path);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();
        }


//        Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
//        in.close();
    }

    public static void createFolder(String folderName, String path) throws IOException {
        String folderPath = path + "/" + folderName;
        Files.createDirectories(Paths.get(folderPath));
    }

    public static boolean checkFolder(String path){
        return Files.exists(Paths.get(path));
    }
}
