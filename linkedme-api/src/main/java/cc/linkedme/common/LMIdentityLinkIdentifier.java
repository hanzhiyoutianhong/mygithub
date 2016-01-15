package cc.linkedme.common;

import java.io.Serializable;

/**
 * Created by qipo on 15/10/3.
 */
public class LMIdentityLinkIdentifier implements Serializable {
    private static final long serialVersionUID = -3748568935377747570L;
    private String identityId;
    private String linkIdentifier;

    /**
     * construction function
     */
    public LMIdentityLinkIdentifier(String identityId, String linkIdentifier) {
        this.identityId = identityId;
        this.linkIdentifier = linkIdentifier;
    }

    /**
     * get and set function
     */

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getLinkIdentifier() {
        return linkIdentifier;
    }

    public void setLinkIdentifier(String linkIdentifier) {
        this.linkIdentifier = linkIdentifier;
    }
}
