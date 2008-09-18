/**
 * VG2toUnicode.java
 *
 * VG2toUnicode implements font encoding conversion from the Visual Ge'ez 2
 * to to Visual Ge'ez Unicode.
 * The conversion is done by mapping a symbol or combination two symbols to
 * a single unicode symbol. The Unicode representation of Ethopic characters
 * can be accesed at http://www.unicode.org/charts/PDF/Unicode-5.0/ 
 */

package FontConverter;

/**
 * VG2toUnicode class accepts a string in VG2 encoding and returns an equivalent 
 * VG Unicode string
 */
import java.io.*;
import java.util.*;
public class VG2toUnicode {
    
 
    /** This holds the font mapping for stand alone symbols */ 
    static int mapsrc[]={104,163,166,255,72,231,108,167,76,47,710,63,8225,109,209,184,165,187,77,228,224,92,339,124,402,114,8222,182,8240,202,82,233,8230,115,250,83,238,185,353,62,235,113,8221,81,246,179,204,8218,170,79,161,98,198,66,239,118,352,86,8249,116,172,84,232,97,99,210,67,211,8211,94,96,126,8220,110,194,78,241,8226,233,190,34,174,8212,120,168,88,229,37,107,181,75,247,44,225,60,243,119,253,234,87,227,59,8520,58,226,122,178,90,248,162,207,8482,206,222,121,8224,8216,195,252,89,215,100,199,196,193,68,236,106,176,169,203,74,237,103,85,71,175,217,45,200,95,245,61,338,192,197,732,43,244,254,8212,212,249,213,91,218,123,219,93,201,125,242,102,251,240,205,216,70,230,208,112,173,80,177};
 
    /** the equivalent unicode font mapping for stand alone characters */  
    static char mapdest[]={'\u1200','\u1202','\u1203','\u1204','\u1205','\u1206','\u1208','\u120B','\u120D','\u1210','\u1213','\u1215','\u1216','\u1218','\u1219','\u121A','\u121B','\u121C','\u121D','\u121E','\u121F','\u1220','\u1223','\u1225','\u1226','\u1228','\u1229','\u122A','\u122B','\u122C','\u122D','\u122E','\u122F','\u1230','\u1233','\u1235','\u1236','\u1238','\u123B','\u123D','\u123E','\u1240','\u1243','\u1245','\u1246','\u1247','\u124B','\u1250','\u1253','\u1255','\u1256','\u1260','\u1263','\u1265','\u1266','\u1268','\u126B','\u126D','\u126E','\u1270','\u1273','\u1275','\u1276','\u1277','\u1278','\u127B','\u127D','\u127E','\u127F','\u1280','\u1283','\u1285','\u128B','\u1290','\u1293','\u1295','\u1296','\u1297','\u1298','\u129B','\u129D','\u129E','\u129F','\u12A0','\u12A3','\u12A5','\u12A6','\u12A7','\u12A8','\u12AB','\u12AD','\u12AE','\u12B8','\u12BB','\u12BD','\u12BE','\u12C8','\u12C9','\u12CB','\u12CD','\u12CE','\u12D0','\u12D3','\u12D5','\u12D6','\u12D8','\u12DB','\u12DD','\u12DE','\u12E0','\u12E3','\u12E5','\u12E6','\u12E7','\u12E8','\u12E9','\u12EA','\u12EB','\u12EC','\u12ED','\u12EE','\u12F0','\u12F1','\u12F3','\u12F4','\u12F5','\u12F6','\u1300','\u1301','\u1303','\u1304','\u1305','\u1306','\u1308','\u130B','\u130D','\u130E','\u1313','\u1320','\u1323','\u1325','\u1326','\u1328','\u1329','\u132A','\u132B','\u132C','\u132D','\u132E','\u132F','\u1330','\u1333','\u1335','\u1336','\u1338','\u133B','\u133D','\u133E','\u1340','\u1343','\u1345','\u1346','\u1348','\u1349','\u134A','\u134B','\u134C','\u134D','\u134E','\u134F','\u1350','\u1353','\u1355','\u1356'};
  
    /**  font mapping for symbols + possible suffix */ 
    static int mapsuffixsrc[][]={{104,35},{108,35},{108,33},{108,64},{108,214},{167,42},{47,35},{47,33},{47,64},{339,33},{339,64},{115,35},{115,33},{115,64},{250,42},{185,35},{185,33},{185,64},{353,42},{113,36},{113,69},{113,38},{113,164},{113,186},{8218,36},{8218,69},{8218,38},{8218,164},{8218,86},{98,35},{98,33},{98,64},{198,42},{118,35},{118,33},{118,64},{352,42},{116,36},{116,69},{116,38},{99,36},{99,69},{99,38},{94,35},{94,64},{94,64},{94,214},{110,35},{110,33},{110,64},{223,35},{223,33},{223,64},{120,35},{120,33},{120,64},{107,35},{107,33},{107,64},{107,214},{107,164},{181,42},{107,186},{44,35},{44,33},{44,64},{44,214},{44,186},{225,42},{44,164},{234,69},{234,38},{59,35},{8250,33},{8520,64},{122,35},{122,33},{122,64},{178,42},{162,36},{162,69},{162,38},{196,33},{196,42},{169,33},{169,42},{103,35},{103,33},{103,64},{103,214},{103,164},{103,186},{45,35},{45,33},{45,64},{200,42},{8217,35},{8217,33},{8217,64},{91,35},{91,33},{91,64},{218,42},{93,35},{201,33},{201,64},{112,36},{112,69},{112,38}};
    
