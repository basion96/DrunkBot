package drunkBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * TODO:
 *  -once game has started, lock to text channel that it was started in
 *
 */

public class KingOfBeersCommand extends Command {
    @Override
    public String description() {
        return "Have a game of King Of Beers and get a bit tipsy (or fuck eyed)";
    }

    private boolean gameInProgress;
    private HashMap<String, String> cardURLs;
    private String channelInUse;
    private ArrayList<String> listOfCards;

    public KingOfBeersCommand(){
        gameInProgress = false;
        this.cardURLs = getCardURLs();
        listOfCards = new ArrayList<>(cardURLs.keySet());

        channelInUse = "";
    }

    @Override
    public void runCommand(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentDisplay();

        //sends the games info
        if(msg.equals("!kob info") || msg.equals("!kob")){
            event.getChannel().sendMessage("Welcome to king of beers, use !kob start to start a new game or !kob reset to reset the current game").queue();
            return;
        }

        //resets the game
        if(msg.equals("!kob reset")){
            resetGame();
            event.getChannel().sendMessage("Game has been reset.").queue();
            return;
        }

        //starts a new king of beers game
        if(msg.equals("!kob start") && !gameInProgress){
            event.getChannel().sendMessage("Time to get get fuck eyed! use !kob draw to draw next card").queue();
            channelInUse = event.getChannel().getId();
            gameInProgress = true;
            event.getChannel().sendMessage(getNextCard(event.getAuthor().getName()).build()).queue();
        }
        else if(msg.equals("!kob start") && gameInProgress){
            event.getChannel().sendMessage("Game's already in progress in " + event.getJDA().getTextChannelById(channelInUse).getName()).queue();
            return;
        }

        //Commands for when a game is in progress

        if(msg.equals("!kob draw")){
            if(listOfCards.size() != 0){
                event.getChannel().sendMessage(getNextCard(event.getAuthor().getName()).build()).queue();
            }
            else{
                event.getChannel().sendMessage("Last card has been drawn. reset game using !kob reset").queue();
            }
        }
    }

    private EmbedBuilder getNextCard(String name){
        String card = listOfCards.remove(new Random().nextInt(listOfCards.size()));
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(card);
        eb.setImage(cardURLs.get(card));
        eb.setDescription("Card pulled by: " + name);
        getCardDetails(eb, card);
        eb.setFooter("King of Beers: " + listOfCards.size() + " cards remaining.");
        eb.setColor(Color.ORANGE);
        return eb;
    }

    private void getCardDetails(EmbedBuilder eb, String card){
        String action="", desc="";

        if(card.contains("Ace")){
            action = "Waterfall";
            desc = "Every player begins drinking, and no one can stop until the player before them does";
        }
        else if(card.contains("King")){
            action = "Ruler";
            desc = "Make a rule that everyone must follow until the next King is drawn";
        }
        else if(card.contains("Queen")){
            action = "Questions";
            desc = "The person that picks a queen poses a question to anyone in the game. That person has to respond to the question with another question. " +
                    "This goes on until someone does not respond with a question and they drink. ";
        }
        else if(card.contains("Jack")){
            action = "Rule";
            desc = "Whoever picks the jack comes up with a rule and whoever breaks the rule throughout the game drinks.";
        }
        else if(card.contains("10")){
            action = "Categories";
            desc = "The person that picks a ten has to come up with a category (liquors, countries, etc..) and the person to his/her right has to say something in the category. " +
                    "When someone can't come up with something in that category, he/she drinks. ";
        }
        else if(card.contains("9")){
            action = "Rhyme";
            desc = "Whoever picks the nine says a word. The person to the right has to say a word that rhymes with it; this goes on until someone cannot think of a rhyme. " +
                    "That person has to take a drink. ";
        }
        else if(card.contains("8")){
            action = "Mate";
            desc = " The person that picks an eight, picks another player to be their 'mate'. When one of the 'mates' drinks, the other has to as well. ";
        }
        else if(card.contains("7")){
            action = "Heaven";
            desc = "When a seven is picked, every player must raise their hand to 'heaven'. The last player to do so drinks.";
        }
        else if(card.contains("6")){
            action = "Dicks";
            desc = "All the guys take a drink. ";
        }
        else if(card.contains("5")){
            action = "Never have I ever";
            desc = "The person who picked the five starts with saying something they have never done, if you have done it, you put a finger down. " +
                    "The first person to put down all their fingers, loses and drinks. ";
        }
        else if(card.contains("4")){
            action = "Whores";
            desc = "All the ladies drink. ";
        }
        else if(card.contains("3")){
            action = "Me";
            desc = "If you pick a three, you are drinking";
        }
        else if(card.contains("2")){
            action = "You";
            desc = "The person who picks two, points to another person and that person drinks. ";
        }

        eb.addField(("**"+action+"**"), desc, false);
    }

    private void resetGame(){
        listOfCards = new ArrayList<>(cardURLs.keySet());
        gameInProgress = false;
        channelInUse = "";
    }

    private HashMap<String, String> getCardURLs(){
        HashMap<String, String> deck = new HashMap<>();
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("resources/deckOfCards.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("deck");
        Iterator deckItr = ja.iterator();
        deckItr.forEachRemaining(card -> {
            JSONObject cardObject = (JSONObject) card;
            deck.put((String)cardObject.get("card"), (String)cardObject.get("URL"));
        });

        return deck;
    }
}
