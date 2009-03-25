/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.persistence;

import com.tetratech.edas2.model.AttributableCreate;
import com.tetratech.edas2.model.AttributableUpdate;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AttributableEntityListener {

	Log log = LogFactory.getLog(AttributableEntityListener.class);

	@PrePersist
	public void prePersist(Object entity) {
		if (entity instanceof AttributableCreate) {
			((AttributableCreate) entity).setCreatedBy(userId());
			((AttributableCreate) entity).setCreatedDate(now());
		}
	}

	@PreUpdate
	public void preUpdate(Object entity) {
		log.debug("updating: " + entity);
		if (entity instanceof AttributableUpdate) {
			log.debug("attributing: " + entity);
			((AttributableUpdate) entity).setUpdatedBy(userId());
			((AttributableUpdate) entity).setUpdatedDate(now());
		}
	}

	private Date now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private Long userId() {
		return 1L;
	}
}
