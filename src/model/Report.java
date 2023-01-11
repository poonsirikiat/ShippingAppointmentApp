package model;
/**
 * Supplied class Report.java
 */
import java.time.LocalDateTime;

public class Report {
    private int appID;
    private String appTitle;
    private String appType;
    private String appContact;
    private String appDescription;
    private LocalDateTime appStartDateTime;
    private LocalDateTime appEndDateTime;
    public int customerID;
    public int contactID;

    public Report(int appID, String appTitle, String appType, String appContact, String appDescription, LocalDateTime appStartDateTime, LocalDateTime appEndDateTime, int customerID, int contactID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appType = appType;
        this.appContact = appContact;
        this.appDescription = appDescription;
        this.appStartDateTime = appStartDateTime;
        this.appEndDateTime = appEndDateTime;
        this.customerID = customerID;
        this.contactID = contactID;
    }

    /**
     *
     * @return appoint ID
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
     * @param appDescription the appointment description to set
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     *
     * @param appContact the appointment contact to set
     */
    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    /**
     *
     * @return appointment contact
     */
    public String getAppContact() {
        return appContact;
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
     * @param appType appointment type to set
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     *
     * @return local start date and time
     */
    public LocalDateTime getAppStartDateTime() {
        return appStartDateTime;
    }

    /**
     *
     * @param appStartDateTime the local start date and time to set
     */
    public void setAppStartDateTime(LocalDateTime appStartDateTime) {
        this.appStartDateTime = appStartDateTime;
    }

    /**
     *
     * @return local end date and time
     */
    public LocalDateTime getAppEndDateTime() {
        return appEndDateTime;
    }

    /**
     *
     * @param appEndDateTime the local end date and time to set
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
     * @return value to string
     */
    @Override
    public String toString() {
        return(appContact);
    }

}
