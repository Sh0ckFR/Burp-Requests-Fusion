package burp;

import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

public class BurpExtender implements IBurpExtender, IHttpListener
{
    private String extensionName = "Burp Requests Fusion";
    private String localServerApiUrl = "http://127.0.0.1:8888/api/";
    private IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
    private PrintWriter stdout;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        
        callbacks.setExtensionName(extensionName);
        
        stdout = new PrintWriter(callbacks.getStdout(), true);
        
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse currentRequest)
    {
        try {
            if(messageIsRequest) {
                JSONObject json = readJsonFromUrl(localServerApiUrl);

                String request = new String(currentRequest.getRequest());
                IRequestInfo requestInfo = helpers.analyzeRequest(currentRequest);
                List<String> headers = requestInfo.getHeaders();
                
                json.keys().forEachRemaining(key -> {
                    boolean isFound = false;
                    Object value = json.get(key);
                    for(int i = 0; i < headers.size(); i ++) {
                        if (headers.get(i).startsWith(key)) {
                            headers.set(i, key + ": " + value);
                            isFound = true;
                        }
                    }
                    if (!isFound) { headers.add(key + ": " + value); }
                });

                String messageBody = request.substring(requestInfo.getBodyOffset());

                byte[] message = helpers.buildHttpMessage(headers, messageBody.getBytes());

                currentRequest.setRequest(message);
            }
        } catch(IOException e) {
            stdout.println(e);
        }
    }
}