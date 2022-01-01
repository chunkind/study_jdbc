package step01;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestApp {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = null;
        PostMethod method= null;
        List<NameValuePair> params = toNameValuePair();

        method = new PostMethod("http://chunkind.com/");
        method.setRequestBody(params.toArray(new NameValuePair[0]));
        httpClient.getParams().setParameter("http.connection.timeout", 2000);

        int statusCode = httpClient.executeMethod(method);

        if(200 == statusCode){
            System.out.println(method.getResponseBodyAsString());
        }else{
            System.out.println("error : " + statusCode);
        }

    }

    public static List<NameValuePair> toNameValuePair(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("test","chunkind"));
        return params;
    }
}
