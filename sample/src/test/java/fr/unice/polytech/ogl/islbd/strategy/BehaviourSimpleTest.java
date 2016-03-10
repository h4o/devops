package fr.unice.polytech.ogl.islbd.strategy;

import junit.framework.TestCase;
import junit.framework.TestSuite;

//TODO : Implémenter les suites de tests !!
public class BehaviourSimpleTest extends TestCase {
	public BehaviourSimpleTest(String method) {
		super(method);
	}
	
	public static TestSuite suite(){
		TestSuite suite = new TestSuite();
		suite.addTestSuite(BSGetRandomDirectionTests.class);
		
		return suite;
	}

}