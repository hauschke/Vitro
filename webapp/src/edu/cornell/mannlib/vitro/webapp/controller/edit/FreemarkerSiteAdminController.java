/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.controller.edit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vedit.beans.LoginFormBean;
import edu.cornell.mannlib.vedit.util.FormUtils;
import edu.cornell.mannlib.vitro.webapp.beans.VClassGroup;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.FreemarkerHttpServlet;
import edu.cornell.mannlib.vitro.webapp.controller.login.LoginTemplateHelper;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;
import freemarker.template.Configuration;

public class FreemarkerSiteAdminController extends FreemarkerHttpServlet {
	
	private static final Log log = LogFactory.getLog(FreemarkerSiteAdminController.class);

    public static final String VERBOSE = "verbosePropertyListing";
    
	public String getTitle(String siteName) {
        return siteName + " Site Administration";
	}

    public String getBody(VitroRequest vreq, Map<String, Object> body, Configuration config) {

        String loginStatus = null;
        
        LoginFormBean loginHandler = (LoginFormBean)vreq.getSession().getAttribute("loginHandler");
        if (loginHandler != null) {
            loginStatus = loginHandler.getLoginStatus();
        }
        
        // Not logged in: just show login form
        if (loginHandler == null || !"authenticated".equals(loginStatus)) {
            body.put("loginPanel", new LoginTemplateHelper(vreq).showLoginPage(vreq, body, config));
            return mergeBodyToTemplate("siteAdmin-main.ftl", body, config);           
        } 
        
        int securityLevel = Integer.parseInt( loginHandler.getLoginRole() );
                
        if (securityLevel >= LoginFormBean.CURATOR) {
            String verbose = vreq.getParameter("verbose");
            if( "true".equals(verbose)) {
                vreq.getSession().setAttribute(VERBOSE, Boolean.TRUE);
            } else if( "false".equals(verbose)) {
                vreq.getSession().setAttribute(VERBOSE, Boolean.FALSE);
            }
        }        


        body.put("singlePortal", new Boolean(vreq.getFullWebappDaoFactory().getPortalDao().isSinglePortal()));
        
        WebappDaoFactory wadf = vreq.getFullWebappDaoFactory();
        
// Not used
//        int languageProfile = wadf.getLanguageProfile();
//        String languageMode = null;
//        if ( 200 <= languageProfile && languageProfile < 300 ) {
//            languageMode = "OWL Mode";        	
//        } else if ( 100 == languageProfile ) {
//            languageMode = "RDF Schema Mode";
//        } 
//        body.put("languageModeStr",  languageMode);       
        

        // Create map for data input entry form
        List classGroups = wadf.getVClassGroupDao().getPublicGroupsWithVClasses(true,true,false); // order by displayRank, include uninstantiated classes, don't get the counts of individuals        
        Iterator classGroupIt = classGroups.iterator();
        ListOrderedMap optGroupMap = new ListOrderedMap();
        while (classGroupIt.hasNext()) {
            VClassGroup group = (VClassGroup)classGroupIt.next();
            List classes = group.getVitroClassList();
            optGroupMap.put(group.getPublicName(),FormUtils.makeOptionListFromBeans(classes,"URI","PickListName",null,null,false));
        }
        body.put("VClassIdOptions", optGroupMap);


        
        return mergeBodyToTemplate("siteAdmin-main.ftl", body, config);
        
    }

}
