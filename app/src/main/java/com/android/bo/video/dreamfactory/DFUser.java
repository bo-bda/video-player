package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Bo on 06.03.2016.
 */
public class DFUser {

    @SerializedName("session_token")
    @Expose
    private String sessionToken;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("is_sys_admin")
    @Expose
    private Boolean isSysAdmin;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("host")
    @Expose
    private String host;

    /**
     * @return The sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * @param sessionToken The session_token
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * @return The sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId The session_id
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The isSysAdmin
     */
    public Boolean getIsSysAdmin() {
        return isSysAdmin;
    }

    /**
     * @param isSysAdmin The is_sys_admin
     */
    public void setIsSysAdmin(Boolean isSysAdmin) {
        this.isSysAdmin = isSysAdmin;
    }

    /**
     * @return The lastLoginDate
     */
    public String getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate The last_login_date
     */
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return The host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host The host
     */
    public void setHost(String host) {
        this.host = host;
    }
}
