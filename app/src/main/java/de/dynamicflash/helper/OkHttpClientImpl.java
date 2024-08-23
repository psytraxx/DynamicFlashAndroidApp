package de.dynamicflash.helper;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public final class OkHttpClientImpl {
    private static OkHttpClient client;

    public static OkHttpClient instance() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .followRedirects(true)
                    .addInterceptor(new LoggingInterceptor())
                    .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2, Protocol.QUIC))
                    .build();

        }
        return client;
    }
}
