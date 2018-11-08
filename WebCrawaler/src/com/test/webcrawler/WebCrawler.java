package com.test.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	
	public static Queue<String> queue = new LinkedList<>();
    public static Set<String> marked = new HashSet<>();
	public static String regex="http[s]*://(\\w+\\.)*(\\w+)";
   
	public static void testclawler(String root) throws IOException {
	
		queue.add(root);
		
		while(!queue.isEmpty()) {
		
			String crawlerurl = queue.poll();
			System.out.println("site");
			
			if(marked.size() > 100)
				return ;
			
			boolean ok = false;
			URL url = null;
			BufferedReader br = null;
			
			while(!ok) {
				try {
					url = new URL(crawlerurl);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ok = true;
				} catch(MalformedURLException e) {
					System.out.println("malformed URL :" + crawlerurl);
					crawlerurl = queue.poll();
					ok = false;
				} catch(IOException e) {
					System.out.println("IOEXCeption URL :" + crawlerurl);
					crawlerurl = queue.poll();
					ok = false;
			    } 				
    	     }
			
			StringBuilder sb = new StringBuilder();
		
			while((crawlerurl = br.readLine()) != null) {
				sb.append(crawlerurl);
				
			}
			crawlerurl = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(crawlerurl);
			
			while(matcher.find()) {
				String s = matcher.group();
				if(!marked.contains(s)) {
					marked.add(s);
					System.out.println("Site added for crawle :" + s);
					queue.add(s);
				}
			}
    	}
	}		
    public static void showResults() {
    	System.out.println("\n\nResult : ");
    	System.out.println("Web site crawled:" + marked.size() +"\n");
    	
    	for(String s : marked) {
    		System.out.println("* "+ s);
    	}
    }
    
    public static void main(String[] args) {
    	try {
    		testclawler("http://www.google.com");
    		showResults();
    	}catch(IOException e) {
    		
    	}
    }
}
