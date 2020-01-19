package com.appdynamics;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.agent.api.ExitCall;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.IReflector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by haojun.li on 7/10/18
 */
public class TCPClientObjectToBytes extends AGenericInterceptor {
    IReflector innerPayload = null;

    //rx.subjects.AsyncSubject

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpclient.TCPClient";
    private static final String METHOD_TO_INSTRUMENT = "objectToBytes";

    public TCPClientObjectToBytes(){
        super();
        innerPayload = getNewReflectionBuilder()
                .accessFieldValue("innerPayload", true).build();
    }

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {

        getLogger().info("START TCPClientObjectToBytes");
        getLogger().info(""+System.identityHashCode(o));


        try {
            ExitCall exitCall = AppdynamicsAgent.fetchExitCall(o);

            String correlationHeader = exitCall.getCorrelationHeader();
            getLogger().info("TCPClientObjectToBytes "+correlationHeader);

            Map x = (Map)innerPayload.execute(o.getClass().getClassLoader(), objects[0]);
            x.put(AppdynamicsAgent.TRANSACTION_CORRELATION_HEADER,correlationHeader);

        }
        catch(Exception e){
            getLogger().info("TCPClientObjectToBytes ", e);
        }

        return null;
    }

    @Override
    public void onMethodEnd(Object o, Object o1, String s, String s1, Object[] objects, Throwable throwable, Object o2) {
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