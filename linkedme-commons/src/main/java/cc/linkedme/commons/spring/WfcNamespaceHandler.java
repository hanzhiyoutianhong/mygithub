package cc.linkedme.commons.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class WfcNamespaceHandler extends NamespaceHandlerSupport {

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("mysql", new WfcDefinitionParser(MysqlClientFactoryBean.class));
    }

}
