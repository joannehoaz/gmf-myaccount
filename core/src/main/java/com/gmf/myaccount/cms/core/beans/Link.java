package com.gmf.myaccount.cms.core.beans;

public class Link {

	private String title;
	private String link;
	private String iconPath;
	private String titleAttribute;
	private String altAttribute;
	private String targetAttribute;
	private String finalLink;
	
	/**
	 * Default constructor.
	 */
	public Link() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param link
	 */
	public Link( String title, String link ) {
		this.setTitle(title);
		this.setLink(link);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink( String link ) {
		this.link = link;
	}

	public String getIconPath() {
		return this.iconPath;
	}

	public void setIconPath( String iconPath ) {
		this.iconPath = iconPath;
	}

	public String getTitleAttribute() {
		return this.titleAttribute;
	}

	public void setTitleAttribute( String titleAttribute ) {
		this.titleAttribute = titleAttribute;
	}

	public String getAltAttribute() {
		return this.altAttribute;
	}

	public void setAltAttribute( String altAttribute ) {
		this.altAttribute = altAttribute;
	}
	
	public String getTargetAttribute() {
		return this.targetAttribute;
	}
	
	public void setTargetAttribute( String targetAttribute ) {
		this.targetAttribute = targetAttribute;
	}

	public String getFinalLink() {
		return this.finalLink;
	}

	public void setFinalLink( String finalLink ) {
		this.finalLink = finalLink;
	}
}
