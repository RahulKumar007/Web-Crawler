/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;
import java.io.IOException;
import java.sql.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
 
       
        
public class Crawler  {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
      Document doc = Jsoup.connect("https://www.flipkart.com").get();
      Elements  links = doc.select("a[href]");
        crl(doc,links);
            
    
    }

    private static void crl(Document doc, Elements lnks) {
             try{
        //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler","root","ramrahu123#");
                Statement st = con.createStatement();
                  lnks = doc.select("a[href]");
                for (Element link : lnks){
                    
                    String s1 = link.attr("href");
                    if(s1.matches(".*flipkart.com.*"))
                    {
                        String sql ="insert into internal values('"+s1+"')";
                        
                         st.executeUpdate(sql);
                        Document doc1 = Jsoup.connect(s1).get();
                        Elements lnk = doc1.select("a[href]");
                        crl(doc1, lnk);
                    }
                    
                    // st.setString(1,s1);
                    //st.executeUpdate();
                    else {
                        String sql ="insert into external values('"+s1+"')";
                        
                        st.executeUpdate(sql);
                        Document doc1 = Jsoup.connect(s1).get();
                        Elements lnk = doc1.select("a[href]");
                        crl(doc1, lnk);
                       
                                
                    }
                    
                } 
                con.close(); 
             }
        catch(Exception e){
        System.out.println(e);
    }  
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
        
}

