insert into social_network_2.PostCondition(id,conditionName) VALUES (1, 'New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (2, 'Like New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (3, 'Refurbished');
insert into social_network_2.PostCondition(id,conditionName) VALUES (4, 'Used');


insert into social_network_2.OrderStatus(id, statusName) values (1, 'Packaged');
insert into social_network_2.OrderStatus(id, statusName) values (2, 'Shipped');
insert into social_network_2.OrderStatus(id, statusName) values (3, 'Delivered');


INSERT INTO social_network_2.phonebrand SELECT * from rds_bak_social_network_2.phonebrand;

INSERT INTO social_network_2.phonemodel SELECT * from rds_bak_social_network_2.phonemodel;