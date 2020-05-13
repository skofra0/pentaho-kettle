/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2019 by Hitachi Vantara : http://www.pentaho.com
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

package plugin.deem.swt.fileopensave.api.providers;

/**
 * Created by bmorrise on 3/5/19.
 */
public class FromTo {

  private plugin.deem.swt.fileopensave.api.providers.File from;
  private plugin.deem.swt.fileopensave.api.providers.File to;

  public FromTo() {
  }

  public plugin.deem.swt.fileopensave.api.providers.File getFrom() {
    return from;
  }

  public void setFrom( plugin.deem.swt.fileopensave.api.providers.File from ) {
    this.from = from;
  }

  public plugin.deem.swt.fileopensave.api.providers.File getTo() {
    return to;
  }

  public void setTo( plugin.deem.swt.fileopensave.api.providers.File to ) {
    this.to = to;
  }
}
