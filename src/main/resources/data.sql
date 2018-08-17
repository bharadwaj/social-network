insert into social_network_3.PostCondition(id,conditionName) VALUES (1, 'New');
insert into social_network_3.PostCondition(id,conditionName) VALUES (2, 'Like New');
insert into social_network_3.PostCondition(id,conditionName) VALUES (3, 'Refurbished');
insert into social_network_3.PostCondition(id,conditionName) VALUES (4, 'Used');


insert into social_network_3.OrderStatus(id, statusName) values (1, 'Packaged');
insert into social_network_3.OrderStatus(id, statusName) values (2, 'Shipped');
insert into social_network_3.OrderStatus(id, statusName) values (3, 'Delivered');


INSERT INTO social_network_3.PhoneBrand SELECT * from social_network_2_aug_15.phonebrand;

INSERT INTO social_network_3.PhoneModel SELECT * from social_network_2_aug_15.phonemodel;

INSERT INTO social_network_3.Post SELECT * from social_network_2_aug_15.post;
