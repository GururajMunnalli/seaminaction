START TRANSACTION;

INSERT INTO airport (id, name, code, locality, state, postal_code, country) VALUES (1, 'Baltimore/Washington International Airport', 'BWI', 'Baltimore', 'MD', '21240', 'United States');
INSERT INTO airport (id, name, code, locality, state, postal_code, country) VALUES (2, 'Ronald Reagan National Airport', 'DCA', 'Arlington', 'VA', '20001', 'United States');
INSERT INTO airport (id, name, code, locality, state, postal_code, country) VALUES (3, 'Dulles International Airport', 'IAD', 'Dulles', 'VA', '20166', 'United States');
INSERT INTO airport (id, name, code, locality, state, postal_code, country) VALUES (4, 'San Francisco International Airport', 'SFO', 'San Francisco', 'CA', '94128', 'United States');

INSERT INTO airline (id, code, name) VALUES (1, 'AA', 'American Airlines');
INSERT INTO airline (id, code, name) VALUES (2, 'UA', 'United Airlines');
INSERT INTO airline (id, code, name) VALUES (3, 'FL', 'Airtran Airways');
INSERT INTO airline (id, code, name) VALUES (4, 'WN', 'Southwest Airlines');
INSERT INTO airline (id, code, name) VALUES (5, 'US', 'US Airways');
INSERT INTO airline (id, code, name) VALUES (6, 'VX', 'Virgin American');

INSERT INTO aircraft (id, manufacturer, model) VALUES (1, 'Airbus', 'A319');
INSERT INTO aircraft (id, manufacturer, model) VALUES (2, 'Boeing', '757-200');

INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (1, 'FIRST', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (2, 'FIRST', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (3, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (4, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (5, 'ECONOMY', 1, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (6, 'ECONOMY', 1, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (7, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (8, 'ECONOMY', 0, 1);

INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('A', 'FIRST', 0, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('C', 'FIRST', 1, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('D', 'FIRST', 0, 1, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('F', 'FIRST', 0, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('A', 'ECONOMY', 0, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('B', 'ECONOMY', 0, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('C', 'ECONOMY', 1, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('D', 'ECONOMY', 0, 1, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('E', 'ECONOMY', 0, 0, 1);
INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('F', 'ECONOMY', 0, 0, 1);

INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 1, 1, 'A', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 2, 1, 'C', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 3, 1, 'D', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 4, 1, 'F', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 5, 2, 'A', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 6, 2, 'C', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 7, 2, 'D', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 8, 2, 'F', 'FIRST', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES ( 9, 3, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (10, 3, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (11, 3, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (12, 3, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (13, 3, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (14, 3, 'F', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (15, 4, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (16, 4, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (17, 4, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (18, 4, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (19, 4, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (20, 4, 'F', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (21, 5, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (22, 5, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (23, 5, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (24, 5, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (25, 5, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (26, 5, 'F', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (27, 6, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (28, 6, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (29, 6, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (30, 6, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (31, 6, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (32, 6, 'F', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (33, 7, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (34, 7, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (35, 7, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (36, 7, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (37, 7, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (38, 7, 'F', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (39, 8, 'A', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (40, 8, 'B', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (41, 8, 'C', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (42, 8, 'D', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (43, 8, 'E', 'ECONOMY', 1)
INSERT INTO seat (id, row_number, letter, section, aircraft_id) VALUES (44, 8, 'F', 'ECONOMY', 1)

INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (1, 'Allen', 'Daniel', '1978-03-20', '8115 Pennington Dr', 'Laurel', 'MD', '20724', 'United States');
INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (2, 'White', 'Sarah', '1978-04-12', '8115 Pennington Dr', 'Laurel', 'MD', '20724', 'United States');
INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (3, 'Traveler', 'Tim', '1972-01-06', '800 Westhurst Ln', 'Oakton', 'VA', '22124', 'United States');

--INSERT INTO passport (owner_id, country, expiration_date, number) VALUES (1, 'United States', '2020-01-01', '1234567890');
--INSERT INTO passport (owner_id, country, expiration_date, number) VALUES (2, 'United States', '2020-01-01', '1234567891');

--INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (1, '2009-06-01 18:35:00', '2009-06-01 21:25:00', 71, 'SCHEDULED', 3, 4, 1, 6);
--INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (2, '2009-06-06 09:25:00', '2009-06-06 17:35:00', 78, 'SCHEDULED', 4, 3, 2, 6);
INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (1, DATE_ADD(DATE_ADD(CURDATE(), INTERVAL HOUR(CURTIME()) + 1 HOUR), INTERVAL 35 MINUTE), DATE_ADD(DATE_ADD(CURDATE(), INTERVAL HOUR(CURTIME()) + 4 HOUR), INTERVAL 25 MINUTE), 71, 'SCHEDULED', 3, 4, 1, 6);
INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (2, DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 5 DAY), INTERVAL '09:25' HOUR_MINUTE), DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 5 DAY), INTERVAL '17:35' HOUR_MINUTE), 78, 'SCHEDULED', 4, 3, 2, 6);

INSERT INTO reservation (id, purchaser_id, number, issue_date, itinerary_type, origin_id, destination_id, airfare, total, status) VALUES (1, 1, 'VX100J1', NOW(), 'ROUND_TRIP', 3, 4, '498.00', '534.00', 'RESERVED');
INSERT INTO reservation (id, purchaser_id, number, issue_date, itinerary_type, origin_id, destination_id, airfare, total, status) VALUES (2, 3, 'VX200J1', NOW(), 'ROUND_TRIP', 3, 4, '249.00', '267.00', 'RESERVED');

INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (1, '0123456780', 1, 1);
INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (2, '0123456781', 2, 1);
INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (3, '0123456795', 3, 2);

INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (1, 1, 1, 1, 1);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (2, 1, 2, 1, 2);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (3, 2, 1, 1, 1);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (4, 2, 2, 1, 2);

INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, seat_id, fare_class) VALUES (1, 1, 0, 9, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (1, 2, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (2, 1, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (2, 2, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, seat_id, fare_class) VALUES (3, 1, 0, 2, 'FIRST');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (3, 2, 0, 'FIRST');

COMMIT;
