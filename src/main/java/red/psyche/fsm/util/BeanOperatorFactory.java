package red.psyche.fsm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author inthendsun
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class BeanOperatorFactory {
	
	private static Logger log = LoggerFactory.getLogger(BeanOperatorFactory.class);
	
	private static Map<Class<?>, BeanOperator> beanOperatorMap = new ConcurrentHashMap<Class<?>, BeanOperator>();
	
	public static <T> BeanOperator<T> getBeanOperator(Class<?> T) {
		
		BeanOperator<T> operator = beanOperatorMap.get(T);
		if(operator == null) {
			synchronized (T) {
				operator = beanOperatorMap.get(T);
				if(operator == null) {
					try {
						createByJa(T);
					} catch(Throwable e) {
						log.error("createByJa for " + T.getSimpleName() + " error,{}",e.getMessage());
						throw new RuntimeException(e);
					}
				}
			}
			operator = beanOperatorMap.get(T);
		}
		return operator;
	}
	
	/**
	 * 通过javassister生成BeanOp
	 * @param T
	 * @return
	 */
	public static <T> BeanOperator<T> getBeanOperate(Class<?> T) {
		
		BeanOperator<T> beanDiff = beanOperatorMap.get(T);
		if(beanDiff == null) {
			synchronized (beanOperatorMap) {
				if(beanDiff == null) {
					createByJa(T);
				}
			}
			beanDiff = beanOperatorMap.get(T);
		}
		return beanDiff;
	}

	private static void createByJa(Class<?> T) {
		log.info("start createByJa for {}",T.getName());
		StringBuilder sb = getSourceCode(T);
		String sourceCode = sb.toString().trim();
		String qName = SourceCodeParser.getClassQulifiedName(sourceCode);
		
		Class c = null;
		long start = System.currentTimeMillis();
		log.info("create by javassist");
		c =  BeanOperatorJavaAssister.compile(T, qName, sourceCode);
		long now = System.currentTimeMillis();
		log.info("create by javassist success,elapsed time {}ms",now-start);
		
        try {
			beanOperatorMap.put(T, (BeanOperator)(c.newInstance()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static StringBuilder getSourceCode(Class<?> T) {
		long start = System.currentTimeMillis();
		StringBuilder sb = BeanOperatorSourceCreater.getSourceCode(T);
		long now = System.currentTimeMillis();
		log.info("source code for {},elapsed time {}ms",T.getName(),(now - start));
		return sb;
	}
}

class SourceCodeParser {

	private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

	private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s+");

	public static String getClassQulifiedName(String sourceCode) {
		Matcher matcher = PACKAGE_PATTERN.matcher(sourceCode);
		String pkg;
		if (matcher.find()) {
			pkg = matcher.group(1);
		} else {
			pkg = "";
		}
		matcher = CLASS_PATTERN.matcher(sourceCode);
		String cls;
		if (matcher.find()) {
			cls = matcher.group(1);
		} else {
			throw new IllegalArgumentException("No such class name in " + sourceCode);
		}
		String className = pkg != null && pkg.length() > 0 ? pkg + "." + cls : cls;
		return className;

	}
}
