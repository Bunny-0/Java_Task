package com.example.JournalService.Service;


import com.example.JournalService.Entity.Event;
import com.example.JournalService.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {


    @Autowired
    EventRepository eventRepository;

    @KafkaListener(topics = "create_user",groupId = "group")
    public Event addEvents(String event){
        Event event1=new Event();
        event1.setEventMessage(event);
        event1.setEventTime(LocalDateTime.now());
        eventRepository.save(event1);
        return event1;

    }

    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

}
