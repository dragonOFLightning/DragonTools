package tk.key_text;

import tk.tools.Tools;

public class KeyText {
    static public String getChatText(String text,boolean random){
        if(random){
            String left = '『' + Tools.getRandomString(4) + "』 ";
            String right = " 『" + Tools.getRandomString(4) + '』';
            return  left + text + right;
        }
        return text;
    }

    static public String
}