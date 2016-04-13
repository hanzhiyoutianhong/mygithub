package cc.linkedme.data.model.params;

/**
 * Created by LinkedME01 on 16/4/11.
 */
public class SummaryButtonParams {

    public long user_id;
    public long app_id;
    public String btn_id;
    public String start_date;
    public String end_date;
    public int return_number;
    public String order_by;
    public long consumer_app_id;
    public int interval;
    public String data_type;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getReturn_number() {
        return return_number;
    }

    public void setReturn_number(int return_number) {
        this.return_number = return_number;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public long getConsumer_app_id() {
        return consumer_app_id;
    }

    public void setConsumer_app_id(long consumer_app_id) {
        this.consumer_app_id = consumer_app_id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public SummaryButtonParams() {}

    public SummaryButtonParams(long user_id, long app_id, String start_date, String end_date, int return_number, String order_by) {
        this.user_id = user_id;
        this.app_id = app_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.return_number = return_number;
        this.order_by = order_by;

    }

    public SummaryButtonParams(long user_id, long app_id, String start_date, String end_date, int interval) {
        this.user_id = user_id;
        this.app_id = app_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.interval = interval;
    }

    public SummaryButtonParams(long user_id, long app_id, String start_date, String end_date, String btnId, int interval) {
        this.user_id = user_id;
        this.app_id = app_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.btn_id = btnId;
        this.interval = interval;
    }

}
