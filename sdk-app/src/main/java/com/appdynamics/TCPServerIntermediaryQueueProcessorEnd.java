package com.appdynamics;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.agent.api.Transaction;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * created by haojun.li on 7/10/18
 */
public class TCPServerIntermediaryQueueProcessorEnd extends AGenericInterceptor {
    //rx.subjects.AsyncSubject

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpserver.TCPServer$QueueProcessor";
    private static final String METHOD_TO_INSTRUMENT = "callURL";

    public TCPServerIntermediaryQueueProcessorEnd(){
        super();
    }

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {
        getLogger().info(""+System.identityHashCode(objects[0]));
        Transaction transaction = AppdynamicsAgent.startSegment(objects[0]);
        return transaction;
    }

    @Override
    public void onMethodEnd(Object o, Object o1, String s, String s1, Object[] objects, Throwable throwable, Object o2) {
        Transaction transaction = (Transaction)o;
        transaction.endSegment();
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