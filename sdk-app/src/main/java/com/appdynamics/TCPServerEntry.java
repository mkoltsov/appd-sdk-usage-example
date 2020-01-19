package com.appdynamics;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.contexts.ISDKUserContext;
import com.appdynamics.instrumentation.sdk.template.AEntry;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.IReflector;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.ReflectorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by haojun.li on 7/10/18
 */
public class TCPServerEntry extends AEntry {
    IReflector innerPayload = null;

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpserver.TCPServer";
    private static final String METHOD_TO_INSTRUMENT = "processPayload";

    public TCPServerEntry(){
        super();
        innerPayload = getNewReflectionBuilder()
                .accessFieldValue("innerPayload", true).build();
    }

    @Override
    public boolean isCorrelationEnabled() {
        return true;
    }

    @Override
    public boolean isCorrelationEnabledForOnMethodBegin() {
        return true;
    }

    @Override
    public String unmarshalTransactionContext(Object o, String s, String s1, Object[] objects, ISDKUserContext isdkUserContext) throws ReflectorException {

        getLogger().info(""+o);
        getLogger().info(""+objects[0]);

        Map x = (Map)innerPayload.execute(o.getClass().getClassLoader(), objects[0]);
        return x.get(AppdynamicsAgent.TRANSACTION_CORRELATION_HEADER).toString();

    }

    @Override
    public String getBusinessTransactionName(Object o, String s, String s1, Object[] objects, ISDKUserContext isdkUserContext) throws ReflectorException {


        return "Default";
    }


    @Override
    public List<Rule> initializeRules() {
        List<Rule> result = new ArrayList<>();

        Rule.Builder bldr = new Rule.Builder(CLASS_TO_INSTRUMENT);
        bldr = bldr.classMatchType(SDKClassMatchType.MATCHES_CLASS).classStringMatchType(SDKStringMatchType.EQUALS);
        bldr = bldr.methodMatchString(METHOD_TO_INSTRUMENT).methodStringMatchType(SDKStringMatchType.EQUALS);
        result.add(bldr.build());
        return result;
    }


}