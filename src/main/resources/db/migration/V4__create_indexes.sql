-- Ensures unique slugs
CREATE UNIQUE INDEX event_slug_key ON events(slug);

-- Avoid duplicated attendees in same event
CREATE UNIQUE INDEX attendees_event_id_email_key ON attendees(event_id, email);

-- Certify that attendees do check in only one time
CREATE UNIQUE INDEX check_ins_attendee_id_key ON check_ins(attendee_id);
