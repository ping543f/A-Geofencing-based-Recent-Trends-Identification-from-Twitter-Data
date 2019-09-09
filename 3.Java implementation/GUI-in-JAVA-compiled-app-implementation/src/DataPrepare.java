package app.home;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import app.home.*;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;


//import statements
public class DataPrepare 
{
	
	static String[]   noun= {"Allah","sheikh","hasina","sahib","al","hasan","mashrafi","murtaza","rohinga","tamim","iqbal","robin","sarkar","ali","rifat","sohel","rahman","ayub","shihab","nabil","muzib","mujibur","mujib","rahman","rahim","ahmed","taskin","ria","maria","ayesha","bikal","hazrat","kader","mia","rohingya"  };
	static String sql;
	
	static IDictionary dict;
	static int count=0;
	static String hashTag="";
	
    public static void myMain(String query) 
    {
    	
    	sql=query;
    	
    	
    	String text="";
    	
    	try {
	    	String wordNetDirectory = "WordNet-3.0";
	        String path = wordNetDirectory + File.separator + "dict";
	        URL url = new URL("file", null, path);
	        dict = new Dictionary(url);
	        dict.open();
    	}catch(Exception e) {
    		
    	}
    	
    	
    	boolean flag= false;
    	int i=0;
        try
        {
//            FileInputStream file = new FileInputStream(new File("data_new.xlsx"));
// 
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
// 
//            //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
// 
//            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) 
//            {
//            	
//                Row row = rowIterator.next();
//                //For each row, iterate through all the columns
//                Iterator<Cell> cellIterator = row.cellIterator();
//                
//                while (cellIterator.hasNext()) 
//                {
//                    Cell cell = cellIterator.next();
//                
//                    if(cell.getColumnIndex()==6) {
//                    	
//                    	String lang=cell.getStringCellValue();
//                    	
//                    	if(lang.equals("en"))
//                    		flag=true;
//                    	else {
//							flag=false;
//						}
//                    }
//                    
//                    if(cell.getColumnIndex()==12 && flag==true) {
//                    	
//                        
//                        //Check the cell type and format accordingly
//    	                    switch (cell.getCellType()) 
//    	                    {
//    	                       
//    	                        case Cell.CELL_TYPE_STRING:
//    	                        	String status = cell.getStringCellValue();
//    	                        	status=linkRemover(status);
//    	                        	//status= status.replaceAll(" use", "");
//    	                        	if (!status.equals("")) {
//	    	                        	if(status.charAt(status.length()-1)!='.' ) {
//	    	                        		status +=".";
//	    	                        	}
////	    	                        	System.out.println(status);
//	    	                        	
//	    	                        	text+=status;
////	    	                        	System.out.println(text);
//    	                        	}
//    	                        	
////    	                        	if(!status.equals("") || !status.equals(" ") ) {
////	    	                        	text +=status+".";
////	    	                        	
//////	    	                            System.out.println(status);
////	    	                            flag=false;
////	    	                            break;
////    	                        	}
//    	                    }
//                        }
//                    
//                }
//                //System.out.println("\n");
//                
//                
//                
//            }
//            file.close();
        	
        	DataAccess db= new DataAccess();
        	
        	ResultSet rs=db.getResultSet(sql);
        	int q=0;
        	while (rs.next()) {
        		q++;
        		String status = rs.getString("tweets");
        		status=linkRemover(status);
        		if (!status.equals("")) {
                	if(status.charAt(status.length()-1)!='.' ) {
                		status +=".";
                	}

                	
                	text+=status;

            	}
        	}
        	
            ArrayList<MyMap> s=frequency(text);
            ArrayList<MyMap> ss=frequency(hashTag);
            

            matchWithHashtags(s, ss);
            System.out.println(q);
        	
//          System.out.println(hashTag);
            System.out.println(count);
//            System.out.println(hashTag);
            //frequency(hashTag);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    
 



	public static String linkRemover( String status) {
		status=status.replaceAll("\\s+", " ");
		boolean nounFlag=false;
		String ss="";
		try {
			String [] sentence= status.split("\\.");
			String[] list=sentence[0].split(" ");
			
		
		
		
		
    	
    	
		String temp="";
		for (int i=0;i<list.length;i++) {
			if(list[i].startsWith("http")||list[i].startsWith("@http")){
				list[i]="";
			}
			if(i!=0) {
				
				temp+=" "+list[i];
			}else {
				
				temp+=list[i];
			}
			
		}
		
		temp=temp.replace(",", "");
//		temp=temp.replace("#", "");
		temp=temp.replaceAll("[!,+,_,@,$,',:,]", "");
		list=temp.split(" ");
		for(int i=0;i<list.length;i++) {
			if(list[i].startsWith("#")) {
				list[i]=list[i].replaceAll("[\\.,…]", "");
				hashTag +=list[i]+".";
//				ss +=" "+list[i];
			}

				for(int m=0;m<noun.length;m++) {
					if(list[i].equals(noun[m])) {
						ss +=" "+list[i];
					}
				}
			try {
					IIndexWord idxWord = dict.getIndexWord (list[i], POS.NOUN );
					IWordID wordID = idxWord.getWordIDs().get(0) ;
					IWord word = dict . getWord ( wordID );
					ISynset synset = word . getSynset ();
					if(list[i].equals("i") || list[i].equals("be")||list[i].equals("he")|| list[i].equals("as") || list[i].equals("in") || list[i].equals("a")) {
					}else{	
						for( IWord w : synset . getWords ()) {
							list[i]=w. getLemma ();
							break;
						}
					
						nounFlag=true;
						if(i !=0) {
							ss +=" "+list[i];
						}else {
							ss +=list[i];
					}
				}
				
				count++;
			}catch(Exception e) {
				ss +="";
			}
			if (nounFlag==true) {
				
			
				try {
					IIndexWord idxWord = dict.getIndexWord (list[i], POS.VERB );
					IWordID wordID = idxWord.getWordIDs().get(0) ;
					IWord word = dict . getWord ( wordID );
					ISynset synset = word . getSynset ();
					if(!list[i].equals("i") || !list[i].equals("be")||!list[i].equals("he")|| !list[i].equals("as") || !list[i].equals("in")) {
						for( IWord w : synset . getWords ()) {
							//list[i]=w. getLemma ();
							break;
						}
						
						nounFlag=false;
						if(i !=0) {
							if(ss.endsWith(list[i])) {
								ss +="";
							}else {
								ss +=" "+list[i]+".";
							}
							
						}else {
							ss +="";
						}
					}
					
					count++;
				}catch(Exception e) {
					ss +="";
				}
			}else {
				ss="";
			}
			
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ss.startsWith(" ")) {
			ss=ss.replaceFirst(" ", "");
		}
		
		return ss;
	}
	
	
	public static ArrayList<MyMap> frequency( String text ) {
		ArrayList<MyMap> myMap= new ArrayList<>();
		String[] keys = text.split("\\.");
		
		for(int i=0;i<keys.length;i++) {
			keys[i].replaceAll("\\s+", " ");
			if(keys[i].startsWith(" ")) {
				keys[i]=keys[i].replaceFirst(" ", "");
			}
		}
		
//		for(int i=0;i<10;i++) {
//			System.out.println(keys[i]);
//		}
		
        String[] uniqueKeys;
        int count = 0;
        int c=0;
        //System.out.println(text);
        uniqueKeys = getUniqueKeys(keys);

        for(String key: uniqueKeys)
        {
        	if(c!=0) {
	            if(null == key)
	            {
	                break;
	            }           
	            for(String s : keys)
	            {
	            	
	                if(key.equals(s))
	                {
	                    count++;
	                }               
	            }
        	}else {
        		c++;
        	}
        	if(key.startsWith(" ")) {
    			key=key.replaceFirst(" ", "");
    		}
//        	System.out.println(key +" , "+count);
        	MyMap m= new MyMap(count, key);
            myMap.add(m);
            count=0;
        }
        
//        for(int i=0;i<myMap.size();i++) {
//        	System.out.println(myMap.get(i).status+" , "+myMap.get(i).count);
//        }
        return myMap;
	}
	
	private static String[] getUniqueKeys(String[] keys)
    {
        String[] uniqueKeys = new String[keys.length];

        uniqueKeys[0] = keys[0];
        int uniqueKeyIndex = 1;
        boolean keyAlreadyExists = false;

        for(int i=1; i<keys.length ; i++)
        {
            for(int j=0; j<=uniqueKeyIndex; j++)
            {
                if(keys[i].equals(uniqueKeys[j]))
                {
                    keyAlreadyExists = true;
                }
            }           

            if(!keyAlreadyExists)
            {
                uniqueKeys[uniqueKeyIndex] = keys[i];
                uniqueKeyIndex++;               
            }
            keyAlreadyExists = false;
        }       
        return uniqueKeys;
    }
	
	@SuppressWarnings("unchecked")
	public static void matchWithHashtags(ArrayList<MyMap> m, ArrayList<MyMap> x) {
		
		
		for (int i=0;i<m.size();i++) {
//			System.out.println(h.length);
			String st=m.get(i).status;
			String []sta=st.split(" ");
			for(int n=0;n<sta.length;n++) {
				
				for(int o=0;o<x.size();o++) {
					if(x.get(o).status.contains(sta[n])) {
						m.get(i).setCount(++m.get(i).count);
						break;
					}
					
				}
			}
		}
		
//		Collections.sort(m, new Comparator<MyMap>() {
//	        @Override
//	        public int compare(MyMap fruit2, MyMap fruit1)
//	        {
//
//	            return  fruit1.compareTo(fruit2);
//	        }
//
////			@Override
////			public int compare(MyMap o1, MyMap o2) {
////				// TODO Auto-generated method stub
////				return 0;
////			}
//	    });
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		DataAccess db= new DataAccess();
		
		for(int i=0;i<m.size();i++) {
			System.out.println(m.get(i).status+" ," +m.get(i).count);
			db.executeQuery("INSERT INTO `frequency` (`id`, `text`, `count`) VALUES (NULL, '"+m.get(i).status+"', '"+m.get(i).count+"')");
		}
		

		
		
		
		
	}
    
}
