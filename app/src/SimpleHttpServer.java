import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class SimpleHttpServer {
    private static final int DEFAULT_PORT_NUMBER = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT_NUMBER), 0);
        server.createContext("/", new MyHandler());
        server.start();
        System.out.println("Server started on port " + DEFAULT_PORT_NUMBER);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if("GET".equals(exchange.getRequestMethod())){
                if("/".equals(exchange.getRequestURI().toString())){
                    sendResponse(exchange, Response.MESSAGE);
                }
                else {
                    sendResponse(exchange, Response.NOT_FOUND);
                }
            } else {
                sendResponse(exchange, Response.METHOD_NOT_ALLOWED);
            }
        }

        private void sendResponse(HttpExchange exchange, Response response) throws IOException {
            int code = response.getStatusCode();
            String message = response.getMessage();
            exchange.sendResponseHeaders(code, message.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(message.getBytes());
                os.flush();
            }
        }
    }

    enum Response {
        MESSAGE(200, "Hello world!"),
        NOT_FOUND(404, "Not found"),
        METHOD_NOT_ALLOWED(405, "Method not allowed");

        private final int statusCode;
        private final String message;

        Response(int statusCode, String message){
            this.statusCode = statusCode;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}