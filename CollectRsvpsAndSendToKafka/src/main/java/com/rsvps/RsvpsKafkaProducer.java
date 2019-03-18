package com.rsvps;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

@Component
@EnableBinding(Source.class)
public class RsvpsKafkaProducer {
  
    private static final int SENDING_MESSAGE_TIMEOUT_MS = 10000;

    private final Source source;

    public RsvpsKafkaProducer(Source source) {
        this.source = source;
    }

    public void readMeetupMessage() throws Exception{

        HttpURLConnection con = null;
        try {
            URL url = new URL("https://stream.meetup.com/2/rsvps");

            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("Status is " + status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                //content.append(inputLine);
                System.out.println("Incoming json "+inputLine);
                            source.output()
                   .send(MessageBuilder.withPayload(inputLine)
                                    .build(),
                            SENDING_MESSAGE_TIMEOUT_MS) ;

            }
            in.close();
        }
        finally {
            if(con!= null)
                con.disconnect();
        }

    }

    public void sendRsvpMessage() {

        try {
            readMeetupMessage();
        } catch (Exception e) {
            System.out.println("Error reading Meetup ");
            e.printStackTrace();
        }

        //JSON parser object to parse read file
//        JSONParser jsonParser = new JSONParser();
//
//        try (FileReader reader = new FileReader("/Users/jsarl/SandBox/Kafka/04] Code/meetrsvp1.json"))
//        {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//
//            JSONArray rsvpList = (JSONArray) obj;
//            System.out.println(rsvpList);
//
//            //Iterate over employee array
//            rsvpList.forEach( rsvp -> source.output()
//                    .send(MessageBuilder.withPayload(rsvp)
//                                    .build(),
//                            SENDING_MESSAGE_TIMEOUT_MS) );
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (org.json.simple.parser.ParseException e) {
//            e.printStackTrace();
//        }

    }
}
