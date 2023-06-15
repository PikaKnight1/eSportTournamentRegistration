package helpers;

public class WebMessage {

    //pola prywatne
    private MessageType typeOfMessage;
    private String[] content;

    /**
     * Getter dla typu wiadomości
     *
     * @return typ wiadomości w postaci enuma (MessageType)
     */
    public MessageType getTypeOfMessage() {
        return typeOfMessage;
    }

    /**
     * Getter zwracający zawartość wiadomośći
     *
     * @return tablicę przechowującą wiadomośći w formie String
     */
    public String[] getContent() {
        return content;
    }

    /**
     * Konstruktor WebMessage
     *
     * @param typeOfMessage typ wiadomości w postaci enuma
     * @param content tablica przechowująca zawartość wiadomości
     */
    public WebMessage(MessageType typeOfMessage, String[] content) {
        this.typeOfMessage = typeOfMessage;
        this.content = content;
    }

    /**
     * Metoda kodująca wiadomość do Stringa: Przykładowa wiadomość:
     * Typ;Arg1;Arg2;Arg3;Arg...
     *
     * @return
     */
    public String encode() {
        String output = getTypeOfMessage().toString();
        for (String item : getContent()) {
            output += ";" + item;
        }
        System.out.println("Zakodowana wiadomosc: " + output);
        return output;
    }

    /**
     * Metoda dekodująca WebMessage z Stringa
     *
     * @param encoded string zawierający zakodowaną wiadomość
     * @return wiadomość w formie WebMessage
     */
    public static WebMessage decode(String encoded) {
        var message = encoded.split(";");
        MessageType type = MessageType.valueOf(message[0]);
        String[] args = new String[message.length - 1];

        for (int i = 1; i < message.length; i++) {
            args[i - 1] = message[i];
        }
        System.out.println("Dekoduje wiadomość " + encoded);

        return new WebMessage(type, args);
    }
}
