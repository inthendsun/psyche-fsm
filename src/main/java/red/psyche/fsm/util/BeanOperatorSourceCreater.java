package red.psyche.fsm.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author inthendsun
 */
public class BeanOperatorSourceCreater {

	public static StringBuilder getSourceCode(Class<?> T) {

		Map<Method, Method> beanMethodMap = BeanOperatorSourceCreater.getBeanMethodMap(T);
		StringBuilder sb = new StringBuilder();

		sb.append("package red.psyche.dyncompile;\n");
		sb.append("public class ").append(T.getSimpleName()).append("Op").append(" implements ")
				.append(BeanOperator.class.getName().replaceFirst("interface ", "")).append("<").append(T.getName())
				.append("> {\n");

		sb.append(getAppendMethodCode(T, beanMethodMap));
		sb.append(getOverrideCode(T, beanMethodMap));
		sb.append(getCopyMissingCode(T, beanMethodMap));

		sb.append("}\n");
		return sb;
	}

	public static StringBuilder getAppendMethodCode(Class<?> T, Map<Method, Method> beanMethodMap) {

		Set<Method> beanMethodSet = beanMethodMap.keySet();
		Iterator<Method> beanMethodIter = beanMethodSet.iterator();
		StringBuilder sb = new StringBuilder();
		// append 方法
		sb.append("public " + T.getName() + " append(" + T.getName() + " one, " + T.getName() + " two) {\n");
		sb.append(T.getName() + " append = new " + T.getName() + "();\n");
		while (beanMethodIter.hasNext()) {
			Method getMethod = (Method) beanMethodIter.next();
			Method setMethod = beanMethodMap.get(getMethod);
			if (!getMethod.getReturnType().isPrimitive()) {
				sb.append("if(two." + getMethod.getName() + "() != null && !two." + getMethod.getName()
						+ "().equals(one." + getMethod.getName() + "())) {\n");
				sb.append("    append." + setMethod.getName() + "(two." + getMethod.getName() + "());\n");
				sb.append("}\n");
			} else {
				sb.append("if(two." + getMethod.getName() + "() != (one." + getMethod.getName() + "())) {\n");
				sb.append("    append." + setMethod.getName() + "(two." + getMethod.getName() + "());\n");
				sb.append("}\n");
			}

		}
		sb.append("return append;\n");

		sb.append("}\n");
		return sb;
	}

	public static StringBuilder getOverrideCode(Class<?> T, Map<Method, Method> beanMethodMap) {
		Set<Method> beanMethodSet = beanMethodMap.keySet();
		Iterator<Method> beanMethodIter = beanMethodSet.iterator();

		StringBuilder sb = new StringBuilder();
		// override方法
		sb.append("public void override(" + T.getName() + " one, " + T.getName() + " two) {\n");
		while (beanMethodIter.hasNext()) {
			Method getMethod = (Method) beanMethodIter.next();
			Method setMethod = beanMethodMap.get(getMethod);
			if (!getMethod.getReturnType().isPrimitive()) {
				sb.append("if(two." + getMethod.getName() + "() != null && !two." + getMethod.getName()
						+ "().equals(one." + getMethod.getName() + "())) {\n");
				sb.append("    one." + setMethod.getName() + "(two." + getMethod.getName() + "());\n");
				sb.append("}\n");
			} else {
				sb.append("if(two." + getMethod.getName() + "() != (one." + getMethod.getName() + "())) {\n");
				sb.append("    one." + setMethod.getName() + "(two." + getMethod.getName() + "());\n");
				sb.append("}\n");
			}
		}
		sb.append("}\n");
		// end

		return sb;
	}
	
	public static StringBuilder getCopyMissingCode(Class<?> T, Map<Method, Method> beanMethodMap) {
		Set<Method> beanMethodSet = beanMethodMap.keySet();
		Iterator<Method> beanMethodIter = beanMethodSet.iterator();

		StringBuilder sb = new StringBuilder();
		// copyMiss方法
		sb.append("public void copyMissing(" + T.getName() + " one, " + T.getName() + " two) {\n");
		while (beanMethodIter.hasNext()) {
			Method getMethod = (Method) beanMethodIter.next();
			Method setMethod = beanMethodMap.get(getMethod);
			if (!getMethod.getReturnType().isPrimitive()) {
				sb.append("if(two." + getMethod.getName() + "() != null && one." + getMethod.getName()+ "() == null) {\n");
				sb.append("    one." + setMethod.getName() + "(two." + getMethod.getName() + "());\n");
				sb.append("}\n");
			}
		}
		sb.append("}\n");
		// end

		return sb;
	}

	public static Map<Method, Method> getBeanMethodMap(Class<?> T) {
		Method[] mArr = T.getMethods();
		Map<Method, Method> beanMethodMap = new HashMap<Method, Method>();

		Map<String, Method> mapMethod = new HashMap<String, Method>();
		for (int i = 0; i < mArr.length; i++) {
			Method m = mArr[i];
			String name = m.getName();
			if ((name.startsWith("get") && m.getReturnType() != void.class && m.getParameterTypes().length == 0)
					|| (name.startsWith("set") && m.getReturnType() == void.class && m.getParameterTypes().length == 1)
					|| (name.startsWith("is") && m.getReturnType() != void.class
							&& m.getParameterTypes().length == 0)) {
				mapMethod.put(name, mArr[i]);
			}
		}

		Set<String> names = mapMethod.keySet();
		Iterator<String> iter = names.iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			if (name.startsWith("get")) {
				Method setMethod = mapMethod.get("set" + name.substring(3));
				if (setMethod != null) {
					Method getMethod = mapMethod.get(name);
					beanMethodMap.put(getMethod, setMethod);
				}
			} else if (name.startsWith("is")) {
				Method setMethod = mapMethod.get("set" + name.substring(2));
				if (setMethod != null) {
					Method getMethod = mapMethod.get(name);
					beanMethodMap.put(getMethod, setMethod);
				}
			}
		}
		return beanMethodMap;
	}
}
