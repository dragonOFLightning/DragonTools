package tk.tools;

import java.util.HashMap;

public class Keys {
    private Keys() {
    }

    static final private String[] charKeyName = {"Tab", "[", "]", ";", "\"", "<", ">", "/", "=", "-", "\\"};
    static final private Integer[] charKeyValue = {15, 26, 27, 39, 40, 51, 52, 53, 13, 12, 43};

    static final private String[] line1KeyName = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    static final private Integer[] line1KeyValue = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25};

    static final private String[] line2KeyName = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    static final private Integer[] line2KeyValue = {30, 31, 32, 33, 34, 35, 36, 37, 38};

    static final private String[] line3KeyName = {"Z", "X", "C", "V", "B", "N", "M"};
    static final private Integer[] line3KeyValue = {44, 45, 46, 47, 48, 49, 50};

    static final private HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
    static final private HashMap<Integer, String> valueMap = new HashMap<Integer, String>();

    static {
        addKeys(charKeyName, charKeyValue);
        addKeys(line1KeyName, line1KeyValue);
        addKeys(line2KeyName, line2KeyValue);
        addKeys(line3KeyName, line3KeyValue);

        addValues(charKeyName, charKeyValue);
        addValues(line1KeyName, line1KeyValue);
        addValues(line2KeyName, line2KeyValue);
        addValues(line3KeyName, line3KeyValue);
    }

    static private void addKeys(String[] keyNames, Integer[] keyValues) {
        for (int i = 0; i < keyNames.length; i++) {
            Keys.keyMap.put(keyNames[i], keyValues[i]);
        }
    }

    static private void addValues(String[] keyNames, Integer[] keyValues) {
        for (int i = 0; i < keyNames.length; i++) {
            Keys.valueMap.put(keyValues[i], keyNames[i]);
        }
    }

    static public int getKeyValue(String keyName) {
        return keyMap.get(keyName);
    }

    static public String getKeyName(int keyValue) {
        return valueMap.get(keyValue);
    }
}
