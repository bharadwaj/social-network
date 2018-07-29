package com.my.network.socialnetwork.notification;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

public class PushNotifictionHandler {
//	public final static String AUTH_KEY_FCM = "AAAAYFkNNDI:APA91bHKqVR32KCypCHOl4ghlWAsVHP0JJa6vod96SZaxd7ti86aLgvQgjhZBJDgDYRbcDhf5fLMtRHx9q-WlQfUypgO3fC2Cxsfg2Y0QHOCjyw7OLQfeLfjeeDmSYNf7VFG_IZpidA2";
//	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    /*private static Properties prop;

    static{
        InputStream is = null;
        try {
            prop = new Properties();
            is = PushNotifictionHandler.class.getClassLoader().getResourceAsStream(CommonDaoHelper.NOTIFICATION_PROPERTIES_FILE);
            System.out.println("is :: "+is);
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }

    public static boolean sendPushNotification(ArrayList<String> tokenList, NotificationsRequest notificationsRequest) throws IOException {
        boolean result = false;
        URL url = new URL(getPropertyValue("API_URL_FCM"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        tokenList = (ArrayList<String>) tokenList.stream().filter((String token) -> token != null && !token.trim().isEmpty() )
                .collect(Collectors.toList());

        System.out.println("tokenList :: "+tokenList);

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + getPropertyValue("AUTH_KEY_FCM"));
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        NotificationData data = notificationsRequest.getData();

        JSONObject dataPayload = new JSONObject();
        if(data !=null) {
            dataPayload.put("image", data.getImage());
            dataPayload.put("command", data.getCommand());
            dataPayload.put("message", data.getMessage());

            json.put("data", dataPayload);
        }

        JSONArray regId = null;

        regId = new JSONArray();
        for (int i = 0; i < tokenList.size(); i++) {
            regId.put(tokenList.get(i));
        }

        json.put("registration_ids", regId);
        json.put("to ", "topic/send");

        JSONObject info = new JSONObject();
        info.put("title", notificationsRequest.getTitle()); // Notification title
        info.put("body", notificationsRequest.getBody()); // Notification
        info.put("sound", "sound.caf"); // Notification sound
        info.put("badge", 1); // Notification Badge

        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            System.out.println("conn responseCode :: " + conn.getResponseCode());
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }*/

    /*public static void main(String[] args) throws IOException {
//		sendPushNotification("");
    }*/
}