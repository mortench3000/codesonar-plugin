package org.jenkinsci.plugins.codesonar.services;

import hudson.AbortException;
import hudson.model.BuildListener;
import java.io.Serializable;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author Andrius
 */
public class HttpService implements Serializable {
    private BuildListener listener;

    public String getContentFromUrlAsString(String url) throws AbortException {
        log(String.format("Request sent to {0}", url));
        
        String output;
        try {
            output = Request.Get(url).execute().returnContent().asString();
        } catch (Exception e) {
            log(String.format("Exception \"{0}\" cought from url \"{1}\"", e.getMessage(), url));
            
            throw new AbortException(e.getMessage());
        } 
        
        return output;
    }
    
    public void setListener(BuildListener listener) {
        this.listener = listener;
    }
    
    private void log(String content) {
        listener.getLogger().println(content);
    }
}
