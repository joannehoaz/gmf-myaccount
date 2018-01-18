package com.gmf.myaccount.cms.core.models.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.json.JSONObject;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmf.cms.commons.core.utils.JcrUtilService;
import com.gmf.cms.commons.core.utils.LinksUtil;
import com.gmf.myaccount.cms.core.beans.Link;
import com.gmf.myaccount.cms.core.models.Footer;
import com.gmf.myaccount.cms.core.utils.MyAccountConstants;
import com.gmf.myaccount.cms.core.utils.ServiceUtil;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = Footer.class, resourceType = FooterImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FooterImpl implements Footer {
	protected static final String RESOURCE_TYPE = "gmf-myaccount/components/structure/footer";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	@SlingObject
    @JsonIgnore
    protected ResourceResolver resourceResolver;
	
	@ScriptVariable
	private Page currentPage;
	
	@Inject
    private ResourceResolverFactory resourceResolverFactory;
	
	private List<Link> brandLinks;
	
	private List<Link> dealerLinks;
	
	private List<Link> socialLinks;
	
	private List<Link> siteLinks;
	
	private String copyright;

	@PostConstruct
	protected void initModel() {
		this.brandLinks = new ArrayList<Link>();
		this.dealerLinks = new ArrayList<Link>();
		this.socialLinks = new ArrayList<Link>();
		this.siteLinks = new ArrayList<Link>();
		
		Locale locale = currentPage != null ? currentPage.getLanguage() : null;
		String languageCountry = locale != null ? StringUtils.lowerCase(locale.toString()) : MyAccountConstants.LANGUAGE_ENGLISH;
		String footerContentPath = new StringBuilder(MyAccountConstants.CONTENT_GMF_PATH).append(languageCountry).append(MyAccountConstants.FOOTER_CONTENT_PATH).toString();
		log.debug("Path to global footer to retrieve content from: {}", footerContentPath);
		
		// resolver should not be null if injected, but an extra check
		if( resourceResolver == null ) {
			resourceResolver = resourceResolverFactory != null ? ServiceUtil.getServiceResolver(resourceResolverFactory, MyAccountConstants.MY_ACCOUNT_SERVICE_USER) : JcrUtilService.getResourceResolver();
		}
		if( resourceResolver != null ) {
			Resource footerResource = resourceResolver.getResource(footerContentPath);
			ValueMap footerMap = footerResource != null ? footerResource.getValueMap() : ValueMap.EMPTY;
			// brand 
			String[] brandLinksArray = footerMap.get("multiLogo", String[].class);
			this.initLinks(this.brandLinks, brandLinksArray, null, "logoLink", "footerLogo", "logoTitle", "logoWindow");
			// dealer "multiLink", "footerLink", "linkTitle", "linkWindow"			
			String[] dealerLinksArray = footerMap.get("multiLink", String[].class);
			this.initLinks(this.dealerLinks, dealerLinksArray, "linkTitle", "footerLink", null, "linkTitle", "linkWindow");
			// social 			
			String[] socialLinksArray = footerMap.get("socialMedia", String[].class);
			this.initLinks(this.socialLinks, socialLinksArray, null, "footerSocialLink", "footerSocialLogo", "socialAltTxt", "socialWindow");
			// site
			String[] siteLinksArray = footerMap.get("siteLinks", String[].class);
			this.initLinks(this.siteLinks, siteLinksArray, "sitemapTitle", "sitemapLink", null, "sitemapTitle", "sitemapWindow");		
			// copyright
			this.copyright = footerMap.get("copyRightText", String.class);	// &copy;
		}
	}
	
	/**
	 * 
	 * @param linksList
	 * @param linksArray
	 * @param titlePropertyName
	 * @param linkPropertyName
	 * @param iconPropertyName
	 * @param altPropertyName
	 * @param windowPropertyName
	 */
	private void initLinks( List<Link> linksList, 
							String[] linksArray, String titlePropertyName, 
							String linkPropertyName, String iconPropertyName, 
							String altPropertyName, String windowPropertyName ) {
		if( ArrayUtils.isNotEmpty(linksArray) ) {
			for( String jsonLink : linksArray ) {
				JSONObject documentObj = new JSONObject(jsonLink);
				Link link = new Link();
				link.setTitle(titlePropertyName != null ? documentObj.getString(titlePropertyName) : null);
				link.setIconPath(iconPropertyName != null ? documentObj.getString(iconPropertyName) : null);
				link.setLink(documentObj.getString(linkPropertyName));
				// TODO final link
				link.setFinalLink(LinksUtil.checkInternalURLByPath(link.getLink(), resourceResolver));
				// TODO window
				link.setTargetAttribute(StringUtils.isNotBlank(documentObj.getString(windowPropertyName)) 
							? LinksUtil.getWindowTarget(documentObj.getString(windowPropertyName)) : LinksUtil.getWindowTarget(Boolean.FALSE));
					
				link.setAltAttribute(documentObj.getString(altPropertyName));
				link.setTitleAttribute(link.getAltAttribute());
				if( (titlePropertyName != null && StringUtils.isNotBlank(link.getTitle()) && StringUtils.isNotBlank(link.getLink()))
						|| (iconPropertyName != null && StringUtils.isNotBlank(link.getIconPath()) && StringUtils.isNotBlank(link.getLink())) ) {
					linksList.add(link);
				}
			}
		}
	}
	
	public List<Link> getBrandLinks() {
		return this.brandLinks;
	}
	
	public List<Link> getDealerLinks() {
		return this.dealerLinks;
	}
	
	public List<Link> getSocialLinks() {
		return this.socialLinks;
	}
	
	public List<Link> getSiteLinks() {
		return this.siteLinks;
	}
	
	public String getCopyright() {
		return this.copyright;
	}
}
