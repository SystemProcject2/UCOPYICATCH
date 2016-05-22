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
import kumoh.d445.ucopyicatch.bookreport.Tag;
import kumoh.d445.ucopyicatch.bookreport.Word;

public class KoreanAnalysis {
	
	MorphemeAnalyzer ma;
	HashMap<String, Integer> tfList;
	HashMap<String, Integer> tagList;

	public KoreanAnalysis()
	{
		ma = new MorphemeAnalyzer();
		tfList = new HashMap<String, Integer>();
		tagList = new HashMap<String, Integer>();
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
				itemData.getTagWord().add(divideTag(st));
			}
			
			//tf 적제
			for(int i = 0 ; i < itemData.getContents().size() ; i++) {
				for(int j = 0 ; j < itemData.getContents().get(i).size() ; j++) {
					itemData.getContents().get(i).get(j).setTF(tfList.get(itemData.getContents().get(i).get(j).getName()));
				}
			}
			//tag tf
			for(int i = 0 ; i < itemData.getTagWord().size() ; i++) {
				for(int j = 0 ; j < itemData.getTagWord().get(i).size() ; j++) {
					itemData.getTagWord().get(i).get(j).setTagCnt(tagList.get(itemData.getTagWord().get(i).get(j).getTagName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//df 누적
		TFIDF.accureDF(tfList);
		return itemData;
	}
	
	public BookReportItemData divideSeparate(int bookcode, String title, String link, String text) {
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
						String[] str = st.getSentence().split(" ");
						ArrayList<String> temp=new ArrayList<String>();
						
						for(int j=0; j<str.length;j++) {
							if(str[j].length() == 1)
							{
								continue;
							}
							temp.add(str[j]);
						}
						itemData.getSeparateWord().add(temp);
						itemData.getTagWord().add(divideTag(st));
					}
					
					//tag tf
					for(int i = 0 ; i < itemData.getTagWord().size() ; i++) {
						for(int j = 0 ; j < itemData.getTagWord().get(i).size() ; j++) {
							itemData.getTagWord().get(i).get(j).setTagCnt(tagList.get(itemData.getTagWord().get(i).get(j).getTagName()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return itemData;
	}
	
	public ArrayList<String> divideSentence(List sentences)
	{
		ArrayList<String> sentenceList = new ArrayList<String>();
		try 
		{
			for(int i=0 ; i < sentences.size() ; i++) {
				sentenceList.add(((Sentence)sentences.get(i)).getSentence());
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
				String noun = mp.getString();
				if(noun.length() == 1 ||  mp.getTag().equals("NR"))
				{	
					continue;
				}
				if(tag.equals("N") || tag.equals("V"))
				{
					
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
	
	public ArrayList<Tag> divideTag(Sentence sentence) {
		ArrayList<Tag> tList = new ArrayList<Tag>();
		
		for(int i=0; i<sentence.size(); i++)
		{
			Eojeol ej = sentence.get(i);
			for(int j=0; j<ej.size(); j++)
			{
				Morpheme mp = ej.get(j);
				String name = mp.getTag().substring(0, 1);

				Tag tag = new Tag(name);
				tList.add(tag);
				if(tagList.containsKey(name))
				{
					int count = tagList.get(name) + 1;
					tagList.put(name, count);
				}
				else
					tagList.put(name, 1);
			}
		}
		return tList;
	}
}
