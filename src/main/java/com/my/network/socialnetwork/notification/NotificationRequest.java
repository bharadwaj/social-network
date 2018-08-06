package com.my.network.socialnetwork.notification;


/*
*
{
 "to" : "ddyhHdz6h6Y:APA91bFU_zbVwUAxc4IxpRkb9XM6EIsYEXS6-adAuQ6LjUs_LUSrbI_tfLuOPhMiEVG5KkzRhpVeZt1fx4j909Z21L2Ic-48clowOfZ5AgQu20C_e3fE5q7TUKWkKqHUfebuIuDrGhkRGRfm935cCIV2LLXHg8isjg",
 "collapse_key" : "type_a",
 "notification" : {
     "body" : "What's up",
     "title": "yo",
	 "sound": "sound.caf",
	 "badge" : 1
 },
 "data" : {
     "body" : "First Notification Body",
     "title": "This is Title",
     "h1" : "Data for key one",
     "h2" : "Hellowww"
 }
}*/
public class NotificationRequest {

    private String to;
    private String collapse_key;

    private Notification notification;

    private NotificationData data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}



