package config;

import com.example.api.utils.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestConfig {

    public static RequestSpecification getPetstoreSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("petstore.base.url"))
                .addHeader("api_key", token)
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
