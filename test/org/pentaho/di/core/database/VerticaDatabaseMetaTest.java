/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2013 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.core.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaBase;

/**
 * Tests for the Vertica Database Meta classes.
 *
 * @author sflatley
 *
 */
public class VerticaDatabaseMetaTest {

  /**
   * 
   */
  private static final String PRIMARY_KEY_NAME = "PrimaryKeyName";
  /**
   * 
   */
  private static final String TECHNICAL_KEY_NAME = "TechnicalKeyName";

  /**
   * Tests the supportsTimeStampToDateConversion method.
   */
  @Test
  public void testSupportsTimeStampToDateConversion() {
    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    assertFalse( databaseInterface.supportsTimeStampToDateConversion() );

    databaseInterface = new Vertica5DatabaseMeta();
    assertFalse( databaseInterface.supportsTimeStampToDateConversion() );

  }

  @Test
  public void testSupportBlob() {
    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    assertFalse( databaseInterface.supportsGetBlob() );
  }

  @Test
  public void testIsDisplaySizeTwiceThePrecision() {
    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    assertTrue( databaseInterface.isDisplaySizeTwiceThePrecision() );
  }

  @Test
  public void testGetDefaultBinaryFieldDefinition() {

    ValueMetaInterface vm = new ValueMetaBase( "TestFieldBinary", ValueMetaInterface.TYPE_BINARY, -1, 1 );
    String expDefaultBinaryField = "VARBINARY";

    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    String defaultBinaryField =
        databaseInterface.getFieldDefinition( vm, TECHNICAL_KEY_NAME, PRIMARY_KEY_NAME, false, false, false );
    String assertMessage = defaultBinaryField + " should be equal to expected " + expDefaultBinaryField;

    assertTrue( assertMessage, expDefaultBinaryField.equals( defaultBinaryField ) );
  }

  @Test
  public void testGetOneByteBinaryFieldDefinition() {

    ValueMetaInterface vm = new ValueMetaBase( "TestFieldBinary", ValueMetaInterface.TYPE_BINARY, 1, 1 );
    String expDefaultBinaryField = "VARBINARY(1)";

    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    String defaultBinaryField =
        databaseInterface.getFieldDefinition( vm, TECHNICAL_KEY_NAME, PRIMARY_KEY_NAME, false, false, false );
    String assertMessage = defaultBinaryField + " should be equal to expected " + expDefaultBinaryField;

    assertTrue( assertMessage, expDefaultBinaryField.equals( defaultBinaryField ) );
  }

  @Test
  public void testGetMaximumByteBinaryFieldDefinition() {

    ValueMetaInterface vm = new ValueMetaBase( "TestFieldBinary", ValueMetaInterface.TYPE_BINARY, 65000, 1 );
    String expDefaultBinaryField = "VARBINARY(65000)";

    DatabaseInterface databaseInterface = new VerticaDatabaseMeta();
    String defaultBinaryField =
        databaseInterface.getFieldDefinition( vm, TECHNICAL_KEY_NAME, PRIMARY_KEY_NAME, false, false, false );
    String assertMessage = defaultBinaryField + " should be equal to expected " + expDefaultBinaryField;

    assertTrue( assertMessage, expDefaultBinaryField.equals( defaultBinaryField ) );
  }

}
