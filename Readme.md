Migration from Social Network 2 to Social Network 3:
1. Create new schema 'social_network_3';

2. Run data.sql script for PostCondition and OrderStatus

3. Copy PhoneBrand and later PhoneModel from social_network_2

4. Copy Pincode, District, State.
http://localhost:8080/load/hashtags/pincodes

5. Initialize all Hashtags.
localhost:8080/load/hashtags/init

6. Initialize all mydukan User to Subscribed users.
http://localhost:8080/user/init/existing

7. Move Posts from social_network_2 to social_network_3.
INSERT INTO social_network_3.Post(id, body, commentCount, createDate, imageUrl, isFriendsOnlyPost, isLiked, isPublicPost, isReported,  likeCount, modifyDate, postExpiryDate, reportAbuseCount, title, uniqueHandle, viewCount, postCondition_id, user_id, promotionFactor) SELECT id, body, commentCount, createDate, imageUrl, isFriendsOnlyPost, isLiked, isPublicPost, isReported,  likeCount, modifyDate, postExpiryDate, reportAbuseCount, title, uniqueHandle, viewCount, postCondition_id, user_id, 0 from social_network_2_aug_15.post;


8. Move Post Likes


SELECT social_network_3.SubscribedUser.id
FROM social_network_3.SubscribedUser
UNION ALL
SELECT social_network_2_aug_15.postlike.user_id
FROM social_network_2_aug_15.postlike

SELECT user_id
FROM
 (
   SELECT social_network_2_aug_15.postlike.user_id
   FROM social_network_2_aug_15.postlike
   UNION ALL
   SELECT social_network_3.SubscribedUser.id
   FROM social_network_3.SubscribedUser
)  t
GROUP BY user_id
HAVING COUNT(*) = 1
ORDER BY user_id;

