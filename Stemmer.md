#Stemmer Description

# Introduction #
Stemming provides much simpler yet powerful way of processing documents. The stemmer uses a Suffix Stripping algorithm.


# Details #

Stemming provides much simpler yet powerful way of processing documents. The stemmer uses a Suffix Stripping algorithm. The reason we have used this stemmer, instead of a full-fledged morphological analyzer is its simplicity. The suffix stripping algorithm uses a list of "rules" that are stored to provide a path for the algorithm, given an input word form, to find its Stem form. These rules for nouns are only considered for this work. Verbs have not been considered as the search terms are rarely verbs. Some examples of the rules include:
<li> if the word ends in 'VISARGA', remove the 'VISARGA'<br>
<li>  if the word ends in 'E,SSA,SIGNU ', remove the 'E,SSA,SIGNU’<br>
<li>  if the word ends in 'BHA,SIGNVIRAMA,YA,VISARGA', remove the ‘BHA,SIGNVIRAMA,YA,VISARGA’<br>
<br>
<strong>The detailed set of rules with examples are available in the Downloads area<br>
<br>
<a href='http://shaastra.googlecode.com/files/StemmingRules.doc'>http://shaastra.googlecode.com/files/StemmingRules.doc</a>