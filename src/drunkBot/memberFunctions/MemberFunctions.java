package drunkBot.memberFunctions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MemberFunctions {

    private ArrayList<Member> members;
    private int startingCredits;

    public MemberFunctions(){
        startingCredits = 100;
        members = new ArrayList<>();

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
        Member member = null;
        for(Member m : members){
            if(m.getName().equalsIgnoreCase(user.getName())){
                member = m;
                break;
            }
        }

        eb.setTitle(user.getName());

        eb.setThumbnail(user.getAvatarUrl());

        eb.setColor(Color.red);
        eb.setColor(new Color(0xF40C0C));
        eb.setColor(new Color(255, 0, 54));

        eb.addField("Credits:", Integer.toString(member.getBalance()), false);
        eb.addBlankField(false);

        return  eb;
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
        System.out.println("Success");
    }
}
