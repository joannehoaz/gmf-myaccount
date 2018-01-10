package com.gmf.myaccount.cms.core.models;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Defines the {@code RedirectPage} Sling Model used for the {@code /apps/gmf-myaccount/components/structure/redirectPage} component.
 *
 * @since 
 */
@ConsumerType
public interface RedirectPage {
	/**
     * Retrieves the redirect target path of the page.
     *
     * @return the redirect path as a {@link String}
     * @since 
     */
    default String getRedirectTarget() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the finalized redirect path the page should redirect to.
     * 
     * @return the final redirect path the page should redirect to as a {@link String}
     */
    default String getRedirectPath() {
    		throw new UnsupportedOperationException();
    }
    
    /**
     * Returns {@code true} if the page has a redirect target path defined.
     *
     * @return true if the page has a redirect target path defined, otherwise false
     * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
     */
    default boolean hasRedirect() {
		return false;
	}
}
