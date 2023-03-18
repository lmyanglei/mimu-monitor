package cn.mimukeji.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

import static cn.mimukeji.util.DateUtil.stringToDate;


public class CosineSimilarityUtil {
    
	/**
	 * 输入两段文本利用孜频率的余弦定理判断二者间的相似度
	 * 
	 * @param doc1,文本1
	 * @param doc2,文本2
	 * @return 相似度值
	 */
    public static double calculateTextSim(String doc1, String doc2) {
		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null
				&& doc2.trim().length() > 0) {
			
			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();
			
			//将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				if(isHanZi(d1)){
					int charIndex = getGB2312Id(d1);
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[0]++;
						}else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}

			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				if(isHanZi(d2)){
					int charIndex = getGB2312Id(d2);
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[1]++;
						}else {
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}
			
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqdoc1 = 0;
			double sqdoc2 = 0;
			double denominator = 0; 
			while(iterator.hasNext()){
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0]*c[1];
				sqdoc1 += c[0]*c[0];
				sqdoc2 += c[1]*c[1];
			}
			
			return denominator / Math.sqrt(sqdoc1*sqdoc2);
		} else {
			throw new NullPointerException("the Document is null or have not cahrs!!");
		}
    }

	/**
	 * 输入一个字符判断是否为中文汉字
	 * 
	 * @param ch，字符
	 * @return true为中文汉字，否则为false
	 */	
    public static boolean isHanZi(char ch) {
	return (ch >= 0x4E00 && ch <= 0x9FA5);    
    }	
	
	/**
	 * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
	 * 
	 * @param ch,输入的GB2312中文字符或者ASCII字符(128个)
	 * @return ch在GB2312中的位置，-1表示该字符不认识
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
				return -1;
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
			return (short) (b0 * 94 + b1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void computeTW(){
		class StringDouble{
			String alias;
			String name;
			Double score;
		}

		List<String> aliasList = FileUtil.readFileToList("D:\\workspace-company\\data\\doc\\NLPUtil\\place_name_alias.txt","utf-8");
		List<String> standList = FileUtil.readFileToList("D:\\workspace-company\\data\\doc\\NLPUtil\\place_name_standard.txt","utf-8");

		aliasList.forEach(item->{
			List<StringDouble> scoreList = new ArrayList<StringDouble>();
			standList.forEach(standItem->{
				StringDouble stringDouble = new StringDouble();
				stringDouble.alias=item;
				stringDouble.name=standItem;
				stringDouble.score=calculateTextSim(item,standItem);
				scoreList.add(stringDouble);
				Collections.sort(scoreList, new Comparator<StringDouble>(){
					@Override
					public int compare(StringDouble item1, StringDouble item2) {
						return item2.score.compareTo(item1.score);
					}
				});
			});
			System.out.println(scoreList.get(0).score+"|"+scoreList.get(0).alias+"|"+scoreList.get(0).name+"|"+scoreList.get(0).score+"|"+scoreList.get(0).alias+"|"+scoreList.get(0).name);
		});
	}
	public static void main(String[] args) {
		String s1="中国台湾省";
		String s2="伊拉克";
		System.out.println(CosineSimilarityUtil.calculateTextSim(s1, s2));
//		computeTW();
	}

}



