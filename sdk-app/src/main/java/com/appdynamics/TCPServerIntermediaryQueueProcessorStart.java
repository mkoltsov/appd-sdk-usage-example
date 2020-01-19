package com.appdynamics;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * created by haojun.li on 7/10/18
 */
public class TCPServerIntermediaryQueueProcessorStart extends AGenericInterceptor {
    //rx.subjects.AsyncSubject

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.util.CustomQueue";
    private static final String METHOD_TO_INSTRUMENT = "enqueue";

    public TCPServerIntermediaryQueueProcessorStart(){
        super();
    }

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {
        getLogger().info(""+System.identityHashCode(objects[0]));

        Object key = objects[0];
        AppdynamicsAgent.getTransaction().markHandoff(key);
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