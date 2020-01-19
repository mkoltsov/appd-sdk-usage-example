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

/**
 * created by haojun.li on 7/10/18
 */
public class TCPClientSendRequest extends AGenericInterceptor {

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpclient.TCPClient";
    private static final String METHOD_TO_INSTRUMENT = "sendRequest";


    public TCPClientSendRequest(){
        super();

    }

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {

        ExitCall exitCall = null;
        exitCall = AppdynamicsAgent.getTransaction().startExitCall("TCP Server Call", "CUSTOM", "CUSTOM",false);
        getLogger().info(""+System.identityHashCode(o));
        exitCall.stash(o);

        return null;
    }

    @Override
    public void onMethodEnd(Object o, Object o1, String s, String s1, Object[] objects, Throwable throwable, Object o2) {
        ExitCall exitCall = AppdynamicsAgent.fetchExitCall(o1);
        exitCall.end();
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