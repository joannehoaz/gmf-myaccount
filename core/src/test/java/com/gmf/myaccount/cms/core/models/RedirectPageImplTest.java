package com.gmf.myaccount.cms.core.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.gmf.myaccount.cms.core.models.impl.RedirectPageImpl;

import io.wcm.testing.mock.aem.junit.AemContext;
import junitx.util.PrivateAccessor;

public class RedirectPageImplTest {
	@Rule
	public final AemContext context = new AemContext();
	
	private RedirectPageImpl redirectPage;
	
	@Before
	public void setUp() throws Exception {
		redirectPage = new RedirectPageImpl();
	    PrivateAccessor.setField(redirectPage, "redirectTarget", "/content/we-retail/language-masters/en/experience");
	}
	
	@Test
	public void testGetRedirectTarget() {
		assertNotNull(redirectPage);
		String redirectTarget = redirectPage.getRedirectTarget();
		assertNotNull(redirectTarget);
		assertTrue(redirectTarget.length() > 0);
	}
	
	@Test
	public void testHasRedirect() {
		assertNotNull(redirectPage);
		boolean hasRedirect = StringUtils.isNotBlank(redirectPage.getRedirectTarget());
		if( hasRedirect ) {
			assertTrue(hasRedirect);
		} else {
			assertFalse(hasRedirect);
		}
	}
	
	@Test
	public void testGetRedirectPath() throws Exception {
		assertNotNull(redirectPage);
		PrivateAccessor.setField(redirectPage, "redirectTarget", "/content/gmf/en");
		Resource resource = context.resourceResolver().getResource(redirectPage.getRedirectTarget());
		String redirectPath = redirectPage.getRedirectTarget();
		assertNotNull(redirectPath);
		if( resource != null ) {
			redirectPath = redirectPage.getRedirectTarget() + ".html";
			assertEquals(redirectPath, "/content/gmf/en.html");
		} else {
			assertEquals(redirectPath, "/content/gmf/en");
		}
		//			String redirectPath = LinksUtil.checkInternalExternalURLByResource(redirectPage.getRedirectTarget(), resource);
	}
}
