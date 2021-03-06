/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.format.json;

import java.math.BigDecimal;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.odata4j.core.Guid;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.core.UnsignedByte;
import org.odata4j.edm.EdmSimpleType;
import org.odata4j.internal.InternalUtil;
import org.odata4j.repack.org.apache.commons.codec.binary.Base64;

public class JsonTypeConverter {

  public static OProperty<?> parse(String name, EdmSimpleType<?> type, String value) {

    if (EdmSimpleType.GUID.equals(type)) {
      Guid uValue = value == null ? null : Guid.fromString(value.substring(5, value.length() - 1));
      return OProperties.guid(name, uValue);
    } else if (EdmSimpleType.BOOLEAN.equals(type)) {
      Boolean bValue = value == null ? null : Boolean.parseBoolean(value);
      return OProperties.boolean_(name, bValue);
    } else if (EdmSimpleType.BYTE.equals(type)) {
      UnsignedByte bValue = value == null ? null : UnsignedByte.parseUnsignedByte(value);
      return OProperties.byte_(name, bValue);
    } else if (EdmSimpleType.SBYTE.equals(type)) {
      Byte bValue = value == null ? null : Byte.parseByte(value);
      return OProperties.sbyte_(name, bValue);
    } else if (EdmSimpleType.INT16.equals(type)) {
      Short sValue = value == null ? null : Short.parseShort(value);
      return OProperties.int16(name, sValue);
    } else if (EdmSimpleType.INT32.equals(type)) {
      Integer iValue = value == null ? null : Integer.parseInt(value);
      return OProperties.int32(name, iValue);
    } else if (EdmSimpleType.INT64.equals(type)) {
      Long lValue = value == null ? null : Long.parseLong(value);
      return OProperties.int64(name, lValue);
    } else if (EdmSimpleType.SINGLE.equals(type)) {
      Float fValue = value == null ? null : Float.parseFloat(value);
      return OProperties.single(name, fValue);
    } else if (EdmSimpleType.DOUBLE.equals(type)) {
      Double dValue = value == null ? null : Double.parseDouble(value);
      return OProperties.double_(name, dValue);
    } else if (EdmSimpleType.DECIMAL.equals(type)) {
      BigDecimal dValue = value == null ? null : new BigDecimal(value);
      return OProperties.decimal(name, dValue);
    } else if (EdmSimpleType.BINARY.equals(type)) {
      byte[] bValue = value == null ? null : new Base64().decode(value);
      return OProperties.binary(name, bValue);
    } else if (EdmSimpleType.DATETIME.equals(type)) {
      LocalDateTime dValue = null;
      if (value != null) {
        if (!value.startsWith("/Date(") || !value.endsWith(")/")) {
          throw new IllegalArgumentException("invalid date format");
        }
        String ticks = value.substring(6, value.length() - 2);
        String offset = null;
        int idx = ticks.indexOf('-');
        if (idx > 0) {
          offset = ticks.substring(idx + 1);
          ticks = ticks.substring(0, idx);
          dValue = new LocalDateTime(Long.parseLong(ticks));
          dValue = dValue.minusMinutes(Integer.valueOf(offset));
        } else if ((idx = ticks.indexOf('+')) > 0) {
          offset = ticks.substring(idx + 1);
          ticks = ticks.substring(0, idx);
          dValue = new LocalDateTime(Long.parseLong(ticks));
          dValue = dValue.plusMinutes(Integer.valueOf(offset));
        } else {
          // ticks are the milliseconds from 1970-01-01T00:00:00Z
          dValue = new LocalDateTime(Long.parseLong(ticks));
        }
      }
      return OProperties.datetime(name, dValue);
    } else if (EdmSimpleType.DATETIMEOFFSET.equals(type)) {
      return OProperties.datetimeOffset(name, InternalUtil.parseDateTime(value.substring(value.indexOf('\'') + 1, value.length() - 1)));
    } else if (EdmSimpleType.TIME.equals(type)) {
      LocalTime tValue = value == null ? null : new LocalTime(value);
      return OProperties.time(name, tValue);
    } else if (EdmSimpleType.STRING.equals(type) || type == null) {
      return OProperties.string(name, value);
    }
    throw new UnsupportedOperationException("type:" + type);
  }

}
