insert into social_network_2.PhoneBrand(id,name) VALUES (1, 'Samsung');
insert into social_network_2.PhoneBrand(id,name) VALUES (2, 'Karbonn');
insert into social_network_2.PhoneBrand(id,name) VALUES (3, 'Google');
insert into social_network_2.PhoneBrand(id,name) VALUES (4, 'LG');
insert into social_network_2.PhoneBrand(id,name) VALUES (5, 'Vivo');


insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (1, 'J7', 1, 3300, 16);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (2, 'Karbonn 7', 2, 4000, 8);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (3, 'Note 34', 1, 3300, 16);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage ) VALUES (4, 'G7', 4, 3000, 32);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (5, 'V20', 4, 2500, 16);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (6, 'V30', 4, 4000, 20);
insert into social_network_2.PhoneModel(id,name, phoneBrand_id, batteryCapacity, internalStorage) VALUES (7, 'S9 Plus', 1, 3300, 16);


insert into social_network_2.PostCondition(id,conditionName) VALUES (1, 'New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (2, 'Like New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (3, 'Refurbished');
insert into social_network_2.PostCondition(id,conditionName) VALUES (4, 'Used');


insert into social_network_2.OrderStatus(id, statusName) values (1, 'Packaged');
insert into social_network_2.OrderStatus(id, statusName) values (2, 'Shipped');
insert into social_network_2.OrderStatus(id, statusName) values (3, 'Delivered');


