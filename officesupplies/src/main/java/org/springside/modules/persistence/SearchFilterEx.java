package org.springside.modules.persistence;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;

public class SearchFilterEx {
	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilterEx(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (value==null) {
				continue;
			}

			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
	
	public static Sort parseSort(String sortType){
		Sort sort = null;
		
		if(sortType!=null){
			String[] arr = sortType.split(",");
			
			for (int i = 0; i < arr.length; i++) {
				String[] pair = arr[i].split(" ");
				
				if(pair.length==2){
					if(sort==null){
						sort = new Sort(parseDirection(pair[1]), pair[0]);	
					}
					else{
						sort = sort.and(new Sort(parseDirection(pair[1]), pair[0]));
					}
				}
			}
		}
		
		return sort;
	}

	public static Direction parseDirection(String drection){
		if (drection.toLowerCase().equals("desc")) {
			return Direction.DESC;
		}
		else{
			return Direction.ASC;
		}
	}
}