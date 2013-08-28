
import java.io.*;

public class IntroResponder implements ResponderInterface {
    @Override
    public Response respond(Request request) {
        InputStream stream = this.getClass().getResourceAsStream("introduction.html");
        String stringResult = this.convertStreamToString(stream);

        Response response = new Response();
        response.inputStream = new ByteArrayInputStream(stringResult.getBytes());
        response = updateSuccessfulResponse(response);

        return response;
    }

    public Response updateSuccessfulResponse(Response response) {
        response.method = "GET";
        response.path = "/move";
        response.statusCode = "200";
        response.statusText = "OK";
        response.httpType = "HTTP/1.1";
        response.contentType = "text/html";
        return response;
    }

    public String convertStreamToString(InputStream stream) {
        int bufferSize = 4000;
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer content = new StringBuffer();
        char[] buffer = new char[bufferSize];
        int n;
        try {
            while ((n = reader.read(buffer)) != -1) {
                content.append(buffer, 0, n);
            }
            return content.toString();
        } catch (IOException ioe) {
            return null;
        }
    }
}
