/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.consumer.behaviors;

import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.repack.org.apache.commons.codec.binary.Base64;

public class BasicAuthenticationBehavior implements OClientBehavior {

  private final String user;
  private final String password;

  public BasicAuthenticationBehavior(String user, String password) {
    this.user = user;
    this.password = password;
  }

  @Override
  public ODataClientRequest transform(ODataClientRequest request) {
    String userPassword = user + ":" + password;
    String encoded = Base64.encodeBase64String(userPassword.getBytes());
    encoded = encoded.replaceAll("\r\n?", "");
    return request.header("Authorization", "Basic " + encoded);

  }

}