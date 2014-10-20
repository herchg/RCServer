/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author hermeschang
 */
public class FileUtil {
    
    /**
     * Read the content of an input stream and concatenate it to a String
     * @param in
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String readStreamAsString(InputStream in, Charset charSet) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Read the content of an input stream as a byte array
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] readStreamAsBytes(InputStream in) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16 * 1024];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }
}
