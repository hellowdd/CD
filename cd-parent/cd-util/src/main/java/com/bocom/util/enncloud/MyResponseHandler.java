package com.bocom.util.enncloud;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class MyResponseHandler implements ResponseHandler<String> {

	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
	    int status = response.getStatusLine().getStatusCode();
	    if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
	        HttpEntity entity = response.getEntity();
	        return entity != null ? EntityUtils.toString(entity) : null;
	    } else {
	        throw new RuntimeException("Unexpected response status: " + status);
	    }
	}

}
