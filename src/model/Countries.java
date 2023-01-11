package model;
/**
 * Supplied class Countries.java
 */
public class Countries {
    private int countryID;
    private String countryName;

    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param countryID the country ID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
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

    /**
     *
     * @return value to string
     */
    @Override
    public String toString() {
        return(countryName);
    }
}

