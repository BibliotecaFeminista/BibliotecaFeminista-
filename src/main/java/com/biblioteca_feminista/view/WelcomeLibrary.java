package com.biblioteca_feminista.view;

public class WelcomeLibrary {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void printWelcomeMessage() {
        String border = ANSI_YELLOW + "══════════════════════════════════════════════════════════════════════════════"
                + ANSI_RESET;

        String booksAscii = ANSI_CYAN + """
                     __________        __________        __________
                    /________/|      /________/|      /________/|
                   |  ____  | |     |  ____  | |     |  ____  | |
                   | |LIBRO| | |    | |LIBRO| | |    | |LIBRO| | |
                   | |____| | /     | |____| | /     | |____| | /
                   |/______|/       |/______|/       |/______|/
                """ + ANSI_RESET;

        String title = ANSI_BOLD + ANSI_MAGENTA + "✨ ¡Bienvenid@ a Palabras Libres! ✨" + ANSI_RESET;
        String subtitle = ANSI_WHITE + "Donde cada historia encuentra su voz y cada palabra respira libertad."
                + ANSI_RESET;
        String callToAction = ANSI_WHITE + "Abre un libro… y abre el mundo. 📚" + ANSI_RESET;

        System.out.println(booksAscii);
        System.out.println(border);
        System.out.println("        " + title);
        System.out.println("        " + subtitle);
        System.out.println("        " + callToAction);
        System.out.println(border);
    }
}
