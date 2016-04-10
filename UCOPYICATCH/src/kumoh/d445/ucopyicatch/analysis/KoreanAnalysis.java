package kumoh.d445.ucopyicatch.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.snu.ids.ha.ma.Eojeol;
import org.snu.ids.ha.ma.Morpheme;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;

import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;
import kumoh.d445.ucopyicatch.database.BookReportDAO;

public class KoreanAnalysis {
	
	MorphemeAnalyzer ma;
	HashMap<String, Integer> tfList;

	public KoreanAnalysis()
	{
		ma = new MorphemeAnalyzer();
		tfList = new HashMap<String, Integer>();
	}
	
	public BookReportItemData divideBookReport(int bookcode, String title, String link, String text)
	{
		//BookReportData
		BookReportItemData itemData = new BookReportItemData();
		try {
			List ret = ma.analyze(text);
			List sentences = ma.divideToSentences(ret);
			
			itemData.setBookcode(bookcode);
			itemData.setContent(text);
			itemData.setLink(link);
			itemData.setTitle(title);
			itemData.getSentence().addAll(divideSentence(sentences));
			for(int i = 0 ; i < sentences.size() ; i++) {
				Sentence st = (Sentence) sentences.get(i);
				itemData.getContents().add(divideNoun(st));
			}
			
			//tf 적제
			for(int i = 0 ; i < itemData.getContents().size() ; i++) {
				for(int j = 0 ; j < itemData.getContents().get(i).size() ; j++) {
					itemData.getContents().get(i).get(j).setTF(tfList.get(itemData.getContents().get(i).get(j).getName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//df 누적
		TFIDF.accureDF(tfList);
		return itemData;
	}
	
	public ArrayList<String> divideSentence(List sentences)
	{
		ArrayList<String> sentenceList = new ArrayList<String>();
		try 
		{
			for(int i=0 ; i < sentences.size() ; i++) {
				sentenceList.add(sentences.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return sentenceList;
	}
	
	public ArrayList<Word> divideNoun(Sentence sentence) {
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		for(int i=0; i<sentence.size(); i++)
		{
			Eojeol ej = sentence.get(i);
			for(int j=0; j<ej.size(); j++)
			{
				Morpheme mp = ej.get(j);
				String tag = mp.getTag().substring(0, 1);
				if(tag.equals("N"))
				{
					String noun = mp.getString();
					Word word = new Word(noun);
					wordList.add(word);
					if(tfList.containsKey(noun))
					{
						int count = tfList.get(noun) + 1;
						tfList.put(noun, count);
					}
					else
						tfList.put(noun, 1);
				}
				
			}
		}
		return wordList;
	}
}
