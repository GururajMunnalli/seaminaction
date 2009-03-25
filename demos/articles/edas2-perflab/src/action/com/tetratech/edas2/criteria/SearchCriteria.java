/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.criteria;

/**
 * A super class for criteria objects which provides some utility features.
 */
public abstract class SearchCriteria {
    protected String buildLikePattern(String search) {
		if (search == null || search.trim().length() == 0) {
			return "%";
		}
		else {
			return "%".concat(search.replace('*', '%').toLowerCase()).concat("%");
		}
    }
    
    public abstract void reset();
}
