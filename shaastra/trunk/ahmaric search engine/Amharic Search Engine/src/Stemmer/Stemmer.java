/*
 * Stemmer.java
 *
 * Stemmer.java implements the modified version of porter stemming algorithm 
 * for Amharic, the Ethiopian National Language.
 * 
 *  Porter stemmer algorithm. The original paper is in
 *
 *     Porter, 1980, An algorithm for suffix stripping, Program, Vol. 14,
 *     no. 3, pp 130-137,
 *
 *  See also http://www.tartarus.org/~martin/PorterStemmer
 */

package Stemmer;
import java.io.*;
/*
 * The stemmer transforms a word into its root form. The input
 * word can be provided a character at time (by calling add()), or at once
 * by calling one of the various stem(something) methods.
 */
public class Stemmer {
   private char[] b; /* Stores the word in character array */
   private int i, k,    /* offsets into b */
               i_end, /* offset to end of stemmed word */
               i_beg, /* offste from the begining of the stemmed word */
               j,           
               l;
   private static final int INC = 50;
                     /* unit of size whereby b is increased */
  
                     /* unicode  representaion of ethiopic symbols  */ 
   private static final char[][] UNICODECHARS=
   { 
   {'\u1200', '\u1201','\u1202','\u1203','\u1204','\u1205','\u1206'},
   {'\u1208', '\u1209','\u120A','\u120B','\u120C','\u120D','\u120E'},
   {'\u1210', '\u1211','\u1212','\u1213','\u1214','\u1215','\u1216'},
   {'\u1218', '\u1219','\u121A','\u121B','\u121C','\u121D','\u121E'},
   {'\u1220', '\u1221','\u1222','\u1223','\u1224','\u1225','\u1226'},
   {'\u1228', '\u1229','\u122A','\u122B','\u122C','\u122D','\u122E'},
   {'\u1230', '\u1231','\u1232','\u1233','\u1234','\u1235','\u1236'},
   {'\u1238', '\u1239','\u123A','\u123B','\u123C','\u123D','\u123E'},
   {'\u1240', '\u1241','\u1242','\u1243','\u1244','\u1245','\u1246'},
   {'\u1260', '\u1261','\u1262','\u1263','\u1264','\u1265','\u1266'},
   {'\u1268', '\u1269','\u126A','\u126B','\u126C','\u126D','\u126E'},
   {'\u1270', '\u1271','\u1272','\u1273','\u1274','\u1275','\u1276'},
   {'\u1278', '\u1279','\u127A','\u127B','\u127C','\u127D','\u127E'},
   {'\u1280', '\u1281','\u1282','\u1283','\u1284','\u1285','\u1286'},
   {'\u1290', '\u1291','\u1292','\u1293','\u1294','\u1295','\u1296'},
   {'\u1298', '\u1299','\u129A','\u129B','\u129C','\u129D','\u129E'},
   {'\u12A0', '\u12A1','\u12A2','\u12A3','\u12A4','\u12A5','\u12A6'},
   {'\u12A8', '\u12A9','\u12AA','\u12AB','\u12AC','\u12AD','\u12AE'},
   {'\u12B8', '\u12B9','\u12BA','\u12BB','\u12BC','\u12BD','\u12BE'},
   {'\u12C8', '\u12C9','\u12CA','\u12CB','\u12CC','\u12CD','\u12CE'},
   {'\u12D0', '\u12D1','\u12D2','\u12D3','\u12D4','\u12D5','\u12D6'},
   {'\u12D8', '\u12D9','\u12DA','\u12DB','\u12DC','\u12DD','\u12DE'},
   {'\u12E0', '\u12E1','\u12E2','\u12E3','\u12E4','\u12E5','\u12E6'},
   {'\u12E8', '\u12E9','\u12EA','\u12EB','\u12EC','\u12ED','\u12EE'},
   {'\u12F0', '\u12F1','\u12F2','\u12F3','\u12F4','\u12F5','\u12F6'},
   {'\u12F8', '\u12F9','\u12FA','\u12FB','\u12FC','\u12FD','\u12FE'},
   {'\u1300', '\u1301','\u1302','\u1303','\u1304','\u1305','\u1306'},
   {'\u1308', '\u1309','\u130A','\u130B','\u130C','\u130D','\u130E'},
   {'\u1320', '\u1321','\u1322','\u1323','\u1324','\u1325','\u1326'},
   {'\u1328', '\u1329','\u132A','\u132B','\u132C','\u132D','\u132E'},
   {'\u1340', '\u1341','\u1342','\u1343','\u1344','\u1345','\u1346'},
   {'\u1348', '\u1349','\u134A','\u134B','\u134C','\u134D','\u134E'},
   {'\u1350', '\u1351','\u1352','\u1353','\u1354','\u1355','\u1356'}
   };
   
