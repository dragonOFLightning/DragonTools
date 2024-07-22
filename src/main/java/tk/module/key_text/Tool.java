package tk.module.key_text;

import tk.tools.Tools;

final class Tool {
    static String getChatText(String text, boolean random) {
        if (random) {
            String left = "『" + Tools.getRandomString(4) + "』 ";
            String right = " 『" + Tools.getRandomString(4) + "』";
            return left + text + right;
        }
        return text;
    }
}
