package drunkBot.memberFunctions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.*;

public class MemberFunctions {

    private HashMap<String, Integer> tiers;
    private ArrayList<Member> members;
    private int startingCredits;

    public MemberFunctions(){
        startingCredits = 100;
        //ranks = readRanks();
        members = new ArrayList<>();

        //read in user data
        ObjectInputStream in = null;
        File folder = new File("resources/users");
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                try {
                    String filename = "resources/users/" + fileEntry.getName();
                    in = new ObjectInputStream(new FileInputStream(filename));
                    members.add((Member)in.readObject());
                    in.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        //Timer to save users data
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveUsers();
            }
        }, 3000000, 6000000);

    }

    public boolean addMember(String name){
        if(isMember(name))
            return false;

        members.add(new Member(name, startingCredits));
        return true;
    }

    public boolean isMember(String name){
        for(Member member : members)
            if(member.getName().equalsIgnoreCase(name))
                return true;

        return false;
    }

    public boolean removeMember(String name){
        for(Member member : members){
            if(member.getName().equalsIgnoreCase(name)){
                members.remove(member);
                return true;
            }
        }
        return false;
    }

    public Member getMember(String name){
        for(Member member : members){
            if(member.getName().equals(name))
                return member;
        }
        return null;
    }

    public EmbedBuilder generateEmbededMessage(User user){
        EmbedBuilder eb = new EmbedBuilder();
        Member member = getMember(user.getName());

        eb.setTitle(user.getName());

        eb.setThumbnail(user.getAvatarUrl());

        eb.setColor(Color.red);
        eb.setColor(new Color(0xF40C0C));
        eb.setColor(new Color(255, 0, 54));

        eb.addField("Credits:", Integer.toString(member.getBalance()), true);
        eb.addField("Rank", "", true);
        eb.addBlankField(true);

        eb.addField("Wins:", Integer.toString(member.getWins()), true);
        eb.addField("Losses", Integer.toString(member.getLosses()), true);
        eb.addBlankField(true);
        eb.addField("Total Winnings", Integer.toString(member.getTotalWinnings()), true);
        eb.addField("Total Losses", Integer.toString(member.getTotalLosses()), true);
        eb.addBlankField(true);
        return eb;
    }

    public void saveUsers(){
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try{
            for(Member member : members){
                String filename = "resources/users/User - " + member.getName() + ".txt";
                fileOut = new FileOutputStream(filename);
                out = new ObjectOutputStream(fileOut);
                out.writeObject(member);
                out.flush();
                out.close();
                fileOut.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private HashMap<String, Integer> readTiers(){
        HashMap<String, Integer> ranks = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("resources/Tiers.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) jsonObject.get("Tiers");
        Iterator jsonItr = jsonArray.iterator();

        jsonItr.forEachRemaining(rnk -> {
            JSONObject obj = (JSONObject) rnk;
            String rank = (String)obj.get("Tier");
            Long cost = (Long)obj.get("Cost");
            ranks.put(rank, cost.intValue());
        });

        return ranks;
    }

    public HashMap<String, Integer> getTiers() {
        return tiers;
    }
}
