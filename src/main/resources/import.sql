INSERT INTO USER (id, user_id, password, name, email, role_name) VALUES (1, 'larry', 'test', 'jung', 'larry@gmail.com', 'ADMIN');
INSERT INTO USER (id, user_id, password, name, email, role_name) VALUES (2, 'charry', 'test', 'chae', 'charry@gmail.com', 'USER');

INSERT INTO ROOM (id, reservable, room_name, occupancy) VALUES (1, true, 101, 5);
INSERT INTO ROOM (id, reservable, room_name, occupancy) VALUES (2, true, 102, 5);
INSERT INTO ROOM (id, reservable, room_name, occupancy) VALUES (3, true, 103, 3);
INSERT INTO ROOM (id, reservable, room_name, occupancy) VALUES (4, true, 104, 3);
INSERT INTO ROOM (id, reservable, room_name, occupancy) VALUES (5, true, 105, 3);

INSERT INTO RESERVATION (id, start_time, end_time, reserved_date, booker_id, reserved_room_id, number_of_attendee) VALUES (1, '2018-07-17 10:00:00', '2018-07-17 12:30:00', '2018-07-16', 1, 1, 5);
INSERT INTO RESERVATION (id, start_time, end_time, reserved_date, booker_id, reserved_room_id, number_of_attendee) VALUES (2, '2018-07-17 13:00:00', '2018-07-17 14:00:00', '2018-07-16', 2, 1, 5);