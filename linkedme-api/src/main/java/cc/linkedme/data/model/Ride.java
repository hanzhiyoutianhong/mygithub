package cc.linkedme.data.model;

import javax.ws.rs.FormParam;

public class Ride {

    @FormParam("pickup_label")
    private String pickupLabel;

    @FormParam("pickup_lng")
    private double pickupLongitude;

    @FormParam("pickup_lat")
    private double pickupLatitude;

    @FormParam("dropoff_label")
    private String dropoffLabel;

    @FormParam("dropoff_lat")
    private double dropoffLatitude;

    @FormParam("dropoff_lng")
    private double dropoffLongitude;



    public String getPickupLabel() {
        return pickupLabel;
    }

    public void setPickupLabel(String pickupLabel) {
        this.pickupLabel = pickupLabel;
    }

    public double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public String getDropoffLabel() {
        return dropoffLabel;
    }

    public void setDropoffLabel(String dropoffLabel) {
        this.dropoffLabel = dropoffLabel;
    }

    public double getDropoffLatitude() {
        return dropoffLatitude;
    }

    public void setDropoffLatitude(double dropoffLatitude) {
        this.dropoffLatitude = dropoffLatitude;
    }

    public double getDropoffLongitude() {
        return dropoffLongitude;
    }

    public void setDropoffLongitude(double dropoffLongitude) {
        this.dropoffLongitude = dropoffLongitude;
    }


}
