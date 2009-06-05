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

-- purely business key

--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (1, 'FIRST', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (2, 'FIRST', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (3, 'ECONOMY', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (4, 'ECONOMY', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (5, 'ECONOMY', 1, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (6, 'ECONOMY', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (7, 'ECONOMY', 0, 1);
--INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (8, 'ECONOMY', 0, 1);
--
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('A', 'FIRST', 1, 0, 1);
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('C', 'FIRST', 0, 1, 1);
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('A', 'ECONOMY', 0, 0, 1);
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('B', 'ECONOMY', 1, 0, 1);
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('C', 'ECONOMY', 0, 1, 1);
--INSERT INTO seat_column (letter, section, aisle_right, aisle_left, aircraft_id) VALUES ('D', 'ECONOMY', 0, 0, 1);
--
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (1, 1, 'A', 'FIRST', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (2, 1, 'C', 'FIRST', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (3, 2, 'A', 'FIRST', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (4, 2, 'C', 'FIRST', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (5, 3, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (6, 3, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (7, 3, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (8, 3, 'D', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (9, 4, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (10, 4, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (11, 4, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (12, 4, 'D', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (13, 5, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (14, 5, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (15, 5, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (16, 5, 'D', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (17, 6, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (18, 6, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (19, 6, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (20, 6, 'D', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (21, 7, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (22, 7, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (23, 7, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (24, 7, 'D', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (25, 8, 'A', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (26, 8, 'B', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (27, 8, 'C', 'ECONOMY', 1)
--INSERT INTO seat (id, row, letter, section, aircraft_id) VALUES (28, 8, 'D', 'ECONOMY', 1)

-- hybrid

INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (1, 'FIRST', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (2, 'FIRST', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (3, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (4, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (5, 'ECONOMY', 1, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (6, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (7, 'ECONOMY', 0, 1);
INSERT INTO seat_row (number, section, exit_row, aircraft_id) VALUES (8, 'ECONOMY', 0, 1);

INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (1, 'A', 'FIRST', 1, 0, 1);
INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (2, 'C', 'FIRST', 0, 1, 1);
INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (3, 'A', 'ECONOMY', 0, 0, 1);
INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (4, 'B', 'ECONOMY', 1, 0, 1);
INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (5, 'C', 'ECONOMY', 0, 1, 1);
INSERT INTO seat_column (id, letter, section, aisle_right, aisle_left, aircraft_id) VALUES (6, 'D', 'ECONOMY', 0, 0, 1);

INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (1, 1, 1, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (2, 1, 2, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (3, 2, 1, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (4, 2, 2, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (5, 3, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (6, 3, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (7, 3, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (8, 3, 6, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (9, 4, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (10, 4, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (11, 4, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (12, 4, 6, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (13, 5, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (14, 5, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (15, 5, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (16, 5, 6, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (17, 6, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (18, 6, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (19, 6, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (20, 6, 6, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (21, 7, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (22, 7, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (23, 7, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (24, 7, 6, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (25, 8, 3, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (26, 8, 4, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (27, 8, 5, 1)
INSERT INTO seat (id, row_number, column_id, aircraft_id) VALUES (28, 8, 6, 1)

-- purely surrogate key

--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (1, 'FIRST', 1, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (2, 'FIRST', 2, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (3, 'ECONOMY', 3, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (4, 'ECONOMY', 4, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (5, 'ECONOMY', 5, 1, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (6, 'ECONOMY', 6, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (7, 'ECONOMY', 7, 0, 1);
--INSERT INTO seat_row (id, section, number, exit_row, aircraft_id) VALUES (8, 'ECONOMY', 8, 0, 1);
--
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (1, 'FIRST', 'A', 1, 0, 1);
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (2, 'FIRST', 'C', 0, 1, 1);
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (3, 'ECONOMY', 'A', 0, 0, 1);
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (4, 'ECONOMY', 'B', 1, 0, 1);
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (5, 'ECONOMY', 'C', 0, 1, 1);
--INSERT INTO seat_column (id, section, letter, aisle_right, aisle_left, aircraft_id) VALUES (6, 'ECONOMY', 'D', 0, 0, 1);
--
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (1, 1, 1, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (2, 1, 2, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (3, 2, 1, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (4, 2, 2, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (5, 3, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (6, 3, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (7, 3, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (8, 3, 6, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (9, 4, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (10, 4, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (11, 4, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (12, 4, 6, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (13, 5, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (14, 5, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (15, 5, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (16, 5, 6, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (17, 6, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (18, 6, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (19, 6, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (20, 6, 6, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (21, 7, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (22, 7, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (23, 7, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (24, 7, 6, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (25, 8, 3, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (26, 8, 4, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (27, 8, 5, 1);
--INSERT INTO seat (id, row_id, column_id, aircraft_id) VALUES (28, 8, 6, 1);

INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (1, 'Allen', 'Daniel', '1978-03-20', '8115 Pennington Dr', 'Laurel', 'MD', '20724', 'United States');
INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (2, 'White', 'Sarah', '1978-04-12', '8115 Pennington Dr', 'Laurel', 'MD', '20724', 'United States');
INSERT INTO passenger (id, last_name, first_name, birth_date, street, locality, state, postal_code, country) VALUES (3, 'Traveler', 'Tim', '1972-01-06', '800 Westhurst Ln', 'Oakton', 'VA', '22124', 'United States');

--INSERT INTO passport (owner_id, country, expiration_date, number) VALUES (1, 'United States', '2020-01-01', '1234567890');
--INSERT INTO passport (owner_id, country, expiration_date, number) VALUES (2, 'United States', '2020-01-01', '1234567891');

INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (1, '2009-06-01 18:35:00', '2009-06-01 21:25:00', 71, 'SCHEDULED', 3, 4, 1, 6);
INSERT INTO flight (id, departure_time, arrival_time, number, status, destination_id, origin_id, aircraft_id, airline_id) VALUES (2, '2009-06-06 09:25:00', '2009-06-06 17:35:00', 78, 'SCHEDULED', 4, 3, 2, 6);

INSERT INTO reservation (id, purchaser_id, number, issue_date, itinerary_type, origin_id, destination_id, airfare, total, status) VALUES (1, 1, 'VX100J1', NOW(), 'ROUND_TRIP', 3, 4, '498.00', '534.00', 'RESERVED');
INSERT INTO reservation (id, purchaser_id, number, issue_date, itinerary_type, origin_id, destination_id, airfare, total, status) VALUES (2, 3, 'VX200J1', NOW(), 'ROUND_TRIP', 3, 4, '249.00', '267.00', 'RESERVED');

INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (1, '0123456780', 1, 1);
INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (2, '0123456781', 2, 1);
INSERT INTO ticket (id, eticket_number, passenger_id, reservation_id) VALUES (3, '0123456795', 3, 2);

INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (1, 1, 1, 1, 1);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (2, 1, 2, 1, 2);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (3, 2, 1, 1, 1);
INSERT INTO flight_segment (id, reservation_id, leg, sequence, flight_id) VALUES (4, 2, 2, 1, 2);

INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, seat_id, fare_class) VALUES (1, 1, 0, 5, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (1, 2, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (2, 1, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (2, 2, 0, 'ECONOMY');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (3, 1, 0, 'BUSINESS');
INSERT INTO boarding_pass (ticket_id, flight_segment_id, checked_in, fare_class) VALUES (3, 2, 0, 'BUSINESS');
