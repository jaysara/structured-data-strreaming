package com.rsvps.controller;

import com.mongodb.MongoClient;
import com.rsvps.model.MeetupRSVP;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RsvpController {
    
    private final ReactiveMongoTemplate mongoTemplate;

    //private final MongoTemplate blockingMongoTemplate;
   
    public RsvpController(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //@CrossOrigin(origins = { "http://localhost:8080" }, maxAge = 6000)
    @GetMapping(value = "/meetupRsvps", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MeetupRSVP> meetupRsvps() {                        
        return mongoTemplate.tail(
                new Query(), MeetupRSVP.class).share();
    }

    @PostMapping("/meetupRsvps")
    public void insertRspv(@RequestBody MeetupRSVP meetupRSVP)
    {
        MongoTemplate template = new MongoTemplate(new MongoClient(),"meetupDB");
        System.out.println("Incoming string "+ meetupRSVP.getVenue().getVenue_name());
      //  Mono<MeetupRSVP> meetupRSVPMono =
        template.insert(meetupRSVP);
    }
}
