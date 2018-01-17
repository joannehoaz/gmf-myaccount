package com.gmf.myaccount.cms.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceUtil.class);
	
	/**
	 * Returns the resource resolver for a specified service user.
	 * 
	 * @param resourceResolverFactory
	 * @param service
	 * @return
	 */
	public static final ResourceResolver getServiceResolver( final ResourceResolverFactory resourceResolverFactory, final String service ) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, service);
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
		} catch( LoginException loginEx ) {
			LOG.error("LoginException trying to get the resource resolver for the service user: {}\n{}", service, loginEx);
		}
		
		return resourceResolver;
	}
}
