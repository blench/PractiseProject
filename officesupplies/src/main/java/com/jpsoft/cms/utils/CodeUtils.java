package com.jpsoft.cms.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 项目: 作者:zq 描述:
 */
public class CodeUtils {
	public static void DecoderStrPropertyForUTF8(Object entity) {
		BeanInfo bean;

		try {
			bean = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] pds = bean.getPropertyDescriptors();

			for (PropertyDescriptor pd : pds) {
				if (pd.getPropertyType().equals(String.class)) {
					String str = BeanUtils.getProperty(entity, pd.getName());
					if (str != null && str.length() != 0) {
						BeanUtils.setProperty(entity, pd.getName(),
								URLDecoder.decode(str, "UTF-8"));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final String GET = "get";
	public static final String IS = "is";
	public static final String SET = "set";

	public static Method[] getPublicGetter(Object obj, Class... returnType) {
		Class clazz = obj.getClass();
		Method[] methodArr = clazz.getDeclaredMethods();
		List accum = new ArrayList();
		for (int i = 0; i < methodArr.length; i++) {
			Method method = methodArr[i];
			if (Modifier.isPublic(method.getModifiers())) {
				if (method.getName().startsWith(GET)
						|| method.getName().startsWith(IS)) {
					if (returnType.length > 0) {
						if (method.getReturnType().isAssignableFrom(
								returnType[0])) {
							accum.add(method);
						}
					} else {
						accum.add(method);
					}
				}
			}
		}
		Method[] retMethod = new Method[accum.size()];
		return (Method[]) accum.toArray(retMethod);
	}

	/**
	 * 将表单中pojo的非空值写入到target中
	 * 
	 * @param target
	 *            数据库中pojo
	 * @param obj
	 *            表单中pojo
	 * @throws Exception
	 */
	public static void extendPojo(Object target, Object obj) throws Exception {
		if (target != null) {
			Method[] methodArr = getPublicGetter(obj);
			for (int i = 0; i < methodArr.length; i++) {
				Method method = methodArr[i];
				Object value = method.invoke(obj, new Object[] {});
				if (value != null) {
					String name = method.getName();
					if (name.startsWith(IS))
						name = name.substring(2, method.getName().length());
					else
						name = name.substring(3, method.getName().length());
					Method setter = null;
					if (value instanceof String)
						try {
							setter = target.getClass().getDeclaredMethod(
									SET + name, String.class);
						} catch (NoSuchMethodException e) {
							continue;
						}
					else if (value instanceof Date)
						try {
							setter = target.getClass().getDeclaredMethod(
									SET + name, Date.class);
						} catch (NoSuchMethodException e) {
							continue;
						}
					else if (value instanceof Boolean) {
						try {
							setter = target.getClass().getDeclaredMethod(
									SET + name, Boolean.class);
						} catch (NoSuchMethodException e) {
							try {
								setter = target.getClass().getDeclaredMethod(
										SET + name, boolean.class);
							} catch (NoSuchMethodException ne) {
								continue;
							}
						}
					} else if (value instanceof Integer) {
						try {
							setter = target.getClass().getDeclaredMethod(
									SET + name, Integer.class);
						} catch (NoSuchMethodException e) {
							try {
								setter = target.getClass().getDeclaredMethod(
										SET + name, int.class);
							} catch (NoSuchMethodException ne) {
								continue;
							}
						}
					} else if (value instanceof BigDecimal) {
						try {
							setter = target.getClass().getDeclaredMethod(
									SET + name, BigDecimal.class);
						} catch (NoSuchMethodException e) {
							continue;
						}
					} else {
						// add by zq 2009-8-5 小写第一个字母
						BeanUtils.setProperty(target, name.substring(0, 1)
								.toLowerCase() + name.substring(1), value);

						continue;
					}

					setter.invoke(target, value);
				}
			}
		}
	}

	public static String[] padding(String[] arr, String c) {
		// TODO Auto-generated method stub
		String[] newArr = new String[arr.length];

		for (int i = 0; i < arr.length; i++) {
			newArr[i] = c + arr[i] + c;
		}

		return newArr;
	}

	public static String join(String[] arr, String c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(c);
			}

			sb.append(arr[i]);
		}

		return sb.toString();
	}

	public static String join(List<String> list, String padding, String sep) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			sb.append(padding);
			sb.append(list.get(i));
			sb.append(padding);
			sb.append(sep);
		}

		if (list.size() > 0) {
			return sb.toString().substring(0, sb.length() - 1);
		} else {
			return "";
		}
	}
	
	public static String wrapperParam(Object param){
		String ret="";

		if (param!=null) {
			if(param instanceof Integer || param instanceof BigDecimal){
				ret = param.toString();
			}
			else if(param instanceof Date){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ret = "'" + sdf.format((Date)param) + "'";
			}
			else{
				ret = "'" + param.toString() + "'";
			}
		}
		else{
			ret = null;
		}
		
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(Integer.toHexString(16));
	}
}