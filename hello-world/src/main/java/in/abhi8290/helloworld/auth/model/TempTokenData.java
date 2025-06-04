package in.abhi8290.helloworld.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// Temporary Token Data
public class TempTokenData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private final String userId;
    private final String userEmail;
    private final String userName;
    private final String provider;
    private final long createdAt;
    
    @JsonCreator
    public TempTokenData(
        @JsonProperty("userId") String userId,
                        @JsonProperty("userEmail") String userEmail,
                        @JsonProperty("userName") String userName,
                        @JsonProperty("provider") String provider,
                        @JsonProperty("createdAt") long createdAt) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.provider = provider;
        this.createdAt = createdAt;
    }
    
    // Getters
    public String getUserId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public String getUserName() { return userName; }
    public String getProvider() { return provider; }
    public long getCreatedAt() { return createdAt; }
}