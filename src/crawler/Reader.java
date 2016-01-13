package crawler;
import java.util.regex.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author jackanderson
 * This class will visit a URL designated by the Crawler,
 * and check it for email addresses as well as hyperlinks.
 */

public class Reader {
	String url;
	Set<String> emailsFound = new HashSet<String>();
	Pattern p = Pattern.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
	List<String> links = new LinkedList<String>();
	Document htmlDocument;
	public Reader(String url){
		this.url = url;
		
	}
	
	public void crawl(){
		try{
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			Elements linksOnPage = htmlDocument.select("a[href]");
			for(Element link : linksOnPage){
				this.links.add(link.absUrl("href"));
			}
			findEmail();
		}
		catch(IOException ioe){
			System.err.println("Error in HTTP request: " + ioe);
		}
	}
	
	public void findEmail(){
		Matcher m = p.matcher(htmlDocument.text());
		while(m.find()) {
			emailsFound.add(m.group());
		}
	}
}
