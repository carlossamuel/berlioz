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
package org.pageseeder.berlioz.furi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A URI Template for constructing URIs following the same structure.
 *
 * Instances of this class implement the URI templates as defined by the URI Template (Draft 3) by
 * Joe Gregorio.
 *
 * A URI Template follows the URI syntax and can be expanded given a set of variable values.
 *
 * @see <a
 *      href="http://bitworking.org/projects/URI-Templates/spec/draft-gregorio-uritemplate-03.html">URI
 *      Template (draft 3)</a>
 *
 * @author Christophe Lauret
 *
 * @version Berlioz 0.9.32
 * @since Berlioz 0.9.32
 */
public class URITemplate implements Expandable {

  /**
   * The regular expression pattern to identify template expansions within the template.
   */
  private static final Pattern EXPANSION_PATTERN = Pattern.compile("\\{[^}]*}");

  /**
   * The string representation of the URL template.
   */
  private final String _template;

  /**
   * The list of tokens corresponding to this URL template.
   */
  private final List<Token> _tokens;

  /**
   * Creates a new URI Template instance.
   *
   * @param template A String following the URI template syntax.
   *
   * @throws NullPointerException If the specified template is <code>null</code>.
   * @throws URITemplateSyntaxException If the string provided does not follow the proper syntax.
   */
  public URITemplate(String template) {
    if (template == null)
      throw new NullPointerException("Cannot create a URI template with a null template");
    this._tokens = digest(template);
    this._template = template;
  }

  // TODO: method to indicate how many variables are used involved in the template

  /**
   * Creates a new URI Template instance using the specified token factory.
   *
   * <p>If the specified factory is <code>null</code>, the default is used.
   *
   * @param template A String following the URI template syntax.
   * @param factory  A token factory in order to choose the URI template syntax to use.
   *
   * @throws NullPointerException If the specified template is <code>null</code>.
   * @throws URITemplateSyntaxException If the string provided does not follow the proper syntax.
   */
  public URITemplate(String template, TokenFactory factory) {
    if (template == null)
      throw new NullPointerException("Cannot create a URI template with a null template");
    this._tokens = digest(template, factory != null? factory : TokenFactory.getInstance());
    this._template = template;
  }

  /**
   * Expands the template to produce a URI as defined by the URI Template specifications.
   *
   * @param parameters The list of variables and their values for substitution.
   */
  @Override
  public final String expand(Parameters parameters) {
    StringBuffer uri = new StringBuffer();
    for (Token t : this._tokens) {
      uri.append(t.expand(parameters));
    }
    return uri.toString();
  }

  /**
   * Method provided for convenience.
   *
   * It returns the same as:
   *
   * <pre>
   * return new URITemplate(template).expand(variables);
   * </pre>
   *
   * @param template The URI template.
   * @param parameters The parameter values to use for substitution.
   *
   * @return The corresponding expanded URI.
   */
  public static String expand(String template, Parameters parameters) {
    return new URITemplate(template).expand(parameters);
  }

  /**
   * Returns the list of tokens corresponding to the specified URI template.
   *
   * @param template The URI template to digest.
   *
   * @return The corresponding list of URL tokens.
   *
   * @throws URITemplateSyntaxException If the string cannot be parsed.
   */
  public static List<Token> digest(String template) throws URITemplateSyntaxException {
    return digest(template, TokenFactory.getInstance());
  }

  /**
   * Returns the list of tokens corresponding to the specified URI template.
   *
   * @param template The URI template to digest.
   * @param factory  The token factory to use.
   *
   * @return The corresponding list of URL tokens.
   *
   * @throws URITemplateSyntaxException If the string cannot be parsed.
   */
  public static List<Token> digest(String template, TokenFactory factory) throws URITemplateSyntaxException {
    List<Token> tokens = new ArrayList<Token>();
    Matcher m = EXPANSION_PATTERN.matcher(template);
    int start = 0;
    while (m.find()) {
      // any text since the last expansion
      if (m.start() > start) {
        String text = template.substring(start, m.start());
        tokens.add(new TokenLiteral(text));
      }
      // add the expansion
      String exp = m.group();
      tokens.add(factory.newToken(exp));
      // update the state variables
      start = m.end();
    }
    // any text left over, including if there were no expansions
    if (start < template.length()) {
      String text = template.substring(start, template.length());
      // support for wild cards only at the end of the string.
      if (text.endsWith("*")) {
        tokens.add(new TokenLiteral(text.substring(0, text.length() - 1)));
        tokens.add(factory.newToken("*"));
      } else {
        tokens.add(new TokenLiteral(text));
      }
    }
    return tokens;
  }

  /**
   * Returns the underlying list of tokens.
   *
   * <p>
   * Note: this method exposes the underlying structure of this class and should remain protected.
   *
   * @return The underlying list of tokens.
   */
  protected final List<Token> tokens() {
    return this._tokens;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if ((o == null) || (o.getClass() != this.getClass()))
      return false;
    URITemplate t = (URITemplate) o;
    return (this._template == t._template || (this._template != null && this._template.equals(t._template)));
  }

  @Override
  public int hashCode() {
    return 127 * this._template.hashCode() + this._template.hashCode();
  }

  @Override
  public String toString() {
    return this._template;
  }
}