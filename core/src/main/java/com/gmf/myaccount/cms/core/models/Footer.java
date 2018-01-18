package com.gmf.myaccount.cms.core.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.gmf.myaccount.cms.core.beans.Link;

/**
 * Defines the {@code Footer} Sling Model used for the {@code /apps/gmf-myaccount/components/structure/footer} component.
 *
 * @since 
 */
@ConsumerType
public interface Footer {
    
	/**
     * Returns a list of brand links in the footer (in the first row), as {@link Link} elements.
     *
     * @return {@link Collection} of {@link com.gmf.myaccount.cms.core.beans.Link}s
     * @since com.adobe.cq.wcm.core.components.models 12.2.0
     */
    default List<Link> getBrandLinks() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns a list of dealer/other country links in the footer (in the second row), as {@link Link} elements.
     * 
     * @return {@link Collection} of {@link com.gmf.myaccount.cms.core.beans.Link}s
     */
    default List<Link> getDealerLinks() {
    		throw new UnsupportedOperationException();
    }
    
    /**
     * Returns a list of social media icon links in the footer, as {@link Link} elements.
     * 
     * @return {@link Collection} of {@link com.gmf.myaccount.cms.core.beans.Link}s
     */
    default List<Link> getSocialLinks() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns a list of internal site links in the footer, as {@link Link} elements, such as
     * links to the site map and the privacy policy.
     * 
     * @return {@link Collection} of {@link com.gmf.myaccount.cms.core.beans.Link}s
     */
    default List<Link> getSiteLinks() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the text of the copyright and/or trademark information in the footer.
     * 
     * @return	returns the copyright text in the footer
     */
    default String getCopyright() {
    		throw new UnsupportedOperationException();
    }
}