   /* The following static variables define constants of the unicode values
    * of Amharic script symbols.
    */
   private static char HA ='\u1200';
   private static char HU= '\u1201';
   private static char HI ='\u1202';
   private static char HAA ='\u1203';
   private static char HEE ='\u1204';
   private static char HE ='\u1205';
   private static char HO ='\u1206';
   private static char LA ='\u1208';
   private static char LU ='\u1209';
   private static char LI ='\u120A';
   private static char LAA ='\u120B';
   private static char LEE ='\u120C';
   private static char LE ='\u120D';
   private static char LO ='\u120E';
   private static char HHA ='\u1210';
   private static char HHU= '\u1211';
   private static char HHI ='\u1212';
   private static char HHAA ='\u1213';
   private static char HHEE ='\u1214';
   private static char HHE ='\u1215';
   private static char HHO ='\u1216';
   private static char MA = '\u1218';
   private static char MU ='\u1219';
   private static char MI ='\u121A';
   private static char MAA ='\u121B';
   private static char MEE ='\u121C';
   private static char ME ='\u121D';
   private static char MO ='\u121E';
   private static char SZA='\u1220';
   private static char SZU='\u1221';
   private static char SZI='\u1222';
   private static char SZAA='\u1223';
   private static char SZEE='\u1224';
   private static char SZE='\u1225';
   private static char SZO='\u1226';
   private static char RA='\u1228';
   private static char RU='\u1229';
   private static char RI='\u122A';
   private static char RAA='\u122B';
   private static char REE='\u122C';
   private static char RE='\u122D';
   private static char RO='\u122E';
   private static char SA='\u1230';
   private static char SU='\u1231';
   private static char SI='\u1232';
   private static char SAA='\u1233';
   private static char SEE='\u1234';
   private static char SE='\u1235';
   private static char SO='\u1236';
   private static char SHA='\u1238';
   private static char SHU='\u1239';
   private static char SHI='\u123A';
   private static char SHAA='\u123B';
   private static char SHEE='\u123C';
   private static char SHE='\u123D';
   private static char SHO='\u123E';
   private static char QA='\u1240';
   private static char QU='\u1241';
   private static char QI='\u1242';
   private static char QAA='\u1243';
   private static char QEE='\u1244';
   private static char QE='\u1245';
   private static char QO='\u1246';
   private static char BA='\u1260';
   private static char BU='\u1261';
   private static char BI='\u1262';
   private static char BAA='\u1263';
   private static char BEE='\u1264';
   private static char BE='\u1265';
   private static char BO='\u1266';
   private static char VA='\u1268';
   private static char VU='\u1269';
   private static char VI='\u126A';
   private static char VAA='\u126B';
   private static char VEE='\u126C';
   private static char VE='\u126D';
   private static char VO='\u126E';
   private static char TA='\u1270';
   private static char TU='\u1271';
   private static char TI='\u1272';
   private static char TAA='\u1273';
   private static char TEE='\u1274';
   private static char TE='\u1275';
   private static char TO='\u1276';
   private static char CA='\u1278';
   private static char CU='\u1279';
   private static char CI='\u127A';
   private static char CAA='\u127B';
   private static char CEE='\u127C';
   private static char CE='\u127D';
   private static char CO='\u127E';
   private static char XA='\u1280';
   private static char XU='\u1281';
   private static char XI='\u1282';
   private static char XAA='\u1283';
   private static char XEE='\u1284';
   private static char XE='\u1285';
   private static char XWAA='\u128B';
   private static char XO='\u1286';
   private static char NA='\u1290';
   private static char NU='\u1291';
   private static char NI='\u1292';
   private static char NAA='\u1293';
   private static char NEE='\u1294';
   private static char NE='\u1295';
   private static char NO='\u1296';
   private static char NYA='\u1298';
   private static char NYU='\u1299';
   private static char NYI='\u129A';
   private static char NYAA='\u129B';
   private static char NYEE='\u129C';
   private static char NYE='\u129D';
   private static char NYO='\u129E';
   private static char A='\u12A0';
   private static char U='\u12A1';
   private static char I='\u12A2';
   private static char AA='\u12A3';
   private static char EE='\u12A4';
   private static char E='\u12A5';
   private static char O='\u12A6';
   private static char KA='\u12A8';
   private static char KU='\u12A9';
   private static char KI='\u12AA';
   private static char KAA='\u12AB';
   private static char KEE='\u12AC';
   private static char KE='\u12AD';
   private static char KO='\u12AE';
   private static char KXA='\u12B8';
   private static char KXU='\u12B9';
   private static char KXI='\u12BA';
   private static char KXAA='\u12BB';
   private static char KXEE='\u12BC';
   private static char KXE='\u12BD';
   private static char KXO='\u12BE';
   private static char WA='\u12C8';
   private static char WU='\u12C9';
   private static char WI='\u12CA';
   private static char WAA='\u12CB';
   private static char WEE='\u12CC';
   private static char WE='\u12CD';
   private static char WO='\u12CE';
   private static char A2='\u12D0';
   private static char U2='\u12D1';
   private static char I2='\u12D2';
   private static char AA2='\u12D3';
   private static char EE2='\u12D4';
   private static char E2='\u12D5';
   private static char O2='\u12D6';
   private static char ZA='\u12D8';
   private static char ZU='\u12D9';
   private static char ZI='\u12DA';
   private static char ZAA='\u12DB';
   private static char ZEE='\u12DC';
   private static char ZE='\u12DD';
   private static char ZO='\u12DE';
   private static char ZHA='\u12E0';
   private static char ZHU='\u12E1';
   private static char ZHI='\u12E2';
   private static char ZHAA='\u12E3';
   private static char ZHEE='\u12E4';
   private static char ZHE='\u12E5';
   private static char ZHO='\u12E6';
   private static char YA='\u12E8';
   private static char YU='\u12E9';
   private static char YI='\u12EA';
   private static char YAA='\u12EB';
   private static char YEE='\u12EC';
   private static char YE='\u12ED';
   private static char YO='\u12EE';
   private static char DA='\u12F0';
   private static char DU='\u12F1';
   private static char DI='\u12F2';
   private static char DAA='\u12F3';
   private static char DEE='\u12F4';
   private static char DE='\u12F5';
   private static char DO='\u12F6';
   private static char JA='\u1300';
   private static char JU='\u1301';
   private static char JI='\u1302';
   private static char JAA='\u1303';
   private static char JEE='\u1304';
   private static char JE='\u1305';
   private static char JO='\u1306';
   private static char GA='\u1308';
   private static char GU='\u1309';
   private static char GI='\u130A';
   private static char GAA='\u130B';
   private static char GEE='\u130C';
   private static char GE='\u130D';
   private static char GO='\u130E';
   private static char THA='\u1320';
   private static char THU='\u1321';
   private static char THI='\u1322';
   private static char THAA='\u1323';
   private static char THEE='\u1324';
   private static char THE='\u1325';
   private static char THO='\u1326';
   private static char CHA='\u1328';
   private static char CHU= '\u1329';
   private static char CHI='\u132A';
   private static char CHAA='\u132B';
   private static char CHEE='\u132C';
   private static char CHE='\u132D';
   private static char CHO='\u132E';
   private static char TZA='\u1340';
   private static char TZU='\u1341';
   private static char TZI='\u1342';
   private static char TZAA='\u1343';
   private static char TZEE='\u1344';
   private static char TZE='\u1345';
   private static char TZO='\u1346';
   private static char FA='\u1348';
   private static char FU='\u1349';
   private static char FI='\u134A';
   private static char FAA='\u134B';
   private static char FEE='\u134C';
   private static char FE='\u134D';
   private static char FO='\u134E';
   private static char PA='\u1350';
   private static char PU='\u1351';
   private static char PI='\u1352';
   private static char PAA='\u1353';
   private static char PEE='\u1354';
   private static char PE='\u1355';
   private static char PO='\u1356';
   
