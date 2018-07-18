package com.larry.meetingroomreservation.acceptanceTest;


import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRoomList() {
        List<Room> rooms = restTemplate.getForObject("/api/rooms/", List.class);
        assertThat(rooms.size(), is(5));
    }
}
