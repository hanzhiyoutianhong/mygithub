package cc.linkedme.commons.client.balancer;

import cc.linkedme.commons.client.balancer.impl.EndpointBalancerConfig;

public interface EndpointManager<R> {

    void init(EndpointPool<R> endpointPool, EndpointBalancerConfig config);

    public EndpointBalancerConfig getConfig();

}
