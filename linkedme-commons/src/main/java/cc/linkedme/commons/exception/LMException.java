package cc.linkedme.commons.exception;

/**
 * Created by qipo on 15/9/29.
 */
public class LMException extends RuntimeException {

    private LMExceptionFactor factor;
    private String description;

    public LMException(LMExceptionFactor factor) {
        if (factor != null) {
            this.factor = factor;
            this.description = factor.getErrorMessageEn();
        }
    }

    public LMException(LMExceptionFactor factor, Object message) {
        this.factor = factor;
        if (message == null) {
            description = factor.getErrorMessageEn();
        } else {
            description = message.toString();
        }
    }

    public String formatExceptionInfo(String path) {
        StringBuilder exceptionInfo = new StringBuilder();
        exceptionInfo.append("{");
        exceptionInfo.append("\"result\":").append("\"").append("false").append("\"").append(",");
        exceptionInfo.append("\"request\":").append("\"").append(path).append("\"").append(",");
        exceptionInfo.append("\"error_code\":").append(factor.getErrorCode());
        exceptionInfo.append(",");
        exceptionInfo.append("\"error\":").append("\"").append(description).append("\"");
        exceptionInfo.append("}");
        return exceptionInfo.toString();
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LMExceptionFactor getFactor() {
        return factor;
    }

    public void setFactor(LMExceptionFactor factor) {
        this.factor = factor;
    }
}
