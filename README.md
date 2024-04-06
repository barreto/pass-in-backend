<h1 align="center">
  Pass.in Back-end
  <img src="docs/insignia.ico" width="32">
</h1>

<p align="center">Application for managing participants in in-person events.</p>

<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v17-blue.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.0.4-brightgreen.svg" />
    </a>
    <a alt="Maven">
        <img src="https://img.shields.io/badge/Maven-v4.0.0-purple.svg" />
    </a>
    <a alt="HyperSQL ">
        <img src="https://img.shields.io/badge/HyperSQL-v2.7.1-darkblue.svg" />
    </a>
    <a alt="Lombok">
        <img src="https://img.shields.io/badge/Lombok-v1.18.30-red.svg">
    </a>
    <a alt="Flyway">
        <img alt="Static Badge" src="https://img.shields.io/badge/Flyway-v9.22.3-red?color=%23cc0000">
    </a>
    <a alt="Visits">
        <img src="https://badges.pufler.dev/visits/barreto/pass-in-backend" alt="Visits Badge" width="auto"/>
    </a>
</p>

![Coding solutions for the world](./docs/wallpaper.png)

## About

Pass.in is a check-in system for in-person events where the organizer can control and manage the list of participants
and each person can generate their credential to access the event.

### Functional requirements

- Organizer
    - Must be able to register a new event;
    - Must be able to view event data;
    - Must be able to view the list of participants;
- Attendees
    - Must be able to register for an event;
    - Must be able to view their registration badge;
    - Must be able to check-in at the event;

### Non-functional requirements

- Check-in at the event will be carried out using a QRCode

### Business rules

- The attendees can only register for an event once;
- Attendees can only register for events with available places;
- The attendees can only check-in to an event once;

### Database model
<p  align="center">
  <img src="./docs/dbmodel.png" alt="Database model">
</p>

## Collection

[
![Collection preview.png](./docs/insomnia.png)
![Import in Insomnia](https://insomnia.rest/images/run.svg)
](./docs/collection)

## How to run

1. Download code base with `git clone`
2. Import project on IntelliJ
3. Download maven dependencies `./mvnw install`
4. Start Spring Boot application `./mvnw spring-boot:run`
5. Do your requests! 🎉

## References

- **Thanking**
    - The project was presented during the Java track at [Rocketseat](https://www.rocketseat.com.br/) NWL Unite bootcamp
      by [Fernanda Kipper](https://github.com/Fernanda-Kipper).

---
<h6 align="center">Thanks ❤️‍🔥</h6>

---

<p align="right"><a href="  #--passin-back-end--">back to top ↑</a></p>