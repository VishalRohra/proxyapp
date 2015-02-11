package ru.codeninja.proxyapp.connection;

/**
 * Created by vital on 11.02.15.
 */
public enum HttpMethod {
    GET("GET"), POST("POST");

    String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "HttpMethod{" +
                "name='" + name + '\'' +
                '}';
    }
}
