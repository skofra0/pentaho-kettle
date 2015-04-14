/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

package org.pentaho.di.trans.steps.loadsave.setter;

import java.lang.reflect.Method;

public class MethodSetter<T> implements Setter<T> {
  private final Method method;

  public MethodSetter( Method method ) {
    this.method = method;
  }

  @Override
  public void set( Object obj, T value ) {
    try {
      method.invoke( obj, value );
    } catch ( Exception e ) {
      throw new RuntimeException( "Error invoking " + method + " on " + obj, e );
    }
  }

}
