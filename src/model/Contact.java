package model;
/**
 * Supplied class Contact.java
 */
public class Contact {
    public int contactID;
    public String contactName;
    public String contactEmail;

    public Contact(int contactID, String contactName, String contactEmail){
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
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
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     *
     * @param contactEmail the contact email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
