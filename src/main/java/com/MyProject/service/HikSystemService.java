package com.MyProject.service;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.InetSocketAddress;
    import java.net.Socket;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

@Service
public class HikSystemService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${hik.system.ip}")
    private String serverIp;

    @Value("${hik.system.port}")
    private int serverPort;

    @Value("${hik.system.timeout}")
    private int timeout;

   public String SendCommand(String command){
        logger.info("Sending TCP command to Hik-System: " + command);

        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(serverIp, serverPort), timeout);
            socket.setSoTimeout(timeout);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(command);
            String response = in.readLine();

            if (response == null) {
                logger.warn("No response received from Hik-System");
                return "Error: No response received from Hik Simulator";
            }

            logger.info("Received response from Hik-System: " + response);
            return response;

        } catch (IOException e) {
            logger.error("Error communicating with Hik-System", e);
            return "Error: " + e.getMessage();
        }
   }
}
