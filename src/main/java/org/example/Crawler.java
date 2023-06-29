package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.SQLException;


import java.io.IOException;
import java.util.HashSet;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Crawler {

    HashSet<String>urlSet;
    int Max_Depth=2;
    Crawler(){
        urlSet=new HashSet<String>();
    }

    public void getPageTextAndLinks(String url,int depth){
        if (urlSet.contains(url)) {
            return;
        }
        if (depth>=Max_Depth) {
            return;
        }
        if(urlSet.add(url)){
            System.out.println(url);
        }
         depth++;

       try {
           // converting html object to java object \
           Document document = Jsoup.connect(url).timeout(5000).get();
           // sent the document to the indexer for storing important data in web page to the database
           Indexer indexer=new Indexer(document,url);
           System.out.println(document.title());
           // ww get the all the links whihc are linked to url, in a chain form and get saved in elemenst object
           Elements availableLinksOnPage = document.select("a[href]");

           // now we traverse through each link, until we reach the max depth of links declared
           for (Element currentlink : availableLinksOnPage) {
               getPageTextAndLinks(currentlink.attr("abs:href"), depth);
           }
       }
       catch (IOException ioException){
           ioException.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException {
           // create a crawler object , and go to function for traversing through links possible
        Crawler crawler=new Crawler();
        crawler.getPageTextAndLinks("https://www.javatpoint.com",0);
        }
}
