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
public class TCPServerEntryInterceptorStart extends AGenericInterceptor {
    IReflector innerPayload = null;

    //rx.subjects.AsyncSubject

    private static final String CLASS_TO_INSTRUMENT = "com.appdynamics.selab.tcpclient.TCPServer";
    private static final String METHOD_TO_INSTRUMENT = "receiveData";

    public TCPServerEntryInterceptorStart(){
        super();
        innerPayload = getNewReflectionBuilder()
                .accessFieldValue("innerPayload", true).build();
    }

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {

        return null;
    }

    @Override
    public void onMethodEnd(Object o, Object o1, String s, String s1, Object[] objects, Throwable throwable, Object o2) {

        try{
            Map x = (Map)innerPayload.execute(o.getClass().getClassLoader(), objects[0]);
            String correlationHeader= x.get(AppdynamicsAgent.TRANSACTION_CORRELATION_HEADER).toString();
            AppdynamicsAgent.startTransaction("TEST",correlationHeader,"SERVLET",false);
        }
        catch(Exception e){

        }
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