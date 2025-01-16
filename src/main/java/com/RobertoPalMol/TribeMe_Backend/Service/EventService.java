package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.Event;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long id, Event eventDetails) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            Event updatedEvent = event.get();
            updatedEvent.setTitle(eventDetails.getTitle());
            updatedEvent.setDescription(eventDetails.getDescription());
            updatedEvent.setEventDate(eventDetails.getEventDate());
            updatedEvent.setPlace(eventDetails.getPlace());
            updatedEvent.setCreateTime(eventDetails.getCreateTime());
            eventRepository.save(updatedEvent);
            return Optional.of(updatedEvent);
        }
        return Optional.empty();
    }

    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

