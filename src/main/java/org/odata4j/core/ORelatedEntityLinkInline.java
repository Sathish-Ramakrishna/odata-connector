/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

/**
 * An <code>ORelatedEntityLinkInline</code> is an {@link ORelatedEntityLink} that also includes the referent inlined.
 *
 * <p>The {@link OLinks} static factory class can be used to create <code>ORelatedEntityLinkInline</code> instances.</p>
 *
 * @see OLinks
 */
public interface ORelatedEntityLinkInline extends ORelatedEntityLink {

  /**
   * Gets the inlined referent.
   */
  OEntity getRelatedEntity();
}
