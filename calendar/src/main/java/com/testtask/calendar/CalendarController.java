package com.testtask.calendar;

import com.testtask.calendar.domain.Event;
import com.testtask.calendar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class CalendarController {
@Autowired
    private EventRepository eventRepository;
    @GetMapping
    public String main( Map<String,Object> model){
        Iterable<Event>events=eventRepository.findAll();
        model.put("events",events);
        return "main";
    }
    @PostMapping
    public String add(@RequestParam String title, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finish, @RequestParam String name, Map<String,Object>model){
        Event event=new Event(title,description,start,finish,name);
        eventRepository.save(event);
        Iterable<Event> events=eventRepository.findAll();
        model.put("events",events);
        return"main";
    }
    @PostMapping("/year")
    public String filter(@RequestParam(required = false)String filter, Map<String,Object> model){
        Iterable<Event>events;

        if(filter!=null&&!filter.isEmpty()){
            events =eventRepository.findByYear(Integer.parseInt(filter));
        }else{
            events=eventRepository.findAll();
        }
        model.put("events",events);
        return  "main";
    }
    @PostMapping("/month")
    public String filterByMonth(@RequestParam(required = false)String filter, Map<String,Object> model){
        Iterable<Event>events;

        if(filter!=null&&!filter.isEmpty()){
            events =eventRepository.findByMonth(Integer.parseInt(filter));
        }else{
            events=eventRepository.findAll();
        }
        model.put("events",events);
        return  "main";
    }
    @PostMapping("/date")
    public String filterByDate(@RequestParam(required = false)String filter, Map<String,Object> model){
        Iterable<Event>events;

        if(filter!=null&&!filter.isEmpty()){
            String[]dateParts=filter.split("-",3);
            events =eventRepository.findByDate(Integer.parseInt(dateParts[2]),
                    Integer.parseInt(dateParts[1]),Integer.parseInt(dateParts[0]));
        }else{
            events=eventRepository.findAll();
        }
        model.put("events",events);
        return  "main";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam(required = false)String filter,Map<String,Object> model){

        Iterable<Event>events=eventRepository.findAll();
        if(filter!=null&&!filter.isEmpty())
            eventRepository.deleteById(Integer.valueOf(filter));
        model.put("events",events);
        return  "main";
    }



}
