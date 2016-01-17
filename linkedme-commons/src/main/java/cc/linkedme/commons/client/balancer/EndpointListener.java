package cc.linkedme.commons.client.balancer;

public interface EndpointListener<R> {

    void onEndpointCreate(Endpoint<R> endpoint);

    void onEndpointDestroy(Endpoint<R> endpoint);
}
