package com.appdynamics;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.contexts.ISDKUserContext;
import com.appdynamics.instrumentation.sdk.template.AExit;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.IReflector;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.ReflectorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by haojun.li on 7/10/18
 */
public class TCPClientExit extends AExit {
    IReflector innerPayload = null;

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpclient.TCPClient";
    private static final String METHOD_TO_INSTRUMENT = "objectToBytes";

    public TCPClientExit(){
        super();
        innerPayload = getNewReflectionBuilder()
                .accessFieldValue("innerPayload", true).build();
    }

    @Override
    public boolean isCorrelationEnabled() {
        return false;
    }

    @Override
    public boolean isCorrelationEnabledForOnMethodBegin() {
        return false;
    }

    @Override
    public void marshalTransactionContext(String s, Object o, String s1, String s2, Object[] objects, Throwable throwable, Object o1, ISDKUserContext isdkUserContext) throws ReflectorException {
        Map x = (Map)innerPayload.execute(o.getClass().getClassLoader(), objects[0]);
        x.put(AppdynamicsAgent.TRANSACTION_CORRELATION_HEADER,s);

    }

    @Override
    public Map<String, String> identifyBackend(Object o, String s, String s1, Object[] objects, Throwable throwable, Object o1, ISDKUserContext isdkUserContext) throws ReflectorException {
        return null;
    }


    @Override
    public List<Rule> initializeRules() {
        List<Rule> result = new ArrayList<>();

        /*Rule.Builder bldr = new Rule.Builder(CLASS_TO_INSTRUMENT);
        bldr = bldr.classMatchType(SDKClassMatchType.MATCHES_CLASS).classStringMatchType(SDKStringMatchType.EQUALS);
        bldr = bldr.methodMatchString(METHOD_TO_INSTRUMENT).methodStringMatchType(SDKStringMatchType.EQUALS);
        result.add(bldr.build());*/
        return result;
    }


}