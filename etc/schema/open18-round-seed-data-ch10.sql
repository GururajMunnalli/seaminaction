INSERT INTO PUBLIC.ROUND(ID, DATE, VERSION, NOTES, TOTAL_SCORE, WEATHER, TEE_SET_ID, GOLFER_ID) VALUES
(1, DATE '2008-11-12', 0, 'I was crushing my drives today.', 78, 'MOSTLY_SUNNY', 44, 2),
(2, DATE '2008-11-14', 0, 'I struggled today with chipping.', 105, 'CLOUDY', 6, 2);;

INSERT INTO PUBLIC.SCORE(ID, PUTTS, STROKES, FAIRWAY, GIR, HOLE_ID, ROUND_ID) VALUES
(1, 1, 4, NULL, NULL, 230, 1),
(2, 1, 3, NULL, NULL, 222, 1),
(3, 2, 4, NULL, NULL, 219, 1),
(4, 2, 3, NULL, NULL, 220, 1),
(5, 3, 5, NULL, NULL, 229, 1),
(6, 2, 4, NULL, NULL, 218, 1),
(7, 3, 4, NULL, NULL, 224, 1),
(8, 3, 5, NULL, NULL, 228, 1),
(9, 5, 6, NULL, NULL, 227, 1),
(10, 1, 4, NULL, NULL, 217, 1),
(11, 3, 5, NULL, NULL, 226, 1),
(12, 4, 6, NULL, NULL, 223, 1),
(13, 2, 5, NULL, NULL, 232, 1),
(14, 3, 4, NULL, NULL, 231, 1),
(15, 3, 5, NULL, NULL, 221, 1),
(16, 0, 3, NULL, NULL, 225, 1),
(17, 3, 5, NULL, NULL, 234, 1),
(18, 1, 3, NULL, NULL, 233, 1),
(19, 2, 4, NULL, NULL, 61, 2),
(20, 2, 7, NULL, NULL, 70, 2),
(21, 3, 4, NULL, NULL, 71, 2),
(22, 3, 7, NULL, NULL, 55, 2),
(23, 3, 7, NULL, NULL, 69, 2),
(24, 3, 7, NULL, NULL, 65, 2),
(25, 3, 4, NULL, NULL, 56, 2),
(26, 3, 7, NULL, NULL, 57, 2),
(27, 3, 7, NULL, NULL, 68, 2),
(28, 3, 7, NULL, NULL, 60, 2),
(29, 3, 7, NULL, NULL, 63, 2),
(30, 3, 7, NULL, NULL, 58, 2),
(31, 3, 5, NULL, NULL, 59, 2),
(32, 1, 3, NULL, NULL, 67, 2),
(33, 1, 5, NULL, NULL, 62, 2),
(34, 1, 4, NULL, NULL, 72, 2),
(35, 3, 7, NULL, NULL, 66, 2),
(36, 3, 6, NULL, NULL, 64, 2);;
