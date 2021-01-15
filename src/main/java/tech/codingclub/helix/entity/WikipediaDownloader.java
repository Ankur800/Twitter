package tech.codingclub.helix.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.helix.global.HttpURLConnectionExample;

public class WikipediaDownloader {

    private String keyword;

    public WikipediaDownloader(){

    }

    public WikipediaDownloader(String keyword) {
        this.keyword = keyword;
    }

    public WikiResult getResult(){

        //1. get clean keyword !
        //2. get  the URL for Wikipedia
        //3. Make a GET request to wikipedia !
        //4. Parsing the useful results using JSOUP
        //5. Showing results !

        if(this.keyword == null || this.keyword.length() == 0){
            return null;
        }

        //STEP 1
        this.keyword = this.keyword.trim().replaceAll("[ ]", "_"); //"[ ]" it will replace all continuous spaces " " only one space

        //STEP 2
        String wikiUrl = getWikipediaUrlForQuery(this.keyword);

        String response = "";
        String imageUrl = "";

        //STEP 3
        try {
            String wikipediaResponseHTML = HttpURLConnectionExample.sendGet(wikiUrl);
            //System.out.println(wikipediaResponseHTML);

            //STEP 4
            Document document = Jsoup.parse(wikipediaResponseHTML, "https://en.wikipedia.org");
            Elements childElements = document.body().select(".mw-parser-output > *");

            int state = 0;  //For finding table tag in wikipedia before first paragraph

            for(Element childElement : childElements){
                //System.out.println(childElement.text());
                if(state == 0) {
                    if (childElement.tagName().equals("table")) {
                        state = 1;
                    }
                } else if (state == 1) {
                    if (childElement.tagName().equals("p")) {
                        state = 2;
                        response = childElement.text();
                        System.out.println(response);
                        break;
                    }
                }
            }

            //Fetching image URL
            try{
                imageUrl = document.body().select(".infobox img").get(0).attr("src");
            } catch (Exception e){
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        WikiResult wikiResult = new WikiResult(this.keyword, response, imageUrl);
        //PUSH RESULT INTO DATABASE

        return wikiResult;

    }

    private String getWikipediaUrlForQuery(String cleanKeyword) {
        return "https://en.wikipedia.org/wiki/" + cleanKeyword;
    }

}
