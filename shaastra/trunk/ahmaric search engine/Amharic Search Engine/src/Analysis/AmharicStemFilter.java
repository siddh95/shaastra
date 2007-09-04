/*
 * AmharicStemFilter.java
 *
 * Created on April 14, 2002, 3:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Analysis;

import java.io.IOException;
import Stemmer.*;
import org.apache.lucene.analysis.*;

public final class AmharicStemFilter extends TokenFilter {
  private Stemmer stemmer;

  public AmharicStemFilter(TokenStream in) {
    super(in);
    stemmer = new Stemmer();
  }

  /** Returns the next input Token, after being stemmed */
  public final Token next() throws IOException {
    Token token = input.next();
    if (token == null)
      return null;
    else {
      String s = stemmer.stem(token.termText());
      if (s != token.termText()) 
  	    token.setTermText(s);
      return token;
    }
  }
}

