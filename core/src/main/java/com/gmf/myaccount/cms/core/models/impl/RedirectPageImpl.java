package com.gmf.myaccount.cms.core.models.impl;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmf.cms.commons.core.utils.LinksUtil;
import com.gmf.myaccount.cms.core.models.RedirectPage;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = RedirectPage.class, resourceType = RedirectPageImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class RedirectPageImpl implements RedirectPage {
	protected static final String RESOURCE_TYPE = "gmf-myaccount/components/structure/redirectPage";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String redirectTarget;
	
	@SlingObject
    @JsonIgnore
    protected ResourceResolver resourceResolver;

	@Self
    private SlingHttpServletRequest slingRequest;

	@ScriptVariable
	@JsonIgnore
	private Resource resource;
	
	private boolean hasRedirect;
	
	private String redirectPath;
	
	@PostConstruct
    protected void initModel() {
		try {
			this.hasRedirect = StringUtils.isNotBlank(redirectTarget);
			redirect(resource);
		} catch( IOException ioEx ) {
			log.error("IOException trying to redirect the page template:\n {}", ioEx);
		}
	}
	
	public void redirect( Resource resource ) throws IOException {
		SlingBindings slingBindings = (SlingBindings) slingRequest.getAttribute(SlingBindings.class.getName());
		SlingHttpServletResponse response = slingBindings.getResponse();

		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page currentPage = null;
		if( pageManager != null ) {
			currentPage = pageManager.getContainingPage(resource);
		}
		boolean wcmModeEdit = (WCMMode.fromRequest(slingRequest).toString().equalsIgnoreCase("EDIT"));
		boolean wcmModeDisabled = (WCMMode.fromRequest(slingRequest).toString().equals("DISABLED"));
		boolean wcmModePreview = (WCMMode.fromRequest(slingRequest).toString().equals("PREVIEW"));

		if( wcmModeEdit ) {
			this.redirectPath = LinksUtil.checkInternalExternalURLByResource(redirectTarget, resource);
		}
		if( StringUtils.isNotBlank(redirectTarget) && ((wcmModeDisabled) || (wcmModePreview)) && response != null ) {
			// check for recursion
			if( currentPage != null && !redirectTarget.equals(currentPage.getPath()) && StringUtils.isNotBlank(redirectTarget) ) {
				// check for absolute path
				final int protocolIndex = redirectTarget.indexOf(":/");
				final int queryIndex = redirectTarget.indexOf('?');

				if( protocolIndex > -1 && (queryIndex == -1 || queryIndex > protocolIndex) ) {
					this.redirectPath = redirectTarget;
				} else {
					resource = resourceResolver.getResource(redirectTarget);
					this.redirectPath = LinksUtil.checkInternalExternalURLByResource(redirectTarget, resource);
					log.debug("Final redirect path: {}", this.redirectPath);
				}
				response.sendRedirect(this.redirectPath);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
	
	@Override
	public String getRedirectTarget() {
		return this.redirectTarget;
	}
	
	@Override
	public String getRedirectPath() {
		return this.redirectPath;
	}
	
	@Override
	public boolean hasRedirect() {
		return this.hasRedirect;
	}
}
