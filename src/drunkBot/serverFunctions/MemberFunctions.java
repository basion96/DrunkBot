package drunkBot.serverFunctions;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.*;
import java.util.*;

public class MemberFunctions {

    private ArrayList<Member> members;
    private int startingCredits;

    public MemberFunctions(){
        startingCredits = 100;
        members = new ArrayList<>();

        //read in user data
        ObjectInputStream in = null;
        File folder = new File("resources/users");
        if(folder.exists()){
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
        }

        //Timer to save users data
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveUsers(null);
            }
        }, 3000000, 6000000);

    }

    public boolean addMember(String name){
        if(isMember(name))
            return false;

        Member member = new Member(name, startingCredits);
        members.add(member);
        saveUsers(member);
        return true;
    }

    public ArrayList<Member> getMembers(){
        return members;
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

    public EmbedBuilder generateEmbeddedMessage(net.dv8tion.jda.api.entities.Member user){
        EmbedBuilder eb = new EmbedBuilder();
        Member member = getMember(user.getEffectiveName());

        eb.setTitle(user.getEffectiveName());

        eb.setThumbnail(user.getUser().getAvatarUrl());

        eb.setColor(Color.red);

        eb.addField("Credits:", Integer.toString(member.getBalance()), true);
        eb.addBlankField(true);

        eb.addField("Wins:", Integer.toString(member.getWins()), true);
        eb.addField("Losses", Integer.toString(member.getLosses()), true);
        eb.addBlankField(true);

        eb.addField("Total Winnings", Integer.toString(member.getTotalWinnings()), true);
        eb.addField("Total Losses", Integer.toString(member.getTotalLosses()), true);
        eb.addBlankField(true);

        eb.addField("Biggest win:", Integer.toString((member.getBiggestWin())), true);
        eb.addField("Times dole used:", Integer.toString(member.getDoleUses()), true);
        eb.addBlankField(true);

        eb.addField("Pub Rank:", Integer.toString(getMember(user.getEffectiveName()).getPubRank()), true);

        return eb;
    }

    public void saveUsers(Member m){
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try{
            if(m==null){
                for(Member member : members){
                    String filename = "resources/users/user_" + member.getName() + ".txt";
                    fileOut = new FileOutputStream(filename);
                    out = new ObjectOutputStream(fileOut);
                    out.writeObject(member);
                    out.flush();
                    out.close();
                    fileOut.close();
                }
            }
            else{
                String filename = "resources/users/user_" + m.getName() + ".txt";
                fileOut = new FileOutputStream(filename);
                out = new ObjectOutputStream(fileOut);
                out.writeObject(m);
                out.flush();
                out.close();
                fileOut.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
