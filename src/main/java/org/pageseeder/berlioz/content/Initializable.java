/*
 * Copyright 2015 Allette Systems (Australia)
 * http://www.allette.com.au
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pageseeder.berlioz.content;

/**
 * Generators implementing this interface can be initialised and destroyed.
 *
 * <p>They must provide <code>init</code> and <code>destroy</code> methods.
 *
 * <p>Use this interface when the initialisation of a generator may yield exception
 * or is processor intensive.
 *
 * @author Christophe Lauret
 *
 * @version Berlioz 0.6.0 - 8 July 2010
 * @since Berlioz 0.6
 */
public interface Initializable {

  /**
   * Initialises this generator.
   */
  void init();

  /**
   * Destroys this generator.
   */
  void destroy();

}
