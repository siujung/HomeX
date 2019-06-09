package file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class Download {
    public static void getFile(String urlPath, String fileName, String localPath) throws IOException {
        byte[] content;
        InputStream input;
        FileOutputStream output;
        URL url = new URL(urlPath);
        File path = new File(localPath);
        File file = new File(localPath + File.separator + fileName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (!path.exists())
            path.mkdirs();
        connection.setConnectTimeout(3600);
        input = connection.getInputStream();
        content = stream2byte(input);
        output = new FileOutputStream(file);
        output.write(content);
        output.flush();
        if (output != null)
            output.close();
        if (input != null)
            input.close();

        System.out.println(urlPath + " downloaded sucessfully to " + localPath);
    }

    public static byte[] stream2byte(InputStream input) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while ((length = input.read(buffer)) != -1) {
            output.write(buffer, 0, length);
        }
        if (output != null)
            output.close();

        return output.toByteArray();
    }

    public static String getBasePath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
    }
}