   /* Creates new instance of stemmer */
   public Stemmer()
   {  b = new char[INC];
      i = 0;
      i_end = 0;
      l=0;
   }

   /**
    * Add a character to the word being stemmed.  When you are finished
    * adding characters, you can call stem(void) to stem the word.
    */

   public void add(char ch)
   {  if (i == b.length)
      {  char[] new_b = new char[i+INC];
         for (int c = 0; c < i; c++) new_b[c] = b[c];
         b = new_b;
      }
      b[i++] = ch;
   }


   /** Adds wLen characters to the word being stemmed contained in a portion
    * of a char[] array. This is like repeated calls of add(char ch), but
    * faster.
    */

   public void add(char[] w, int wLen)
   {  if (i+wLen >= b.length)
      {  char[] new_b = new char[i+wLen+INC];
         for (int c = 0; c < i; c++) new_b[c] = b[c];
         b = new_b;
      }
      for (int c = 0; c < wLen; c++) b[i++] = w[c];
   }

   /**
    * After a word has been stemmed, it can be retrieved by toString(),
    * or a reference to the internal buffer can be retrieved by getResultBuffer
    * and getResultLength (which is generally more efficient.)
    */
   public String toString() { return new String(b,i_beg,i_end); }
   /**
    * Returns the length of the word resulting from the stemming process.
    */
   public int getResultLength() { return i_end - i_beg; }

