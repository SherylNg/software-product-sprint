// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*; 
import java.util.Set;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<TimeRange> available = new ArrayList<TimeRange>();
    Collection<Event> clashedEvents = new ArrayList<Event>();
    Collection<String> attendees = request.getAttendees();
    Long requestedDuration = request.getDuration();
    
    if (requestedDuration > TimeRange.WHOLE_DAY.duration()) {
      return available;
    }

    for (Event event: events) {
      if (clashed(event, attendees)) {
        clashedEvents.add(event);
      }
    }

    available.add(TimeRange.WHOLE_DAY);
    
    for (Event event: clashedEvents) {
      TimeRange eventTimeSlot = event.getWhen();

      Collection<TimeRange> updatedTimeSlots = new ArrayList<TimeRange>();
 
      for (TimeRange timeSlot: available) {
      
        if (timeSlot.overlaps(eventTimeSlot)) {
          int start = timeSlot.start();
          int end = timeSlot.end();
          int eventStart = eventTimeSlot.start();
          int eventEnd = eventTimeSlot.end();
          TimeRange newTimeSlot;

          if (start < eventStart) {
            newTimeSlot = TimeRange.fromStartEnd(start, eventStart, false);
            if (newTimeSlot.duration() >= requestedDuration) {
              updatedTimeSlots.add(newTimeSlot);
            }
          }

          if (end > eventEnd) {
            newTimeSlot = TimeRange.fromStartEnd(eventEnd, end, false);
            if (newTimeSlot.duration() >= requestedDuration) {
              updatedTimeSlots.add(newTimeSlot);
            }
          }

        } else {
          updatedTimeSlots.add(timeSlot);
        }
      }
  
      available = updatedTimeSlots;
    } 

    return available;
  }

  private static Boolean clashed(Event scheduled, Collection<String> attendees ) {
    Set<String> eventAttendees = scheduled.getAttendees();
  
    for (String attendee: attendees) {
      if (eventAttendees.contains(attendee)) {
        return true;
      } 
    }
    return false;
  }
}
