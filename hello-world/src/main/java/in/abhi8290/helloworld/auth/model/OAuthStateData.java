package in.abhi8290.helloworld.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class OAuthStateData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private final String callbackUrl;
    private final String provider;
    private final long createdAt;
    
    @JsonCreator
    public OAuthStateData(@JsonProperty("callbackUrl") String callbackUrl,
                         @JsonProperty("provider") String provider,
                         @JsonProperty("createdAt") long createdAt) {
        this.callbackUrl = callbackUrl;
        this.provider = provider;
        this.createdAt = createdAt;
    }
    
    // Getters
    public String getCallbackUrl() { return callbackUrl; }
    public String getProvider() { return provider; }
    public long getCreatedAt() { return createdAt; }
}

