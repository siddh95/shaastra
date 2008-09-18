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

/** Transforms the token stream as per the Porter stemming algorithm.
    Note: the input to the stemming filter must already be in lower case,
    so you will need to use LowerCaseFilter or LowerCaseTokenizer farther
    down the Tokenizer chain in order for this to work properly!
    <P>
    To use this filter with other analyzers, you'll want to write an
    Analyzer class that sets up the TokenStream chain as you want it.
    To use this with LowerCaseTokenizer, for example, you'd write an
    analyzer like this:
    <P>
    <PRE>
    class MyAnalyzer extends Analyzer {
      public final TokenStream tokenStream(String fieldName, Reader reader) {
        return new PorterStemFilter(new LowerCaseTokenizer(reader));
      }
    }
    </PRE>
*/
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
      if (s != token.termText()) // Yes, I mean object reference comparison here
  	    token.setTermText(s);
      return token;
    }
  }
}

