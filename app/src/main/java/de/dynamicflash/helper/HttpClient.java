package de.dynamicflash.helper;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public final class HttpClient {
    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(new LoggingInterceptor())
                .protocols(Arrays.asList(Protocol.HTTP_1_1,Protocol.HTTP_2, Protocol.QUIC))
                .build();
    }
}
