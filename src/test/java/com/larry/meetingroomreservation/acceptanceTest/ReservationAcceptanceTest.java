package com.larry.meetingroomreservation.acceptanceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.larry.meetingroomreservation.domain.entity.*;
import com.larry.meetingroomreservation.domain.entity.dto.ReservationRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationAcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(ReservationAcceptanceTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getReservationListByDateAndRoomName() throws IOException {
        Long reservedRoomId = 1L;
        LocalDate date = LocalDate.of(2018, 7, 17);
        String url = String.format("/api/reservations/%s/rooms/%d", toStringDateTime(date), reservedRoomId);

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<Reservation> reservations = convertList(response, Reservation.class);
        Reservation reservation = reservations.get(0);
        assertThat(reservations.size(), is(1));
        assertThat(reservation, instanceOf(Reservation.class));
        assertThat(reservation.getMeetingTime().getReservedDate(), is(LocalDate.of(2018, 7, 17)));
    }

    @Test
    public void registerReservation() {
        LocalDate reservedDate = LocalDate.of(2018, 7, 18);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(reservedDate, LocalTime.of(10, 0), LocalTime.of(11, 0), 4);

        ResponseEntity<Reservation> response = restTemplate.postForEntity(String.format("/api/reservations/%s/rooms/%d", reservedDate, 1), reservationRequestDto, Reservation.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getReservedDate(), is(reservedDate));
    }

    @Test
    public void register_fail_duplicate_time() {

    }



    public <T> List<T> convertList(ResponseEntity<List> response, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonArray = mapper.writeValueAsString(response.getBody());
        log.info("json : {}", jsonArray );
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> asList = mapper.readValue(jsonArray, javaType);
        return asList;
    }

    private String toStringDateTime(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Optional.ofNullable(date)
                .map(formatter::format)
                .orElse("");
    }

    protected <T> T getResource(String location, Class<T> responseType) {
        return restTemplate.getForObject(location, responseType);
    }

}