    /** The euivalent unicode font mapping for symbols with suffix */
    static char mapsuffixdest[]={'\u1201','\u1209','\u120A','\u120C','\u120E','\u120F','\u1211','\u1212','\u1214', '\u1222','\u1224','\u1231','\u1232','\u1234','\u1237','\u1239','\u123A','\u123C','\u123F','\u1241','\u1242','\u1244','\u124A','\u124D','\u1251','\u1252','\u1254','\u125A','\u125D','\u1261','\u1262','\u1264','\u1267','\u1269','\u126A','\u126C','\u126F','\u1271','\u1272','\u1274','\u1279','\u127A','\u127C','\u1281','\u1282','\u1284','\u1286','\u1291','\u1292','\u1294','\u1299','\u129A','\u129C','\u12A1','\u12A2','\u12A4','\u12A9','\u12AA','\u12AC','\u12B0','\u12B2','\u12B3','\u12B5','\u12B9','\u12BA','\u12BC','\u12C0','\u12C2','\u12C3','\u12C5','\u12CA','\u12CC','\u12D1','\u12D2','\u12D4','\u12D9','\u12DA','\u12DC','\u12DF','\u12E1','\u12E2','\u12E4','\u12F2','\u12F7','\u1302','\u1307','\u1309','\u130A','\u130C','\u1310','\u1312','\u1315','\u1321','\u1322','\u1324','\u1327','\u1331','\u1332','\u1334','\u1339','\u133A','\u133C','\u133F','\u1341','\u1342','\u1344','\u1351','\u1352','\u1354'};
    
    /** list of suffixes used for VG2 encoding */
    static int suffix[]={33,35,36,38,42,64,69,164,186,214};
    
    /** Creates a new instance of VG2toUnicode */
    public VG2toUnicode(){
      
        
    }
    /** Convert() accepts a string with VG2 encoding and returns a new string 
     *  containing VG Unicode symbols.
     *  the conversion is done by comparing each symbol and its possible
     *  suffix to the table.
     */
    public static String Convert(String src)
    {
        char dest[] = new char[src.length()];
        int i=0;
        int j=0;
       
        while(i<src.length()){
            if(i<src.length()-1 && isSuffix((int)src.charAt(i+1))){
                dest[j++]= ConvertChar(src.charAt(i),src.charAt(i+1));
                i+=2;
            }
            else{
                dest[j++]=ConvertChar(src.charAt(i));
                i++;
            }
        }
       return new String(dest,0,i);
    }
    /** isSuffix() this returns true if the symbol is a suffix. false otherwise */
    public static boolean isSuffix(int c)
    {
        for(int i=0;i<suffix.length;i++) {
            if (suffix[i]==c) return true;
        }
        return false;
    }
    /** This accepts a symbol and finds its equivalent representation in unicode 
     */
    public static char ConvertChar(int c){
        for(int i=0;i<mapsrc.length;i++){
            if(c==mapsrc[i]) return mapdest[i];
         }
        return ' ';
    }
    
    /** This accepts a symbol and its suffix and finds its equivalent representation in unicode 
     */
    public static char ConvertChar(int c, int suf){
        for(int i=0;i<mapsuffixsrc.length;i++){
            
            if(c==mapsuffixsrc[i][0] && suf== mapsuffixsrc[i][1]) 
                return mapsuffixdest[i];
         }
        return ' ';
    }
    
  /*
    public static void main(String args[] ){
       try
	{
	   VG2toUnicode converter = new VG2toUnicode();
           //FileInputStream fr= new FileInputStream("c:\\vgtest1.txt");
           //InputStreamReader isr = new InputStreamReader(fr);//, "UTF8");
           FileReader fin= new FileReader("c:\\vgtest1.txt");
           BufferedReader in = new BufferedReader(fin); 
          // Reader in = new BufferedReader(isr);
           FileOutputStream fos = new FileOutputStream("c:\\vgunicode3.txt");
           Writer out = new OutputStreamWriter(fos, "UTF8");
           
	  //FileWriter r=new FileWriter("c:\\vgunicode.txt");
	  //BufferedWriter bw = new BufferedWriter(r);
	  String str= new String();
          
	  while((str=in.readLine())!=null) {
              String s=converter.Convert(str);
		out.write(s);
                out.write("\r\n");
                System.out.print("here we go"+ s);
          }
	 
	 fin.close();
	 out.close();
			
	}
	catch(Exception e)
	{
	System.out.println("Exception occur..."+e);
	}
    }
   */
}