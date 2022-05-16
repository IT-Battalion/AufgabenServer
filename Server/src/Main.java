/**
 * Die Main Klasse für den Server. Wird als erstes Argument kein Port beim
 * starten Übergeben wird der Standard Port 2445 verwendet.
 *
 * @author Patrick Elias
 * @version 2022-05-16
 */
public class Main {
    public static void main(String[] args) {
        String port = args.length == 0 ? "2445" : args[0];

    }
}
