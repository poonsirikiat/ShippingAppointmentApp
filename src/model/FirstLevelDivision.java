package model;
/**
 * Supplied class FirstLevelDivision.java
 */
public class FirstLevelDivision {
    private int divisionID;
    private String divisionName;
    public int countryID;

    public FirstLevelDivision(int divisionID, String divisionName, int countryID){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
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
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @param divisionName the divison name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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
     * @return value to string
     */
    @Override
    public String toString() {
        return(divisionName);
    }

}
