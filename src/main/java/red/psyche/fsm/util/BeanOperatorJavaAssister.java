package red.psyche.fsm.util;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author inthendsun
 */
public class BeanOperatorJavaAssister {
	
	@SuppressWarnings("unchecked")
	public static synchronized <T> Class<T> compile(Class<?> T,String qName,CharSequence javaSource)  {
		String localAppend = "public Object append(Object one,Object two) { return append(("+T.getName()+")one,("+T.getName()+")two);}";
		String localOverride = "public void override(Object one,Object two) { override(("+T.getName()+")one,("+T.getName()+")two);}";
		String localCopyMissing = "public void copyMissing(Object one,Object two) { copyMissing(("+T.getName()+")one,("+T.getName()+")two);}";
		ClassPool classPool = ClassPool.getDefault();
		classPool.insertClassPath(new ClassClassPath(BeanOperatorJavaAssister.class));

		CtClass cc = classPool.makeClass("red.psyche.dynamic." + T.getSimpleName() +"Op");
		
		try {
			cc.setInterfaces(new CtClass[]{classPool.get(BeanOperator.class.getName())});
			Map<Method,Method> beanMethodMap = BeanOperatorSourceCreater.getBeanMethodMap(T);
			//interface append method
			cc.addMethod(CtNewMethod.make(BeanOperatorSourceCreater.getAppendMethodCode(T, beanMethodMap).toString(), cc));
			//local append method
			cc.addMethod(CtNewMethod.make(localAppend, cc));
			
			//interface override method
			cc.addMethod(CtNewMethod.make(BeanOperatorSourceCreater.getOverrideCode(T, beanMethodMap).toString(), cc));
			//local override method
			cc.addMethod(CtNewMethod.make(localOverride, cc));
			
			//interface override method
			cc.addMethod(CtNewMethod.make(BeanOperatorSourceCreater.getCopyMissingCode(T, beanMethodMap).toString(), cc));
			//local override method
			cc.addMethod(CtNewMethod.make(localCopyMissing, cc));

			return cc.toClass();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
