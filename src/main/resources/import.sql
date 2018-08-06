-- larry admin
-- charry password
INSERT INTO USER (id, user_id, password, name, email, role_name) VALUES (1, 'larry', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'jung', 'larry@gmail.com', 'ROLE_ADMIN');
INSERT INTO USER (id, user_id, password, name, email, role_name) VALUES (2, 'charry', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'chae', 'charry@gmail.com', 'ROLE_USER');
INSERT INTO USER (id, user_id, password, name, email, role_name, social_id, social_provider) VALUES (3, '정채균', 'ramdompassword', 'SOCIAL_USER', 'corbswjd@nate.com', 'ROLE_USER', 888666043, 'KAKAO');

INSERT INTO ROOM (id, room_name, occupancy) VALUES (1, 101, 5);
INSERT INTO ROOM (id, room_name, occupancy) VALUES (2, 102, 5);
INSERT INTO ROOM (id, room_name, occupancy) VALUES (3, 103, 3);
INSERT INTO ROOM (id, room_name, occupancy) VALUES (4, 104, 3);
INSERT INTO ROOM (id, room_name, occupancy) VALUES (5, 105, 3);

-- INSERT INTO RESERVATION (id, start_time, end_time, reserved_date, booker_id, reserved_room_id, number_of_attendee) VALUES (1, '2018-07-17 10:00:00', '2018-07-17 12:30:00', '2018-07-17', 1, 1, 5);
INSERT INTO RESERVATION (id, start_time, end_time, reserved_date, booker_id, reserved_room_id, number_of_attendee) VALUES (2, '13:00:00', '14:00:00', '2018-08-01', 2, 1, 5);
INSERT INTO RESERVATION (id, start_time, end_time, reserved_date, booker_id, reserved_room_id, number_of_attendee) VALUES (3, '10:00:00', '11:00:00', '2018-08-01', 3, 1, 5);