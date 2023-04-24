public class App {

    // I can make it into classes but I thought for reading purposes I think it's
    // easier to read in this state.

    static char[] referenceTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '(', ')', '*',
            '+', ',', '-', '.', '/' };
    private static char offSet = 'F';

    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";
        encode(plainText);
        String encodedText1 = "FC/GGJ RJMG.";
        String encodedText2 = "BGDKKN VNQKC";
        decode(encodedText2);

    }

    public static String encode(String plainText) {
        // using common code cause same logic
        System.out.println(App.offSet + App.commonCode(true, plainText));
        return App.offSet + App.commonCode(true, plainText);
    }

    public static String decode(String encodedText) {
        // using common code cause same logic but reversed, just need to get the first
        // char.
        App.offSet = App.getFirstChar(encodedText);
        String newText = encodedText.substring(1);
        System.out.println(App.offSet + App.commonCode(false, encodedText));
        return commonCode(false, newText);
    }

    public static int findReferenceTableIndex(char[] referenceTable, char offSet) {
        // to decouple from common code
        int index = 0;
        for (int i = 0; i < referenceTable.length; i++) {
            if (referenceTable[i] == offSet) {
                index = i;
            }
        }
        return index;
    }

    public static char getFirstChar(String encodedText) {
        char firstChar = encodedText.charAt(0);
        return firstChar;
    }

    public static String removeFirstChar(String encodedText) {
        return encodedText.substring(1);
    }

    public static String commonCode(Boolean isEncode, String text) {
        char[] arr = text.toUpperCase().toCharArray();
        char[] newArr = new char[text.length()];
        int diff, turnaround, jumps;
        jumps = App.findReferenceTableIndex(App.referenceTable, App.offSet);
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < referenceTable.length; j++) {
                if (arr[i] == referenceTable[j] && j < 43) {
                    if (isEncode == true) {
                        diff = j - jumps;
                    } else {
                        diff = j + jumps;
                    }
                    if (diff > 0 && diff < referenceTable.length) {
                        newArr[i] = referenceTable[diff];
                    } else if (diff < 0) {
                        turnaround = referenceTable.length + diff;
                        newArr[i] = referenceTable[turnaround];
                    } else if (diff > referenceTable.length) {
                        turnaround = diff - referenceTable.length;
                        newArr[i] = referenceTable[turnaround];
                    }
                } else if (arr[i] != referenceTable[j]) {
                    newArr[i] = arr[i];
                }
            }
        }
        String encodedtext = new String(newArr);
        System.out.println(encodedtext);
        return encodedtext;
    }
}