   /**
    * Returns a reference to a character buffer containing the results of
    * the stemming process.  You also need to consult getResultLength()
    * to determine the length of the result.
    */
   public char[] getResultBuffer() { return b; }
   
   /**
    * Returns a true if a true if the word to be stemmed ends with specified
    * suffix otherwise false.
    */ 
   public boolean  ends(String strsuffix)
   {
       char[] suffix= strsuffix.toCharArray();
       for(int i=0;i<suffix.length; i++)
       {
           if(b[k-suffix.length + i]!= suffix[i]) return false;
       }
       return true;
   }
   
   /**
    * Returns a true if a true if the word to be stemmed begins with specified
    * prefix otherwise false.
    */ 
   public boolean  begins(char [] prefix)
   {
       for(int i=0;i<prefix.length; i++)
       {
           if(b[l+i]!= prefix[i]) return false;
       }
       return true;
   }
   /* step1() gets rid of the suffix -na used as conjunction of words. 
    * This suffix can be added to any word either root word or modified
    * 
    * eg. sewachna -> sew
    *     betna -> bet
    */
   
    public void step1()
   {
     if(b[k]==NAA) 
       {
           k-=1;
       }
    }

   /* step2() gets rid of all the other suffixes that can be add to a word.
    * Amharic root words can be modified by adding suffixes of length from
    * one to five.
    * eg.   betoch -> bet
    *       sewoch -> sew
    *       felegehalehu -> feleg
    *       felegshalehu -> feleg
    *       felegachwalehugn  -> feleg
    *           .
    *           .
    *           .
    * The suffix can be for specifing number, action( persons- first, second..)
    * or combinations of both.
    * In this step the possible combinations are checked starting from suffix
    * of size six to suffix of size one.  
    */      
   public void step2()
   {
      try
      {
           //SUFFIX Five
       if(k>6 && b[k-4]==A && b[k-3]==CE && b[k-2]==XWAA && b[k-1]==LA && b[k]==HU) //a ch hua le hu
       {
           k-=5;
       }
       else if(k >6 && b[k-4]==A && b[k-3]==CA && b[k-2]==WAA &&b[k-1]==LA && b[k]==HU) //a ch wa le hu
       {
           k-=5;
       }
       else if(k>6 && b[k-4]==A && b[k-3]==CA &&b[k-2]==WAA && b[k-1]==LA && b[k]==SHE) 
       {
           k-=5;
       }
       //SUFFIX FOUR
       else if(k>5 && b[k-3]==CE &&b[k-2]==HU && b[k-1]==A && b[k]==TE) 
       {
           k-=4;
       }
       else if(k>5 && b[k-3]==WAA &&b[k-2]==CE && b[k-1]==XWAA && b[k]==LE) 
       {
           k-=4;
       }
       else if(k>5 && b[k-3]==WAA &&b[k-2]==CA && b[k-1]==WAA && b[k]==LE) 
       {
           k-=4;
       }
       else if(k>5 && b[k-3]==CA &&b[k-2]==WAA && b[k-1]==LA && b[k]==CE) 
       {
           k-=4;
       }
        
       else if(k>5 && b[k-3]==A && b[k-2]==TAA &&b[k-1]==LA && b[k]==HU) //a ta le hu
       {
           k-=4;
       }
        else if(k>5 && b[k-3]==A &&b[k-2]==TAA && b[k-1]==LA && b[k]==SHE) 
       {
           k-=4;
       }     
       
       
       else if(k>5 && b[k-3]==HAA &&b[k-2]==CA && b[k-1]==WAA && b[k]==LE) 
       {
           k-=4;
       }
       else if(k>5 && b[k-3]==CA &&b[k-2]==WAA && b[k-1]==LA && b[k]==HE) 
       {
           k-=4;
       }
        else if(k>5 && b[k-3]==SHAA &&b[k-2]==A &&b[k-1]==CA && b[k]==WE) 
       {
           k-=4;
       }
       else if(k>5 && b[k-3]==SHAA && b[k-2]==CA &&b[k-1]==WAA && b[k]==LE) 
       {
           k-=4;
       }
       //suffix 3
        else if(k>3 && b[k-2]==SHE &&b[k-1]==NYAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==SHE &&b[k-1]==WAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==SHAA &&b[k-1]==TAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==SHE &&b[k-1]==NAA && b[k]==LE) 
       {
           k-=3;
       }
      
       else if(k>3 && b[k-2]==SHAA && b[k-1]==CA && b[k]==WE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NYAA &&b[k-1]==LA && b[k]==SHE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==LA && b[k]==SHE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==LA && b[k]==SHE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==XWAA &&b[k-1]==CHA && b[k]==WE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==KXA &&b[k-1]==NYAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==KXA &&b[k-1]==WAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==HAA &&b[k-1]==TA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==KXA &&b[k-1]==NAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NYAA &&b[k-1]==LA && b[k]==HE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==LA && b[k]==HE)
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==TAA &&b[k-1]==LA && b[k]==HE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==LA && b[k]==HE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==XWAA && b[k-1]==CE && b[k]==HU) //hua ch hu
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==XWAA &&b[k-1]==CA && b[k]==WE) //hua ch w
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==HAA &&b[k-1]==LA && b[k]==HU) //ha le hu
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==SHAA &&b[k-1]==LA && b[k]==HU) //sha le hu
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==LA && b[k]==HU) //wa le hu
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==CAA &&b[k-1]==CE && b[k]==HU) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==CAA &&b[k-1]==CE && b[k]==WE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==CE && b[k]==HU) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==CA && b[k]==WE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==CE &&b[k-1]==HU && b[k]==NYE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==CE &&b[k-1]==HU && b[k]==TE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==CE &&b[k-1]==HU && b[k]==NE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NYA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==HAA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==SHAA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==TAA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==LA && b[k]==CE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NE &&b[k-1]==HAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NE &&b[k-1]==SHAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NA &&b[k-1]==WAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==NAA &&b[k-1]==TAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WE &&b[k-1]==NYAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WE &&b[k-1]==HAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WE &&b[k-1]==SHAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WE &&b[k-1]==TAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==TAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WE &&b[k-1]==NAA && b[k]==LE) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==CE && b[k]==HU) 
       {
           k-=3;
       }
       else if(k>3 && b[k-2]==WAA &&b[k-1]==CE && b[k]==WE) 
       {
           k-=3;
       }
      else if(k>3 && b[k-2]==WE && b[k-1]==YAA && b[k]==NE) 
       {
           k-=3;
       }
        //2 SUFFIXES
        else if(k>2 && b[k-1]==SHE && b[k]==NYE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==SHE && b[k]==WE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==SHAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==SHE && b[k]==NE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NYAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==HAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==SHAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==TAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(b[k-1]==SHAA && b[k]==LE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==HU) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CA && b[k]==HU) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==HU) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==KXA && b[k]==NYE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==KXA && b[k]==WE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==HAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==KXA && b[k]==NE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==HU && b[k]==HE) //hu h
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==HU && b[k]==SHE)// hu sh
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==HU && b[k]==TE) //hu t
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==XWAA && b[k]==TE) //hua t
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==NYE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==HE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==SHE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==WE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==CE && b[k]==NE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NE && b[k]==HE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NE && b[k]==SHE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NA && b[k]==WE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WE && b[k]==NYE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WE && b[k]==HE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WE && b[k]==SHE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WE && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==WO && b[k]==CE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==YAA && b[k]==NE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==YAA && b[k]==TE) 
       {
           k-=2;
       }
       else if(k>2 && b[k-1]==NA && b[k]==TE) 
       {
           k-=2;
       }
       //SUFFIX ONE
       else if(k>1 && b[k]==NYE) 
       {
           k-=1;
       }
        else if(k>1 && b[k]==HE) 
       {
           k-=1;
       }
        else if(k>1 && b[k]==SHE) 
       {
           k-=1;
       }
        else if(b[k]==WE) 
       {
           k-=1;
       }
        else if(b[k]==NE) 
       {
           k-=1;
       }
        else if(k>1 && b[k]==WI) 
       {
           k-=1;
       }
        else if(k>1 && b[k]==CE) 
       {
           k-=1;
       }
        else if(k>1 && b[k]==LE) 
       {
           k-=1;
       }
        else if(b[k]==TE) 
       {
           k-=1;
       }
        else if(b[k]==ME) 
       {
           k-=1;
       }
      }catch(ArrayIndexOutOfBoundsException e)
      {
          System.out.println("Array exception");
      }
       
   }
   
   /* step3() gets rid of all the prefixes that can be add to a word.
    * Amharic root words can be modified by adding prefixes of length from
    * one to four.
    * eg.   bebet -> bet
    *       endsew -> sew
    *           .
    *           .
    *           .
    * 
    * In this step the possible combinations are checked starting from prefix
    * of size four to prefix of size one.  
    */
   public void step3()
   {
       //PREFIX 3
        if(b[l]==E && b[l+1]==NE && b[l+2]==DE) 
       {
           l+=3;
       }
        else if(b[l]==E && b[l+1]==NE && b[l+2]==DA) 
       {
           l+=3;
       }
        else if(b[l]==E && b[l+1]==NE && b[l+2]==DI) 
       {
           l+=3;
       }
        else if(b[l]==YA && b[l+1]==MAA && b[l+2]==YE) 
       {
           l+=3;
       }
       //PREFIX 2
       else if(b[l]==E && b[l+1]==NE ) 
       {
           l+=2;
       }
        else if(b[l]==E && b[l+1]==NA ) 
       {
           l+=2;
       }
        else if(b[l]==E && b[l+1]==YA ) 
       {
           l+=2;
       }
        else if(b[l]==A && b[l+1]==LE ) 
       {
           l+=2;
       }
        else if(b[l]==YA && b[l+1]==MI ) 
       {
           l+=2;
       }
         else if(b[l]==YA && b[l+1]==ME ) 
       {
           l+=2;
       }
        else if(b[l]==A && b[l+1]==YE ) 
       {
           l+=2;
       }
         else if(b[l]==A && b[l+1]==SE ) 
       {
           l+=2;
       }
         else if(b[l]==BA && b[l+1]==MA ) 
       {
           l+=2;
       }
         else if(b[l]==YA && b[l+1]==TA ) 
       {
           l+=2;
        }
        
       //PREFIX 1 
       else  if(b[l]==E ) 
       {
           l+=1;
       }
       else if(b[l]==LE ) 
       {
           l+=1;
       }
        else if(b[l]==TE ) 
       {
           l+=1;
       }
        else if(b[l]==YE ) 
       {
           l+=1;
       }
       else if(b[l]==BE ) 
       {
           l+=1;
       }
        else if(b[l]==BA ) 
       {
           l+=1;
       }
        else if(b[l]==BI ) 
       {
           l+=1;
       }
        else if(b[l]==MA ) 
       {
           l+=1;
       }
        else if(b[l]==KA ) 
       {
           l+=1;
       }
        else if(b[l]==LA ) 
       {
           l+=1;
       }
       else if(b[l]==YAA ) 
       {
           l+=1;
       }
        else if(b[l]==YA ) 
       {
           l+=1;
       }
        else if(b[l]==LE ) 
       {
           l+=1;
       }
       
   }
   /* step4() removes all vowels used to modify a symbol from the stemmed word.
    * Amharic vowels are used only for pronouncitaion. A combination of 
    * a symbol called sadis(6th) and vowels represented by single symbol. So
    * in this step all symbols will be represented by their sadis. 
    *    
    *  
    */
   public void step4(){
       boolean found;
       for(int w=l;w<=k;w++){
           found=false;
           for(i=0;i<UNICODECHARS.length;i++){
               for(j=0;j<UNICODECHARS[i].length;j++){
                   if(b[w]==UNICODECHARS[i][j]) {
                       b[w]=UNICODECHARS[i][5];
                       found= true;
                       break;
                   }
                   if(found) break;
               }
           }
       }
   }
   /* step5() gets rid of all the infixes that can be add to a word.
    * infix can be added in a form of repeating one symbol in some manner
    * or repeting the word it self 
    * 
    * eg.  tiratire -> tire 
    *      felalege -> felge
    *           .
    *           .
    *           .
    *  Since the previous step step 4 removed the vowels this step needs to look
    *  for symmetry and reption of symbols.
    */
  
   public void step5(){
       int len=k-l+1;
       boolean repeated=true;
     /*  if(len>2 && len%2==0){
           for(int i=l; i<len/2;i++){
               if(b[i]!=b[len+i]){
                   repeated=false;
                   break;
               }
           }
           if(repeated){ k-=len; return;}
       }
*/       
       for(int i=l;i<k;i++)
       {
           if(b[i]==b[i+1]) {
               for(int j=i+1;j<k;j++) b[j]=b[j+1];
               k--;
               break;
           }
       }
   }
   /* stem() used to start the stemming procedure */
   
   public void stem()
   {
       k=i-1;
       l=0;
       if(k>1){step1();step2();step3();step4(); step5();
       }
       i_end=k+1-l;i=0;
       i_beg=l;
   }
   public String stem(String w)
   {
   add(w.toCharArray(), w.length());
   stem();
   return toString();
   }
   /*
   public static void main(String args[])
   {
       StringBuffer buffer = new StringBuffer();
       Stemmer s= new Stemmer();
      try {
          FileInputStream fis = new FileInputStream("c:\\test.txt");
          InputStreamReader isr = new InputStreamReader(fis, "UTF8");
          Reader in = new BufferedReader(isr);
          FileOutputStream fos = new FileOutputStream("c:\\test2.txt");
          Writer out = new OutputStreamWriter(fos, "UTF8");
           

          int ch;
          
          while ((ch = in.read()) > -1) {
             if((char)ch!='\r')
             {
                 s.add((char)ch);
                 buffer.append((char)ch);
             }
             else
             {
             ch = in.read();
             s.stem();
             out.write(buffer.toString()+ "         "+ s.toString() +"\r\n");
             buffer = new StringBuffer();
             s = new Stemmer();
             }
          }
          in.close();
          out.close();
          
      } catch (IOException e) {
          e.printStackTrace();
          
      }

   }
    **/
}

