package com.appdynamics;

import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.contexts.ISDKDataContext;
import com.appdynamics.instrumentation.sdk.contexts.ISDKUserContext;
import com.appdynamics.instrumentation.sdk.template.AEntry;
import com.appdynamics.instrumentation.sdk.template.AExit;
import com.appdynamics.instrumentation.sdk.template.AMidDataCollector;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.IReflector;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.ReflectorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by haojun.li on 7/10/18
 */
public class Test extends AEntry {
    IReflector id = null;

    private static final String CLASS_TO_INSTRUMENT = "rx.subjects.AsyncSubject";
    private static final String METHOD_TO_INSTRUMENT = "<init>";

    public Test(){
        super();
        id = getNewReflectionBuilder()
                .invokeInstanceMethod("id", true).build();
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
        return null;
    }

    @Override
    public String getBusinessTransactionName(Object o, String s, String s1, Object[] objects, ISDKUserContext isdkUserContext) throws ReflectorException {
        return "Default Name";
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