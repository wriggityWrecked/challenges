import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * https://www.reddit.com/r/dailyprogrammer/comments/8ewq2e/20180425_challenge_358_intermediate_everyones_a/
 * @author bugatu
 */
public class Challenge358 {

    public static final String WIN_KEY = "wins";
    public static final String LOSE_KEY = "losses";

    /**
     * https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
     * 
     * @param url
     * @return 
     */
    public static List<String> downloadData(String url) {
        
        //TODO think about processing the line in the loop, reduces memory cost

        List<String> data = new ArrayList<>();
        try {
            URL u = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(u.openStream()));
            String line;
            boolean startParsing = false;
            
            while ((line = bufferedReader.readLine()) != null) {
                
                //look for <pre> tag to start
                if(line.contains("<pre>")) {
                    startParsing = true;
                    data.add(line.split("<pre>")[1]);
                }
                //look for empty, or Games</pre> to stop
                else if(line.isEmpty() || line.contains("</pre>") || line.contains("Games:")) {
                    break;//needs to come before startParsing flag
                } else if(startParsing) {
                    data.add(line);
                }
            }
        } catch (MalformedURLException ex) {
            //todo
        } catch (IOException ex) {
            //todo
        }
        return data;
    }
    
    /**
     * Parse a line from the data set: https://www.masseyratings.com/scores.php?s=298892&sub=12801&all=1
     * 
     * @param line
     * @param map
     * @param transitive 
     */
    public static void parseLine(String line, HashMap<String, HashMap<String, HashSet<String>>> map, HashSet<String>transitive) {
        //2017-11-15  E Texas Bap              89 @Centenary                81           
        line = line.trim().replaceAll("\\s+", " ").replaceAll("@",""); //replace all spaces with a single space
        String[] split = line.split(" ");
        
        String team1 = "";
        String team2 = "";
        int score1 = -1;
        int score2 = -1;
        
        // TODO sit down and learn regex....
        //index 0 is date
        for(int i=1; i<split.length; i++) {
            //if not a number
            if(!split[i].matches("\\d+")) {

                if(score1 == -1) {
                    team1 += split[i];
                } else {
                    team2 += split[i];
                }
            } else {
                if(score1 == -1)
                    score1 = Integer.parseInt(split[i]);
                else {
                    score2 = Integer.parseInt(split[i]);
                    break; //done parsing, don't care about anything after last score
                }
            }
        }
        
        if(score1 > score2) {
            addToMap(map, team1, team2);
        } else if(score1 > score2) {
            addToMap(map, team2, team1);
        } 
        //tie don't care
    }   
    
    /**
     * 
     * @param map
     * @param winTeam
     * @param loseTeam 
     */
    public static void addToMap(HashMap<String, HashMap<String, HashSet<String>>> map, String winTeam, String loseTeam) {
        
        HashMap<String, HashSet<String>> subMap = map.computeIfAbsent(winTeam, k -> new HashMap<>());
        HashSet<String> set = subMap.computeIfAbsent(WIN_KEY, k -> new HashSet<>());
        set.add(loseTeam);
        
        HashMap<String, HashSet<String>>  subMap2 = map.computeIfAbsent(loseTeam, k -> new HashMap<>());
        HashSet<String> set2 = subMap2.computeIfAbsent(LOSE_KEY, k -> new HashSet<>());
        set2.add(winTeam);
        
    }
    
    /**
     * Brute force method.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String champion = "Villanova"; //todo command line
        
        HashMap<String, HashMap<String, HashSet<String>>> map = new HashMap<>();
        List<String> list = downloadData("https://www.masseyratings.com/scores.php?s=298892&sub=12801&all=1");
        
        HashSet<String> transitive = new HashSet<>();
        transitive.add(champion);
        
        for(String s: list) {
            parseLine(s, map, transitive);
            //should think about parsing data here to save time
        }
        
        //this is brute force after collecting all the data starting with the current champion
        //get all the teams the champion lost to
        addTransitiveChampion(champion, map, transitive);
        System.out.println(transitive.size() - 1); //exclude the current champion, (outputs 1191)
        //see thread https://www.reddit.com/r/dailyprogrammer/comments/8ewq2e/20180425_challenge_358_intermediate_everyones_a/e1ksguv/
    }
    
    /**
     * Recursive method to add transitive winners. 
     * 
     * @param team
     * @param map
     * @param transitive 
     */
    public static void addTransitiveChampion(String team, HashMap<String, HashMap<String, HashSet<String>>> map, HashSet<String> transitive) {
        
        //get all the teams the current champion lost to
        HashSet<String> nested = map.get(team).get(LOSE_KEY);

        //some teams don't have full win / loss records
        if(nested == null) {
            return;
        }

        for(String newTeam: nested) {
            if(!transitive.contains(newTeam)) {
                transitive.add(newTeam);
                addTransitiveChampion(newTeam, map, transitive);
            } 
            //else do nothing
        }
    }
    
}
