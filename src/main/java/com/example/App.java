
package com.example;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {

    public static String message() {
        return "Hello from Java 21 CI/CD!";
    }

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt(
            System.getProperty("server.port", "8081")
        );

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", port), 0
        );

        server.createContext("/", exchange -> {

            byte[] response = message().getBytes(
                StandardCharsets.UTF_8
            );

            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=utf-8"
            );

            exchange.sendResponseHeaders(200, response.length);

            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });

        server.start();

        System.out.println(
            "Java 21 application started on port " + port
        );
    }
}
