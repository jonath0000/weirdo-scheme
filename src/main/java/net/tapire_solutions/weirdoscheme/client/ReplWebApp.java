package net.tapire_solutions.weirdoscheme.client;

import net.tapire_solutions.weirdoscheme.Environment;
import net.tapire_solutions.weirdoscheme.ReplApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class ReplWebApp implements EntryPoint {
	private static Environment globalEnv = new Environment(null);
	
    public void onModuleLoad() {
    	exportMethods(this);
    }
    
    public static String evalExpression(String expression) {
    	String ret = ReplApp.evaluateLine(expression, globalEnv);
    	GWT.log("Eval:ed to " + ret, null);
    	return ret;
    }
    
    public static String getEnvironment() {
    	return globalEnv.toString();
    }
    
    public static String getHelp() {
    	return ReplApp.getHelp();
    }
    
    private native void exportMethods(ReplWebApp instance) /*-{
    	$wnd.evalExpression =
    		$entry(@net.tapire_solutions.weirdoscheme.client.ReplWebApp::evalExpression(Ljava/lang/String;));
    	$wnd.getEnvironment =
    		$entry(@net.tapire_solutions.weirdoscheme.client.ReplWebApp::getEnvironment());
    	$wnd.getHelp =
    		$entry(@net.tapire_solutions.weirdoscheme.client.ReplWebApp::getHelp());
    	if ($wnd.onGwtLoaded && typeof $wnd.onGwtLoaded == 'function') $wnd.onGwtLoaded();
  	}-*/;

}