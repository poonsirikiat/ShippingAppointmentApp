package model;
/**
 * Supplied class Customer.java
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;
    private String customerCountry;
    private String divisionName;
    private int divisionID;
    private String countryName;

    public Customer(int customerID, String customerName, String customerAddress, String customerPostal, String customerPhone, int divisionID, String divisionName, String countryName){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerPhone = customerPhone;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;

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
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     *
     * @param customerAddress the customer address to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     *
     * @return customer postal code
     */
    public String getCustomerPostal() {
        return customerPostal;
    }

    /**
     *
     * @param customerPostal the customer postal code to set
     */
    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    /**
     *
     * @return customer phone number
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     *
     * @param customerPhone the customer phone number to set
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     *
     * @return customer country
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     *
     * @param customerCountry the customer country to set
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    /**
     *
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @param divisionName the division name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @param divisionID the division ID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName the country name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
