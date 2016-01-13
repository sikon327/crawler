package crawler;
import java.util.regex.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * This program takes a url as a command line argument,
 * then crawls the associated website for emails.
 * It checks all accessible webpages and returns a list
 * of the returned emails to the user.
 * The work of actually reading the webpages will be done
 * by a second class called "Reader.java"
 * @author jackanderson
 * 
 */
public class Crawler {
	public static void main(String[] args) {
		Set<String> pagesVisited = new HashSet<String>();
		List<String> pagesToVisit = new LinkedList<String>();
		Set<String> emailsFound = new HashSet<String>();
		/*pagesVisited keeps track of pages we've already crawled
		to avoid any sort of infinite loop from happening.
		pagesToVisit keeps track of hyperlinks found on a page
		to create readers for each.
		emailsFound is a list to which each reader appends whatever
		emails it found in its designated page.
		*/
		/*Pattern email = Pattern.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
		try {
			URL url = new URL(args[0]);
		} catch (MalformedURLException e) {
			System.err.println("Usage: java Crawler [URL]");
		}*/
		String currentUrl = args[0];
		Reader reader;
		do{
			if(currentUrl.contains(args[0])){
				reader = new Reader(currentUrl);
				reader.crawl();
				emailsFound.addAll(reader.emailsFound);
				pagesToVisit.addAll(reader.links);
			}
			//we check our list for pages we haven't visited yet
			do{
				currentUrl = pagesToVisit.remove(0);
			} while(pagesVisited.contains(currentUrl));
			pagesVisited.add(currentUrl);
		}while(!pagesToVisit.isEmpty() && currentUrl != "");
		//once we run out of pages to visit we output our findings
		if(emailsFound.isEmpty()){
			System.out.println("No emails found. Sorry!");
		}
		else{
			System.out.println("Emails found:");
			for(String email : emailsFound) {
				System.out.println(email);
			}
		}
	}
	
	
}
