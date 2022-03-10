
public class Data {
    private static String text = "";
    private static boolean NewText = false;

    public synchronized static String getText() {
        return text;
    }

    public synchronized static void setText(String text) {
        Data.text = text;
    }

    public synchronized static boolean isNewText() {
        return NewText;
    }

    public synchronized static void setNewText(boolean newText) {
        NewText = newText;
    }
}
