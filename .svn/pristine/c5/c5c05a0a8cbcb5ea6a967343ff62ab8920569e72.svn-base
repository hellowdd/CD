package com.bocom.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class Json2Map {

	public static Map<String, Object> toHashMap(Object object) {
		Map<String, Object> data = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			data.put(key, jsonObject.get(key));
		}
		return data;
	}

}
