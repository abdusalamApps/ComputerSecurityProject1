//==========================================================================
//Sample tlsserver using sslsockets

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

public class TLSServer {
    private static final int PORT = 8043;

    public static void main(String[] args) throws Exception {
        SSLContext sslContext;
        KeyManagerFactory keyManagerFactory;
        KeyStore keyStore;

        char[] passphrase = "12345678".toCharArray();
        keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/serverKeyStore.jks"), passphrase);

        keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, passphrase);

        sslContext = SSLContext.getInstance("TLSv1.3");
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        sslContext.init(keyManagers, null, null);

        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        ServerSocket serverSocket = sslServerSocketFactory.createServerSocket(PORT);

        SSLSocket sslSocket = (SSLSocket) serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        String line;
        while (((line = bufferedReader.readLine()) != null)) {
            System.out.println(line);
        }
        bufferedReader.close();
        sslSocket.close();
    }
}
