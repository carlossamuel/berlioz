/*
 * This file is part of the Berlioz library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.berlioz.generator;

import org.weborganic.berlioz.content.Cacheable;
import org.weborganic.berlioz.content.ContentGenerator;
import org.weborganic.berlioz.content.ContentGeneratorBase;
import org.weborganic.berlioz.content.ContentRequest;

import com.topologi.diffx.xml.XMLWriter;

/**
 * Generates no content.
 * 
 * <p>This content generator is only useful for when the XML header already contains
 * enough information for the purpose of application. 
 * 
 * @author Christophe Lauret (Weborganic)
 * @version 1 June 2010
 */
public final class NoContent extends ContentGeneratorBase implements ContentGenerator, Cacheable {

  /**
   * Always returns the <code>"nocontent"</code>.
   * 
   * {@inheritDoc}
   */
  public String getETag(ContentRequest req) {
    return "nocontent";
  }

  /**
   * Do nothing.
   * 
   * {@inheritDoc}
   */
  public void process(ContentRequest req, XMLWriter xml) {
  }

}
