package com.kitsrc.watt.utils;

import java.util.*;

public abstract class MapUtil {

    public static List<Map<Object, Object>> convertMapToListMap(Map<String, String> flattenMap, String prefix) {
        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
        for (Map.Entry<String, String> entry : flattenMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.contains(prefix)) {
                String[] keys = key.replace(prefix, "").split("\\.");
                String pre = prefix + keys[0];
                if (keys[0].contains("[")) {
                    String mapKey = key.replace(pre + ".", "");
                    int index = Integer.parseInt(keys[0].replace("[", "").replace("]", ""));
                    list = setList(list, index, mapKey, value);
                }
            }
        }
        return list;
    }

    public static Map<Object, Object> convertMapToMap(Map<String, String> flattenMap, String prefix) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Map.Entry<String, String> entry : flattenMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.contains(prefix)) {
                String[] keys = key.replace(prefix, "").split("\\.");
                String pre = prefix + keys[0];
                String mapKey = key.replace(pre + ".", "");
                map = setMap(map, mapKey, value);
            }
        }
        return map;
    }

    private static List<Map<Object, Object>> setList(List<Map<Object, Object>> targetList, int index, String key,
            String value) {
        List<Map<Object, Object>> list = targetList;
        if (null == list) {
            list = new ArrayList<Map<Object, Object>>();
        }
        while (list.size() <= index) {
            list.add(new HashMap<Object, Object>());
        }
        list.set(index, setMap(list.get(index), key, value));
        return list;
    }

    private static Map<Object, Object> setMap(Map<Object, Object> targetMap, String key, String value) {
        Map<Object, Object> map = targetMap;
        if (null == map) {
            map = new HashMap<Object, Object>();
        }
        if (key.contains("[")) {
            String[] keys = key.split("\\.");
            String listKey = key.substring(0, key.indexOf("["));
            int index = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
            List<Map<Object, Object>> listObj = (List<Map<Object, Object>>) map.get(listKey);
            listObj = setList(listObj, index, key.replace(keys[0] + ".", ""), value);
            map.put(listKey, listObj);
        } else if (key.contains(".")) {
            String[] keys = key.split("\\.");
            String mapKey = keys[0];
            // exclude *.Length
            if (!(map.get(mapKey) instanceof List) && !"Length".equals(keys[1])) {
                map.put(mapKey, setMap((Map<Object, Object>) map.get(mapKey),
                        key.replace(keys[0] + ".", ""), value));
            }
        } else {
            map.put(key, value);
        }
        return map;
    }
    public static List<Map<Object, Object>> toListMap(Map<String, String> flattenMap, String prefix) {

        return MapUtil.convertMapToListMap(flattenMap, prefix);
    }

    public static Map<Object, Object> toMap(Map<String, String> flattenMap, String prefix) {

        return MapUtil.convertMapToMap(flattenMap, prefix);
    }

    public static Object put(Map<String, String> flattenMap, Object object, String[] subKeys, int subKeysIndex) {
        if (subKeysIndex >= subKeys.length) {
            return object;
        }
        String key = subKeys[subKeysIndex];

        if (key.endsWith("]")) {
            int index = parseIndex(key);
            if (index == -1) {
                return null;
            }

            ArrayList<Object> arrayList;

            if (object == null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < subKeysIndex; i++) {
                    sb.append(subKeys[i]).append(".");
                }
                sb.append(key);
                int length = parseLength(flattenMap, sb.toString());
                if (length == -1) {
                    return null;
                }
                arrayList = new ArrayList<Object>(Collections.nCopies(length, null));
            } else {
                arrayList = (ArrayList<Object>) object;
            }

            if (subKeys.length == subKeysIndex + 1) {
                arrayList.set(index, flattenMap.get(stringJoin(".", subKeys)));
                return arrayList;
            } else {
                arrayList.set(index, put(flattenMap, arrayList.get(index), subKeys, subKeysIndex + 1));
                return arrayList;
            }

        } else {
            HashMap<Object, Object> hashMap;
            if (object == null) {
                hashMap = new HashMap<Object, Object>();
            } else {
                hashMap = (HashMap<Object, Object>) object;
            }
            if (subKeys.length == subKeysIndex + 1) {
                hashMap.put(key, flattenMap.get(stringJoin(".", subKeys)));
                return hashMap;
            } else {
                hashMap.put(key, put(flattenMap, hashMap.get(key), subKeys, subKeysIndex + 1));
                return hashMap;
            }
        }
    }

    public static Object putForMap(Map<String, String> flattenMap, Object object, String[] subKeys, int subKeysIndex) {
        if (subKeysIndex >= subKeys.length) {
            return object;
        }
        String key = subKeys[subKeysIndex];

        if (key.endsWith("]")) {
            int index = parseIndex(key);
            if (index == -1) {
                return null;
            }

            if (object != null && !(object instanceof HashMap)) {
                return null;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < subKeysIndex; i++) {
                sb.append(subKeys[i]).append(".");
            }
            sb.append(key);
            int length = parseLength(flattenMap, sb.toString());
            if (length == -1) {
                return null;
            }

            String name = parseArrayName(key);

            ArrayList<Object> arrayList;
            HashMap<Object, Object> hashMap;
            if (object != null) {
                hashMap = (HashMap<Object, Object>) object;

                if (!(hashMap.get(name) instanceof ArrayList)) {
                    arrayList = new ArrayList<Object>(Collections.nCopies(length, null));
                    hashMap.put(name, arrayList);
                } else {
                    arrayList = (ArrayList<Object>) hashMap.get(name);
                }
            } else {
                hashMap = new HashMap<Object, Object>();
                arrayList = new ArrayList<Object>(Collections.nCopies(length, null));
                hashMap.put(name, arrayList);
                object = hashMap;
            }

            if (subKeys.length == subKeysIndex + 1) {
                arrayList.set(index, flattenMap.get(stringJoin(".", subKeys)));
                return object;
            } else {
                arrayList.set(index, putForMap(flattenMap, arrayList.get(index), subKeys, subKeysIndex + 1));
                return object;
            }

        } else {
            HashMap<Object, Object> hashMap;
            if (object == null) {
                hashMap = new HashMap<Object, Object>();
            } else {
                hashMap = (HashMap<Object, Object>) object;
            }
            if (subKeys.length == subKeysIndex + 1) {
                hashMap.put(key, flattenMap.get(stringJoin(".", subKeys)));
                return hashMap;
            } else {
                hashMap.put(key, putForMap(flattenMap, hashMap.get(key), subKeys, subKeysIndex + 1));
                return hashMap;
            }
        }
    }

    public static int parseIndex(String key) {
        int start = key.indexOf("[");
        int end = key.indexOf("]");
        if (start == -1 || end == -1 || end <= start) {
            return -1;
        }

        try {
            return Integer.parseInt(key.substring(start + 1, end));
        } catch (Exception e) {
            return -1;
        }
    }

    public static int parseLength(Map<String, String> flattenMap, String key) {
        int end = key.lastIndexOf("[");
        if (end == -1) {
            return -1;
        }

        try {
            return Integer.parseInt(flattenMap.get(key.substring(0, end) + ".Length"));
        } catch (Exception e) {
            return -1;
        }
    }

    public static String parseArrayName(String key) {
        if (key == null) {
            return null;
        }
        int end = key.lastIndexOf("[");
        if (end == -1) {
            return null;
        }
        return key.substring(0, end);
    }

    public static String stringJoin(String delimiter, String... sequences) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequences.length; i++) {
            sb.append(sequences[i]);
            if (i < sequences.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
