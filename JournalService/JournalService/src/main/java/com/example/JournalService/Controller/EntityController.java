package com.example.JournalService.Controller;

import com.example.JournalService.Entity.Event;
import com.example.JournalService.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class EntityController {

    @Autowired
    EventService eventService;

    @GetMapping(value = "events")
    public List<Event> getEvent(){
        return eventService.getEvents();
    }

}
