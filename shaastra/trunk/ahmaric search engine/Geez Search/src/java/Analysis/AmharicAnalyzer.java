/*
 * AmharicStemAnalyzer.java
 *
 * Created on April 14, 2002, 3:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Analysis;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * Filters {@link StandardTokenizer} with {@link StandardFilter}, {@link
 * LowerCaseFilter} and {@link StopFilter}, using a list of English stop words.
 *
 * @version $Id: StandardAnalyzer.java 472959 2006-11-09 16:21:50Z yonik $
 */
public class AmharicAnalyzer extends Analyzer {
  private Set stopSet;

  /** An array containing some common English words that are usually not
  useful for searching. */
  public static final String[] STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS;

  /** Builds an analyzer with the default stop words ({@link #STOP_WORDS}). */
  public AmharicAnalyzer() {
    this(STOP_WORDS);
  }

  /** Builds an analyzer with the given stop words. */
  public AmharicAnalyzer(Set stopWords) {
    stopSet = stopWords;
  }

  /** Builds an analyzer with the given stop words. */
  public AmharicAnalyzer(String[] stopWords) {
    stopSet = StopFilter.makeStopSet(stopWords);
  }

  /** Builds an analyzer with the stop words from the given file.
   * @see WordlistLoader#getWordSet(File)
   */
  public AmharicAnalyzer(File stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  /** Builds an analyzer with the stop words from the given reader.
   * @see WordlistLoader#getWordSet(Reader)
   */
  public AmharicAnalyzer(Reader stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  /** Constructs a {@link StandardTokenizer} filtered by a {@link
  StandardFilter}, a {@link LowerCaseFilter} and a {@link StopFilter}. */
  public TokenStream tokenStream(String fieldName, Reader reader) {
    TokenStream result = new StandardTokenizer(reader);
    result = new AmharicStemFilter(result);
    result = new StopFilter(result, stopSet);
    return result;
  }
}
