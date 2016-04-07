package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenUberParams {

    public String btn_id;
    public double pickup_lat;
    public double pickup_lng;
    public double dropoff_lat;
    public double dropoff_lng;

    public String getBtn_id() {
        return btn_id;
    }

    public void setBtn_id(String btn_id) {
        this.btn_id = btn_id;
    }

    public double getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(double pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public double getPickup_lng() {
        return pickup_lng;
    }

    public void setPickup_lng(double pickup_lng) {
        this.pickup_lng = pickup_lng;
    }

    public double getDropoff_lat() {
        return dropoff_lat;
    }

    public void setDropoff_lat(double dropoff_lat) {
        this.dropoff_lat = dropoff_lat;
    }

    public double getDropoff_lng() {
        return dropoff_lng;
    }

    public void setDropoff_lng(double dropoff_lng) {
        this.dropoff_lng = dropoff_lng;
    }

}
