package model;
/**
 * Supplied class Appointment.java
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Appointment {

    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    public int contactID;
    private String appType;
    private LocalDateTime appStartDateTime;
    private LocalDateTime appEndDateTime;
    public int customerID;
    public int userID;

    public Appointment(int appID, String appTitle, String appDescription, String appLocation, int contactID, String appType, LocalDateTime appStartDateTime, LocalDateTime appEndDateTime, int customerID, int userID){
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.contactID = contactID;
        this.appType = appType;
        this.appStartDateTime = appStartDateTime;
        this.appEndDateTime = appEndDateTime;
        this.customerID = customerID;
        this.userID = userID;
    }

    /**
     *
     * @return appointment ID
     */
    public int getAppID() {
        return appID;
    }

    /**
     *
     * @param appID the appointment ID to set
     */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /**
     *
     * @return appointment title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     *
     * @param appTitle the appointment title to set
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     *
     * @return appointment description
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     *
     * @param appDescription the  appointment description to set
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     *
     * @return  appointment location
     */
    public String getAppLocation() {
        return appLocation;
    }

    /**
     *
     * @param appLocation the appointment location to set
     */
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /**
     *
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID the contact ID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return appointment type
     */
    public String getAppType() {
        return appType;
    }

    /**
     *
     * @param appType the appointment type to set
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     *
     * @return appointment start date and time
     */
    public LocalDateTime getAppStartDateTime() {
        return appStartDateTime;
    }

    /**
     *
     * @param appStartDateTime the appointment start date and time to set
     */
    public void setAppStartDateTime(LocalDateTime appStartDateTime) {
        this.appStartDateTime = appStartDateTime;
    }

    /**
     *
     * @return appointment end date and time
     */
    public LocalDateTime getAppEndDateTime() {
        return appEndDateTime;
    }

    /**
     *
     * @param appEndDateTime the appointment end date and time
     */
    public void setAppEndDateTime(LocalDateTime appEndDateTime) {
        this.appEndDateTime = appEndDateTime;
    }

    /**
     *
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID the customer ID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

}
