
    create table aircraft (
        id bigint not null auto_increment,
        comment varchar(255),
        manufacturer varchar(255),
        model varchar(255),
        primary key (id),
        unique (manufacturer, model)
    );

    create table airline (
        id bigint not null auto_increment,
        code varchar(255),
        name varchar(255),
        primary key (id),
        unique (code)
    );

    create table airport (
        id bigint not null auto_increment,
        country varchar(255),
        locality varchar(255),
        postal_code varchar(255),
        state varchar(255),
        street varchar(255),
        code varchar(255),
        name varchar(255),
        primary key (id),
        unique (code)
    );

    create table boarding_pass (
        flight_segment_id bigint not null,
        ticket_id bigint not null,
        bags integer,
        checked_in bit not null,
        fare_class varchar(255) not null,
        seat_id bigint,
        primary key (flight_segment_id, ticket_id)
    );

    create table flight (
        id bigint not null auto_increment,
        arrival_gate varchar(255),
        arrival_time datetime not null,
        departure_gate varchar(255),
        departure_time datetime not null,
        number integer not null,
        status varchar(255),
        aircraft_id bigint,
        origin_id bigint not null,
        destination_id bigint not null,
        airline_id bigint not null,
        primary key (id)
    );

    create table flight_segment (
        id bigint not null auto_increment,
        leg integer not null,
        sequence integer not null,
        reservation_id bigint not null,
        flight_id bigint not null,
        primary key (id),
        unique (reservation_id, leg, sequence)
    );

    create table passenger (
        id bigint not null auto_increment,
        country varchar(255),
        locality varchar(255),
        postal_code varchar(255),
        state varchar(255),
        street varchar(255),
        birth_date date,
        first_name varchar(255),
        last_name varchar(255),
        primary key (id)
    );

    create table passport (
        owner_id bigint not null,
        country varchar(255),
        expiration_date date,
        number varchar(255),
        primary key (owner_id),
        unique (number)
    );

    create table reservation (
        id bigint not null auto_increment,
        airfare numeric(19,2),
        issue_date date not null,
        itinerary_type varchar(255) not null,
        number varchar(255) not null,
        status varchar(255),
        total numeric(19,2),
        origin_id bigint,
        destination_id bigint,
        purchaser_id bigint not null,
        primary key (id),
        unique (number)
    );

    create table seat (
        id bigint not null auto_increment,
        section varchar(255),
        aircraft_id bigint not null,
        letter varchar(255) not null,
        row bigint not null,
        primary key (id),
        unique (aircraft_id, row, letter)
    );

    create table seat_column (
        aircraft_id bigint not null,
        letter varchar(255) not null,
        section varchar(255) not null,
        aisle_left bit,
        aisle_right bit,
        primary key (aircraft_id, letter, section)
    );

    create table seat_row (
        aircraft_id bigint not null,
        number bigint not null,
        exit_row bit,
        section varchar(255),
        primary key (aircraft_id, number)
    );

    create table ticket (
        id bigint not null auto_increment,
        eticket_number varchar(255) not null,
        reservation_id bigint not null,
        passenger_id bigint not null,
        primary key (id),
        unique (eticket_number)
    );

    alter table boarding_pass 
        add index FKCDC1CB3450A2B044 (ticket_id), 
        add constraint FKCDC1CB3450A2B044 
        foreign key (ticket_id) 
        references ticket (id);

    alter table boarding_pass 
        add index FKCDC1CB34E39F82F (flight_segment_id), 
        add constraint FKCDC1CB34E39F82F 
        foreign key (flight_segment_id) 
        references flight_segment (id);

    alter table boarding_pass 
        add index FKCDC1CB3447979C24 (seat_id), 
        add constraint FKCDC1CB3447979C24 
        foreign key (seat_id) 
        references seat (id);

    alter table flight 
        add index FKB4318470796B830 (airline_id), 
        add constraint FKB4318470796B830 
        foreign key (airline_id) 
        references airline (id);

    alter table flight 
        add index FKB43184709DB6184 (aircraft_id), 
        add constraint FKB43184709DB6184 
        foreign key (aircraft_id) 
        references aircraft (id);

    alter table flight 
        add index FKB43184704F009CD5 (origin_id), 
        add constraint FKB43184704F009CD5 
        foreign key (origin_id) 
        references airport (id);

    alter table flight 
        add index FKB4318470914CFEED (destination_id), 
        add constraint FKB4318470914CFEED 
        foreign key (destination_id) 
        references airport (id);

    alter table flight_segment 
        add index FK1C925A6455065A70 (reservation_id), 
        add constraint FK1C925A6455065A70 
        foreign key (reservation_id) 
        references reservation (id);

    alter table flight_segment 
        add index FK1C925A648B8D85C4 (flight_id), 
        add constraint FK1C925A648B8D85C4 
        foreign key (flight_id) 
        references flight (id);

    alter table reservation 
        add index FKA2D543CC4F009CD5 (origin_id), 
        add constraint FKA2D543CC4F009CD5 
        foreign key (origin_id) 
        references airport (id);

    alter table reservation 
        add index FKA2D543CC914CFEED (destination_id), 
        add constraint FKA2D543CC914CFEED 
        foreign key (destination_id) 
        references airport (id);

    alter table reservation 
        add index FKA2D543CC66C5D6D9 (purchaser_id), 
        add constraint FKA2D543CC66C5D6D9 
        foreign key (purchaser_id) 
        references passenger (id);

    alter table seat 
        add index FK35CE05D79B58B4 (aircraft_id, letter, section), 
        add constraint FK35CE05D79B58B4 
        foreign key (aircraft_id, letter, section) 
        references seat_column (aircraft_id, letter, section);

    alter table seat 
        add index FK35CE059DB6184 (aircraft_id), 
        add constraint FK35CE059DB6184 
        foreign key (aircraft_id) 
        references aircraft (id);

    alter table seat 
        add index FK35CE0514873589 (aircraft_id, row), 
        add constraint FK35CE0514873589 
        foreign key (aircraft_id, row) 
        references seat_row (aircraft_id, number);

    alter table seat_column 
        add index FKD45B22D09DB6184 (aircraft_id), 
        add constraint FKD45B22D09DB6184 
        foreign key (aircraft_id) 
        references aircraft (id);

    alter table seat_row 
        add index FK35122CA09DB6184 (aircraft_id), 
        add constraint FK35122CA09DB6184 
        foreign key (aircraft_id) 
        references aircraft (id);

    alter table ticket 
        add index FKCBE86B0C55065A70 (reservation_id), 
        add constraint FKCBE86B0C55065A70 
        foreign key (reservation_id) 
        references reservation (id);

    alter table ticket 
        add index FKCBE86B0C610B9530 (passenger_id), 
        add constraint FKCBE86B0C610B9530 
        foreign key (passenger_id) 
        references passenger (id);
