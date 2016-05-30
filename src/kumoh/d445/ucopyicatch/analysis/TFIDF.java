package kumoh.d445.ucopyicatch.analysis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class TFIDF {
	static HashMap<String, Integer> dfList = new HashMap<String, Integer>();
	
	public static double tfIdf(int tf, int df, int total) {
		double result = tf*Math.log10((double)total/df);
		return result;
	}
	
	public static void accureDF(HashMap<String, Integer> list) {
		Set<Entry<String, Integer>> set = list.entrySet();
		Iterator<Entry<String, Integer>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
			String noun = e.getKey();
		
			if(dfList.containsKey(noun))
			{
				int count = dfList.get(noun);
				count++;
				dfList.put(noun, count);
			}
			else
				dfList.put(noun, 1);
		}
	}
	
	public static HashMap<String, Integer> getDfList()
	{
		return dfList;
	}
	
	public static void clearDF() {
		dfList.clear();
	}
}
