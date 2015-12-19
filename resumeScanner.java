import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class resumeScanner{
	 static Set<String> nounPhrases = new HashSet<>();
	 //static Set<String> adjectivePhrases = new HashSet<>();
	 static Set<String> verbPhrases = new HashSet<>();
	 
	 private static String jobdesc =""; 
	
	 public static void main(String[] args) throws Exception {
		  StandCoreNLP nlp = new StandCoreNLP();
		  
		  // phase verb and noun in jobdescription and do lemmatization
		  jobdesc = readFile(args[0]);
		  parserAction();
		  Set<String> descrip_noun = new HashSet<>();
		  Set<String> descrip_verb = new HashSet<>();
		  for(String s: nounPhrases){
			  descrip_noun.addAll(nlp.lemmatize(s));
		  }
		  for(String s: verbPhrases){
			  descrip_verb.addAll(nlp.lemmatize(s));
		  }
		  
		  //System.out.println(descrip_noun);
		  
		  // phase verb and noun in resume and do lemmatization
		  String resume = readFile(args[1]);
		  List<String> pool = nlp.lemmatize(resume);
		  
		  // calculate the freq of words in job description
		  HashMap<String, Integer> verbfreq = new HashMap<String, Integer>();
		  HashMap<String, Integer> nounfreq = new HashMap<String, Integer>();
		  for(String s: descrip_verb){
			  verbfreq.put(s,0);
		  }
		  for(String s: descrip_noun){
			  nounfreq.put(s,0);
		  }
		  for(String s: descrip_verb){
			  for(String m: pool){
				  if(m.contains(s)){
					  if(verbfreq.containsKey(s)){
						  verbfreq.replace(s,verbfreq.get(s) + 1);
					  }
				  }
			  }
		  }  
		  for(String s: descrip_noun){
			  for(String m: pool){
				  if(m.contains(s)){
					  if(nounfreq.containsKey(s)){
						  nounfreq.replace(s,nounfreq.get(s) + 1);
					  }
				  }
			  }
		  }
		  
		  
		  // output to print
		  Map<String, Integer> sorted_v = new HashMap<String, Integer>(verbfreq);
		  Map<String, Integer> sorted_n = new HashMap<String, Integer>(nounfreq);
		  sorted_v = MapUtil.sortByValue( sorted_v );
		  sorted_n = MapUtil.sortByValue( sorted_n );
		  System.out.println("\n"+"====== Verb =====");
		  for (String name: sorted_v.keySet()){
	            String key =name.toString();
	            if(key.length()<2){
	            	continue;
	            }
	            String value = sorted_v.get(name).toString();  
	            System.out.println(key + " " + value);  
		  }
		  System.out.println("\n"+"====== Noun =====");
		  for (String name: sorted_n.keySet()){
	            String key =name.toString();
	            if(key.length()<2){
	            	continue;
	            }
	            String value = sorted_n.get(name).toString();  
	            System.out.println(key + " " + value);  
		  }
		  
	 }
	
	 // phase noun and verb
	 public static void getNounPhrases(Parse p) {
	  if (p.getType().equals("NN") || p.getType().equals("NNS") ||  p.getType().equals("NNP") || p.getType().equals("NNPS")) {
	          nounPhrases.add(p.getCoveredText());
	  }
//	  if (p.getType().equals("JJ") || p.getType().equals("JJR") || p.getType().equals("JJS")) {
//	      adjectivePhrases.add(p.getCoveredText());
//	  }
	  if (p.getType().equals("VB") || p.getType().equals("VBP") || p.getType().equals("VBG")|| p.getType().equals("VBD") || p.getType().equals("VBN")) {
	      verbPhrases.add(p.getCoveredText());
	   }   
	  for (Parse child : p.getChildren()) {
	          getNounPhrases(child);
	  }
	 }
	 
	 // search the noun and verb
	 public static void parserAction() throws Exception {
		 InputStream is = new FileInputStream("en-parser-chunking.bin");
		 ParserModel model = new ParserModel(is);
		 Parser parser = ParserFactory.create(model);
		 Parse topParses[] = ParserTool.parseLine(jobdesc, parser, 1);
		 for (Parse p : topParses){
		  //p.show();
		  getNounPhrases(p);
		 }
	 }
		 
	// read job description and resume
	 public static String readFile(String fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
	}
	 	 
}