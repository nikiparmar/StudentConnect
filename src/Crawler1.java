import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Crawler1{

    private static String base = "http://stupidsid.com";
    BufferedWriter bf = null;

    public static void main(String[] args) throws IOException{
        Crawler1 crawl = new Crawler1();
        
        crawl.bf = new BufferedWriter(new FileWriter (new File ("/home/niki/Desktop/Student Connect/Data Collection/stupidsid1.txt")));
        
        
      //  crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-cs-and-mis");
       // crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-ee-ece-telecom");
       // crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-mech-and-aero");
      //  crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-biomed-biotech-and-chemical");
      //  crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-civil-structural-and-material-science");
      //  crawl.getForum("http://stupidsid.com/forum/profile-evaluation-spring-fall-2015-ms-in-financial-industrial-and-engg-management");
      //  crawl.getForum("http://stupidsid.com/forum/ms-in-cs-and-mis-profile-evaluation-spring-fall-2014");
        //crawl.getForum("http://stupidsid.com/forum/ms-in-ee-ece-telecom-profile-evaluation-spring-fall-2014");
     //   crawl.getForum("http://stupidsid.com/forum/ms-in-mech-and-aero-profile-evaluation-spring-fall-2014");
     //   crawl.getForum("http://stupidsid.com/forum/biomed-biotech-chemical-spring-fall-2014-profile-evaluation");
     //   crawl.getForum("http://stupidsid.com/forum/ms-in-financial-industrial-and-engg-management-profile-evaluation-spring-fall-2014");
   
    //    crawl.getForum("http://stupidsid.com/forum/ms-in-civil-structural-and-material-science-profile-evaluation-spring-fall-2014");
       // crawl.getForum("http://stupidsid.com/forum/ms-in-usa-universities-university-comparer");
       // crawl.getForum("http://stupidsid.com/forum/statement-of-purpose-sop");
        crawl.getForum("http://stupidsid.com/forum/letters-of-recommendation");
      //  crawl.getForum("http://stupidsid.com/forum/application-deadlines");
       // crawl.getForum("http://stupidsid.com/forum/application-queries-and-documents");
       
      //  crawl.getForum("http://stupidsid.com/forum/finances-documents-and-certificates");
        crawl.getForum("http://stupidsid.com/forum/work-experience-internship-activities");
     //   crawl.getForum("http://stupidsid.com/forum/gre-toefl-etc");
      //  crawl.getForum("http://stupidsid.com/forum/visa-interview-and-experiences");
      //  crawl.getForum("http://stupidsid.com/forum/finances-and-education-loans");
        crawl.getForum("http://stupidsid.com/forum/ms-in-us-universities-profile-evaluation-for-spring-fall-2013");
        crawl.getForum("http://stupidsid.com/forum/ms-in-us-universities-admitsrejects");
       
       crawl.bf.close();
       
    }

    public void getForum(String url){
        String new_url;
        int no_ques = 0, no_pages = 1;
        try{
        Document doc = Jsoup.connect(url).timeout(10*1000).get();
        String title = doc.getElementsByTag("title").first().text();
        if(doc.getElementsByClass("kpagination")!= null && doc.getElementsByClass("kpagination").size() > 0)
        	no_pages = Integer.parseInt(doc.getElementsByClass("kpagination").first().getElementsByTag("a").last().text());
        
        System.out.println(no_pages);
        for(int i =0;i<no_pages;i++){
            new_url = url + "?start=" + no_ques;
        //    System.out.println(new_url);
            no_ques  += crawlDiscussion(new_url, title);
        }
       
        System.out.println("Done");
        }catch(Exception e){
            System.out.println("Caught excpeiton in the page" + url);
        }
       
    }

    public int crawlDiscussion(String url, String title){
        Document doc = null;
        JSONObject record = new JSONObject();
        int no_posts  = 0;
        record.put("head", "");
        record.put("ques", "");
        record.put("ans", "");
        record.put("replies", 0);
        record.put("views", "");
        record.put("tag", title);

        int size  = 0, i =0,j=0 , no_ans =0, replies = 0;
        try {
            doc = Jsoup.connect(url).timeout(10*1000).get();
            Elements posts = doc.getElementById("kflattable").getElementsByTag("tr");
            size = posts.size();
            //System.out.println(size);
            for(i=0;i<size;i++){
                Element post = posts.get(i);
                replies = Integer.parseInt(post.getElementsByTag("td").first().getElementsByTag("strong").text());
                if(replies == 0)
                    continue;
               
                record.put("head",post.select("div.ktopic-title-cover").text());
                record.put("replies",replies);
                record.put("time",post.select("span.ktopic-posted-time").attr("title"));
                record.put("views",post.select("span.ktopic-views-number").text());
                String ans_url = post.getElementsByClass("ktopic-title-cover").get(0).getElementsByTag("a").attr("abs:href");
                Document forum = Jsoup.connect(ans_url).timeout(10*1000).get();
                JSONArray json_ans = new JSONArray();
                Elements answers = forum.select("div.kmsgtext");
                record.put("ques",answers.get(0).text());

                no_posts  = getAnswersPage(ans_url, json_ans, 1);
                int window = no_posts;
                //String new_urls = ans_url.substring(0, ans_url.length() - 12);
               
                int next_start  = no_posts;
                while(no_posts < replies ){

                    String l = ans_url + "?start=" + next_start;
                    //System.out.println(l);
                    no_posts += getAnswersPage(l, json_ans,0);
                    next_start += window;

                }
                record.put("ans", json_ans);
               // System.out.println(record.toString());
                bf.write(record.toString().replace("\n", " ") + "\n");
            }

            bf.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        return size;
    }

    public int getAnswersPage(String ans_url, JSONArray json_ans, int start){
        int j,no_ans;
        //System.out.println(ans_url);
        try{
            Document forum = Jsoup.connect(ans_url).timeout(10*1000).get();
            Elements answers = forum.select("div.kmsgtext");
            no_ans = answers.size();

            for(j=start;j<no_ans;j++){
                json_ans.put(answers.get(j).text());
            }
        }catch(Exception e){
        //    System.out.println("Url does not exist");
            return 0;
        }
        return no_ans;
    }

}