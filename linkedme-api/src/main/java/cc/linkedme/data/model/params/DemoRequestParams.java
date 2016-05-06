package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Vontroy on 16/3/19.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoRequestParams {
    public String name;
    public String email;
    public String mobile_phone;
    public String company_product_name;
    public String from_channel;
    public Long channel_tag;
    public String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getCompany_product_name() {
        return company_product_name;
    }

    public void setCompany_product_name(String company_product_name) {
        this.company_product_name = company_product_name;
    }

    public String getFrom_channel() {
        return from_channel;
    }

    public void setFrom_channel(String from_channel) {
        this.from_channel = from_channel;
    }

    public Long getChannel_tag() {
        return channel_tag;
    }

    public void setChannel_tag(Long channel_tag) {
        this.channel_tag = channel_tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
