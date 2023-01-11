package model;
/**
 * Supplied class ReportTotalApp.java
 */
public class ReportTotalApp {
    public String appMonth;
    public String appType;
    public int appCount;

    public ReportTotalApp(String appMonth, String appType, int appCount){
        this.appMonth = appMonth;
        this.appType = appType;
        this.appCount = appCount;
    }

    /**
     *
     * @param appMonth the appointment month to set
     */
    public void setAppMonth(String appMonth) {
        this.appMonth = appMonth;
    }

    /**
     *
     * @return the appointment month
     */
    public String getAppMonth() {
        return appMonth;
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
     * @return the appointment type
     */
    public String getAppType() {
        return appType;
    }

    /**
     *
     * @param appCount the total count to set
     */
    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    /**
     *
     * @return the total count
     */
    public int getAppCount() {
        return appCount;
    }
}
