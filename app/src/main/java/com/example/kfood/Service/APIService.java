package com.example.kfood.Service;

public class APIService {

    /*private static String baseURL = "https://devandroi.000webhostapp.com/Server/";*/
    public static String baseURL ="http://192.168.0.104/server/";

   // public static String baseURL = "https://androidzing.000webhostapp.com/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(baseURL).create(DataService.class);
    }
}
