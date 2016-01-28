package cc.linkedme.commons.mcq.forward;

public class DefaultForwardFilter implements ForwardFilter {

    @Override
    public boolean needForward(long flag) {
        return true;
    }

}
