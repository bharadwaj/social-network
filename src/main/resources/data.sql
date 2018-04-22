insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (1, true, true, true, 'Rajesh');

insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (2, true, false, true, 'Rakesh');

insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (3, true, false, true, 'Ramesh');

insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (4, true, false, true, 'Suresh');

insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (5, true, false, true, 'Somesh');

insert into social_network.user_class(id, is_open_follow, is_open_message, is_open_profile, name)
VALUES (6, true, false, true, 'Sailesh');

insert into social_network.phone_model(id,name) VALUES (1, 'Samsung J7');
insert into social_network.phone_model(id,name) VALUES (2, 'Karbonn 7');
insert into social_network.phone_model(id,name) VALUES (3, 'Note 34');
insert into social_network.phone_model(id,name) VALUES (4, 'LG G7');


insert into social_network.post_condition(id,condition_name) VALUES (1, 'New');
insert into social_network.post_condition(id,condition_name) VALUES (2, 'Like New');
insert into social_network.post_condition(id,condition_name) VALUES (3, 'Refurbished');
insert into social_network.post_condition(id,condition_name) VALUES (4, 'Used');

insert into social_network.order_status(id, status_name) values (1, 'Packaged');
insert into social_network.order_status(id, status_name) values (2, 'Shipped');
insert into social_network.order_status(id, status_name) values (3, 'Delivered');


insert into social_network_2.PostCondition(id,conditionName) VALUES (1, 'New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (2, 'Like New');
insert into social_network_2.PostCondition(id,conditionName) VALUES (3, 'Refurbished');
insert into social_network_2.PostCondition(id,conditionName) VALUES (4, 'Used');

#########################################################################################
###################################### New Classes ######################################
#########################################################################################


insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (1, true, true, true, 'Rajesh');

insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (2, true, false, true, 'Rakesh');

insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (3, true, false, true, 'Ramesh');

insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (4, true, false, true, 'Suresh');

insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (5, true, false, true, 'Somesh');

insert into social_network.userclass(id, isOpenFollow, isOpenMessage, isOpenProfile, name)
VALUES (6, true, false, true, 'Sailesh');

insert into social_network.phonebrand(id,name) VALUES (1, 'Samsung');
insert into social_network.phonebrand(id,name) VALUES (2, 'Karbonn');
insert into social_network.phonebrand(id,name) VALUES (3, 'Google');
insert into social_network.phonebrand(id,name) VALUES (4, 'LG');
insert into social_network.phonebrand(id,name) VALUES (4, 'Vivo');

insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (1, 'J7', 1);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (2, 'Karbonn 7', 2);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (3, 'Note 34', 1);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (4, 'G7', 4);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (5, 'V20', 4);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (6, 'V30', 4);
insert into social_network.phonemodel(id,name, phoneBrand_id) VALUES (7, 'S9 Plus', 1);


insert into social_network.postcondition(id,conditionName) VALUES (1, 'New');
insert into social_network.postcondition(id,conditionName) VALUES (2, 'Like New');
insert into social_network.postcondition(id,conditionName) VALUES (3, 'Refurbished');
insert into social_network.postcondition(id,conditionName) VALUES (4, 'Used');

insert into social_network.orderstatus(id, statusName) values (1, 'Packaged');
insert into social_network.orderstatus(id, statusName) values (2, 'Shipped');
insert into social_network.orderstatus(id, statusName) values (3, 'Delivered');

