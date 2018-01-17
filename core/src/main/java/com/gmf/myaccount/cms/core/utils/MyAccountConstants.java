package com.gmf.myaccount.cms.core.utils;

import com.day.cq.commons.jcr.JcrConstants;

public class MyAccountConstants {

	private MyAccountConstants() {
		// restricting instantiation
	}
	
	// TODO move constants to gmf-commons
	public static final String SLASH = "/";
	public static final String CONTENT_GMF_PATH = "/content/gmf/";
	public static final String LANGUAGE_ENGLISH = "en";
	public static final String FOOTER_CONTENT_PATH = SLASH + JcrConstants.JCR_CONTENT + "/footer";
	public static final String HEADER_CONTENT_PATH = SLASH + JcrConstants.JCR_CONTENT + "/header";
}
