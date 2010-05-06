/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ViewObject {

    private static final Log log = LogFactory.getLog(ViewObject.class.getName());
    // We may need a method to do the work of p:process
    
//    protected HttpServletResponse response;
//    protected ServletContext context;
//    protected Portal portal;
    
    // Making private because we might change the way this is handled, and we don't want to change
    // all subclasses. They'll just use the accessor method.
    private String contextPath;
    
    public ViewObject() { }
    
    public ViewObject(String contextPath) {
        this.contextPath = contextPath;
    }
    
    protected String getContextPath() {
        return contextPath;
    }
    
    // Get an arbitrary property value - i.e., one that the view object doesn't have a method for.
    public String getProperty(String property) {
        String value = null;
        return value;  // finish this
    }
    
    protected String encodeUrl(String url) {
        String encoding = "ISO-8859-1";
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(url, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error("Error encoding url " + url + " with encoding " + encoding + ": Unsupported encoding.");
        }
        return encodedUrl;
    }

}
