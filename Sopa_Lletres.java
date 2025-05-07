import java.util.Scanner;

public class Sopa_Lletres {
    Scanner entrada = new Scanner(System.in);
    int menuDiccionari = 0;
    int menuPrincipal = 0;
    String paraules[] = { "casa", "gat", "sol", "flor", "arbre", "lluna", "mar", "cel", "vent", "foc" };
    String paraulesSeleccionades[] = new String[5];
    String paraulaCorrecta[] = new String[5];
    int punter = 0;
    char sopa[][] = new char[15][10];
    int mode = 0;
    char sortir = ' ';
    boolean paraulesEncertades = false;
    int opcio_correcte = 0;
    boolean admin = false;
    public long tempsInicial;
    public long tempsFinal;

    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        Sopa_Lletres main2 = new Sopa_Lletres();
        main2.main2();
    }

    public void main2() {
        limiparSopa();
        System.out.println("Benvingut a la sopa de lletres!!!");
        do {
            MenuPrincipalPrint();
            OpcioPrincipal();
        } while (sortir() == false);
    }

    public void limiparSopa() {
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa[i].length; j++) {
                sopa[i][j] = '-';
            }
        }
        paraulesEncertades = false;
        menuDiccionari = 0;
        menuPrincipal = 0;
        punter = 0;
        opcio_correcte = 0;
        mode = 0;
        sortir = ' ';
        admin = false;
        for (int x = 0; x < paraulesSeleccionades.length; x++) {
            paraulaCorrecta[x] = null;
            paraulesSeleccionades[x] = null;
        }
    }

    public void MenuPrincipalPrint() {
        System.out.println("1. Mantenir el Diccionari de paraules del joc");
        System.out.println("2. Jugar");
        System.out.println("3. Sortir");
        System.out.println("Selecciona una opció:");
        menuPrincipal = entrada.nextInt();
    }

    public void MantenirDiccionari() {
        System.out.println("1. Afegir 10 paraules");
        System.out.println("2. Modificar una paraula");
        System.out.println("3. Tornar al menú principal");
        System.out.println("Selecciona una opció:");
        menuDiccionari = entrada.nextInt();
        OpcioMantenirDiccionari();
    }

    public void OpcioPrincipal() {
        switch (menuPrincipal) {
            case 1:
                System.out.println("Has seleccionat mantenir el diccionari");
                MantenirDiccionari();
                break;
            case 2:
                System.out.println("Has seleccionat jugar");
                Jugar();
                seleccionarMode();
                System.out.println();
                while (paraulesEncertades == false) {
                    printarSopa();
                    comencarJoc();
                    comprobarParaulesPerFinalitzar();
                    if (paraulesEncertades == false) {
                        System.out.println("Vols sortir del Joc? (S/N)");
                        sortir = entrada.next().charAt(0);
                        sortir = Character.toUpperCase(sortir);
                        if (sortir == 'S') {
                            menuPrincipal = 3;
                        }
                        seleccionarMode();
                    } else {
                        System.out.println("Has trobat totes les paraules!!");
                        pararCronometre();
                        limiparSopa();
                        break;
                    }
                }
                break;
            case 3:
                System.out.println("Has seleccionat sortir");
                break;
            case 7:
                System.out.println("Has seleccionat el mode administrador");
                admin = true;
                break;
            default:
                System.out.println("Opció incorrecta");
                break;
        }
    }

    public void OpcioMantenirDiccionari() {
        switch (menuDiccionari) {
            case 1:
                System.out.println("Has seleccionat afegir 10 paraules");
                afegirParaules();
                PrintarParaulesIntroduides();
                break;
            case 2:
                System.out.println("Has seleccionat modificar una paraula");
                ModificarParaula();
                System.out.println("Paraula afegida correctament!!");
                break;
            case 3:
                System.out.println("Has seleccionat tornar al menú principal");
                break;
            default:
                System.out.println("Opció incorrecta");
                break;
        }
    }

    public void afegirParaules() {
        System.out.println("Introdueix 10 paraules:");
        for (int x = 0; x < paraules.length; x++) {
            System.out.println("Introdueix la paraula " + (x + 1) + ":");
            String paraula = entrada.next();
            while (contenirNumeros(paraula)) {
                System.out.println("La paraula no pot contenir números. Introdueix una paraula diferent:");
                paraula = entrada.next();
            }
            while (paraulaRepetida(paraula)) {
                System.out.println("La paraula ja existeix. Introdueix una paraula diferent:");
                paraula = entrada.next();
            }
            paraules[x] = paraula;
        }
    }

    public boolean paraulaRepetida(String paraula) {
        for (String p : paraules) {
            if (p.equals(paraula)) {
                return true;
            }
        }
        return false;
    }

    public boolean paraulaRepetidaSeleccionada(String paraula) {
        for (String p : paraulesSeleccionades) {
            if (p != null && p.equals(paraula)) {
                return true;
            }
        }
        return false;
    }

    public boolean contenirNumeros(String paraula) {
        for (int i = 0; i < paraula.length(); i++) {
            if (Character.isDigit(paraula.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void PrintarParaulesIntroduides() {
        System.out.println();
        for (int x = 0; x < paraules.length; x++) {
            System.out.println("Paraula " + (x + 1) + ": " + paraules[x]);
        }
    }

    public void ModificarParaula() {
        PrintarParaulesIntroduides();
        System.out.println("Quina paraula vols modificar? (1-10)");
        int numParaula = entrada.nextInt() - 1;
        boolean correte = false;
        while (!correte) {
            if (numParaula >= 0 && numParaula < 10) {
                correte = true;
            } else {
                System.out.println("Número incorrecte. Introdueix un número entre 1 i 10:");
                numParaula = entrada.nextInt();
            }
        }
        System.out.println("La paraula que vols modificar és: " + paraules[numParaula]);
        System.out.println("Introdueix la nova paraula:");
        String paraula = entrada.next();
        while (contenirNumeros(paraula) || paraulaRepetida(paraula)) {
            if (paraulaRepetida(paraula)) {
                System.out.println("La paraula ja existeix. Introdueix una paraula diferent:");
            }
            if (contenirNumeros(paraula)) {
                System.out.println("La paraula no pot contenir números. Introdueix una paraula diferent:");
            }
            paraula = entrada.next();
        }
        paraules[numParaula] = paraula;
    }

    public void Jugar() {
        System.out.println("Seleccionant les paraules per jugar...");
        for (int x = 0; x < paraulesSeleccionades.length; x++) {
            String paraula;
            do {
                int numrandom = (int) (Math.random() * paraules.length);
                paraula = paraules[numrandom];
            } while (paraulaRepetidaSeleccionada(paraula));
            paraulesSeleccionades[x] = paraula;
        }
        System.out.println("Les paraules seleccionades són:");

        for (int i = 0; i < paraulesSeleccionades.length; i++) {
            System.out.println((i + 1) + ": " + paraulesSeleccionades[i]);
        }
        System.out.println();
        System.out.println();
        JugarSopa();
        comencarCronometre();
    }

    public void JugarSopa() {
        for (int i = 0; i < paraulesSeleccionades.length; i++) {
            String paraula = paraulesSeleccionades[i];
            int allargada = paraula.length();
            boolean colocada = false;

            while (!colocada) {
                int dir = (int) (Math.random() * 8);
                int fila = (int) (Math.random() * 15);
                int col = (int) (Math.random() * 10);
                boolean espaiLliure = false;

                switch (dir) {
                    case 0: // Horitzontal dreta
                        if (col + allargada <= 10) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila][col + j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 1: // Horitzontal esquerra
                        if (col - allargada + 1 >= 0) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila][col - j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 2: // Vertical amunt
                        if (fila - allargada + 1 >= 0) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila - j][col] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 3: // Vertical avall
                        if (fila + allargada <= 15) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila + j][col] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 4: // Diagonal dreta amunt
                        if (fila - allargada + 1 >= 0 && col + allargada <= 10) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila - j][col + j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 5: // Diagonal dreta avall
                        if (fila + allargada <= 15 && col + allargada <= 10) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila + j][col + j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 6: // Diagonal esquerra amunt
                        if (fila - allargada + 1 >= 0 && col - allargada + 1 >= 0) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila - j][col - j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                    case 7: // Diagonal esquerra avall
                        if (fila + allargada <= 15 && col - allargada + 1 >= 0) {
                            espaiLliure = true;
                            for (int j = 0; j < allargada; j++) {
                                if (sopa[fila + j][col - j] != '-') {
                                    espaiLliure = false;
                                    break;
                                }
                            }
                        }
                        break;
                }

                if (espaiLliure) {
                    switch (dir) {
                        case 0:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila][col + j] = paraula.charAt(j);
                            }
                            break;
                        case 1:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila][col - j] = paraula.charAt(j);
                            }
                            break;
                        case 2:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila - j][col] = paraula.charAt(j);
                            }
                            break;
                        case 3:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila + j][col] = paraula.charAt(j);
                            }
                            break;
                        case 4:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila - j][col + j] = paraula.charAt(j);
                            }
                            break;
                        case 5:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila + j][col + j] = paraula.charAt(j);
                            }
                            break;
                        case 6:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila - j][col - j] = paraula.charAt(j);
                            }
                            break;
                        case 7:
                            for (int j = 0; j < allargada; j++) {
                                sopa[fila + j][col - j] = paraula.charAt(j);
                            }
                            break;
                    }
                    colocada = true;
                }
            }
        }
        if (admin)
            admin();
    }

    public void admin() {
        System.out.print("\t");
        for (int j = 0; j < sopa[0].length; j++) {
            System.out.print((char) (65 + j) + "\t");
        }
        System.out.println();
        for (int i = 0; i < sopa.length; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < sopa[i].length; j++) {
                if (sopa[i][j] == 0) {
                    System.out.print(" " + "\t");
                } else {
                    System.out.print(sopa[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    public void printarSopa() {
        System.out.print("\t");
        for (int j = 0; j < sopa[0].length; j++) {
            System.out.print((char) (65 + j) + "\t");
        }
        System.out.println();
        for (int i = 0; i < sopa.length; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < sopa[i].length; j++) {
                if (sopa[i][j] == '-') {
                    sopa[i][j] = (char) (Math.random() * 26 + 'a');
                }
                if (Character.isUpperCase(sopa[i][j])) {
                    System.out.print(ANSI_RED + sopa[i][j] + ANSI_RESET + "\t");
                } else {
                    System.out.print(ANSI_GREEN + sopa[i][j] + ANSI_RESET + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
        printarParaulesSeleccionades();
        System.out.println();
    }

    public void printarParaulesSeleccionades() {
        System.out.println("Aquestes son les paraules que has de trobar:");
        for (int i = 0; i < paraulesSeleccionades.length; i++) {
            boolean trobar = false;
            for (int x = 0; x < paraulaCorrecta.length; x++) {
                if (paraulaCorrecta[x] != null) {
                    if (paraulesSeleccionades[i].equals(paraulaCorrecta[x])) {
                        trobar = true;
                    }
                }
            }
            if (trobar != true)
                System.out.println((i + 1) + ": " + paraulesSeleccionades[i]);
        }
    }

    public void seleccionarMode() {
        System.out.println("Ara has de seleccionar el mode de joc:");
        System.out.println("1. Mode Interactiu");
        System.out.println("2. Mode Automàtic");
        System.out.println("Selecciona una opció:");
        mode = entrada.nextInt();
    }

    public void comencarJoc() {
        switch (mode) {
            case 1:
                System.out.println("Has seleccionat el mode interactiu");
                modeInteractiu();
                break;
            case 2:
                System.out.println("Has seleccionat el mode automàtic");
                modeAutomatic();
                break;
            default:
                System.out.println("Opció incorrecta");
                break;
        }
    }

    public void modeInteractiu() {
        System.out.println("Selecciona la paraula que has trobat, ha de ser estil; Bernat-c2-i2:");
        String paraula = entrada.next();
        paraula = paraula.toLowerCase();
        String[] paraulaSeparada = paraula.split("-");
        boolean correcte = comprovarColumnes(paraulaSeparada);
        if (correcte) {
            comprobarParaulesPerFinalitzar();
        }
    }

    public boolean comprovarColumnes(String paraulaSeparada[]) {
        int ubicacio = 0;
        boolean correcte = true;
        if (paraulaSeparada.length == 3) {
            int inicialFila = Integer.parseInt(paraulaSeparada[1].substring(1)) - 1;
            int inicialColumna = paraulaSeparada[1].toLowerCase().charAt(0) - 'a';
            int finalFila = Integer.parseInt(paraulaSeparada[2].substring(1)) - 1;
            int finalColumna = paraulaSeparada[2].toLowerCase().charAt(0) - 'a';

            if (inicialColumna == finalColumna) {
                ubicacio = 1; // Vertical
            } else if (inicialFila == finalFila) {
                ubicacio = 2; // Horitzontal
            } else if (Math.abs(finalFila - inicialFila) == paraulaSeparada[0].length() - 1
                    && Math.abs(finalColumna - inicialColumna) == paraulaSeparada[0].length() - 1) {
                ubicacio = 3; // Diagonal
            }

            if (inicialFila >= 0 && inicialFila < 15 && inicialColumna >= 0 && inicialColumna < 10 && finalFila >= 0
                    && finalFila < 15 && finalColumna >= 0 && finalColumna < 10) {
                switch (ubicacio) {
                    case 1: // Vertical
                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length
                                    || Character.toLowerCase(sopa[inicialFila + x][inicialColumna]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 3;
                            break;
                        }

                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x < 0
                                    || Character.toLowerCase(sopa[inicialFila - x][inicialColumna]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 2;
                            break;
                        }
                        break;

                    case 2: // Horitzontal
                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialColumna + x >= sopa[0].length
                                    || Character.toLowerCase(sopa[inicialFila][inicialColumna + x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 0;
                            break;
                        }

                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialColumna - x < 0
                                    || Character.toLowerCase(sopa[inicialFila][inicialColumna - x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 1;
                            break;
                        }
                        break;

                    case 3: // Diagonal
                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length || inicialColumna + x >= sopa[0].length
                                    || Character.toLowerCase(sopa[inicialFila + x][inicialColumna + x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 5;
                            break;
                        }

                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length || inicialColumna - x < 0
                                    || Character.toLowerCase(sopa[inicialFila + x][inicialColumna - x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 7;
                            break;
                        }

                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x < 0 || inicialColumna + x >= sopa[0].length
                                    || Character.toLowerCase(sopa[inicialFila - x][inicialColumna + x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 4;
                            break;
                        }

                        correcte = true;
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x < 0 || inicialColumna - x < 0
                                    || Character.toLowerCase(sopa[inicialFila - x][inicialColumna - x]) != Character
                                            .toLowerCase(paraulaSeparada[0].charAt(x))) {
                                correcte = false;
                                break;
                            }
                        }
                        if (correcte) {
                            opcio_correcte = 6;
                            break;
                        }
                        break;
                }

                if (correcte) {
                    System.out.println("La paraula: " + paraulaSeparada[0] + ", és correcta");
                    paraulaCorrecta[punter] = paraulaSeparada[0];
                    marcarParaulaCorrecta(paraulaSeparada, inicialFila, inicialColumna);
                    punter++;
                } else {
                    System.out.println("La paraula és incorrecta");
                }
            } else {
                System.out.println("Posició incorrecta");
                correcte = false;
            }
        } else {
            System.out.println("Format incorrecte");
            correcte = false;
        }
        return correcte;
    }

    public void modeAutomatic() {
        for (String paraula : paraulesSeleccionades) {
            if (!paraulaJaTrobada(paraula)) {
                trobarParaulaAutomatica(paraula);
            }
        }
        comprobarParaulesPerFinalitzar();
    }

    public boolean paraulaJaTrobada(String paraula) {
        for (String p : paraulaCorrecta) {
            if (paraula.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void trobarParaulaAutomatica(String paraula) {
        for (int fila = 0; fila < sopa.length; fila++) {
            for (int col = 0; col < sopa[fila].length; col++) {
                if (Character.toLowerCase(sopa[fila][col]) == paraula.charAt(0)) {
                    int[] direccio = trobarDireccioParaula(paraula, fila, col);
                    if (direccio != null) {
                        paraulaCorrecta[punter] = paraula;
                        marcarParaulaMajuscules(paraula, fila, col, direccio);
                        punter++;
                        System.out.println("Paraula trobada: " + paraula);
                        printarSopa();
                        return;
                    }
                }
            }
        }
    }

    public int[] trobarDireccioParaula(String paraula, int fila, int col) {
        int[][] direccions = {
                { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 },
                { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }
        };

        for (int i = 0; i < direccions.length; i++) {
            if (comprovarParaulaEnDireccio(paraula, fila, col, direccions[i][0], direccions[i][1])) {
                opcio_correcte = i;
                return direccions[i];
            }
        }
        return null;
    }

    public boolean comprovarParaulaEnDireccio(String paraula, int fila, int col, int dirFila, int dirCol) {
        if (!estaDinsSopa(fila, col, dirFila, dirCol, paraula.length())) {
            return false;
        }

        for (int i = 0; i < paraula.length(); i++) {
            char lletraSopa = Character.toLowerCase(sopa[fila + (dirFila * i)][col + (dirCol * i)]);
            if (lletraSopa != paraula.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean estaDinsSopa(int fila, int col, int dirFila, int dirCol, int longitud) {
        int filaFinal = fila + (dirFila * (longitud - 1));
        int colFinal = col + (dirCol * (longitud - 1));

        return filaFinal >= 0 && filaFinal < sopa.length &&
                colFinal >= 0 && colFinal < sopa[0].length;
    }

    public void marcarParaulaMajuscules(String paraula, int fila, int col, int[] direccio) {
        for (int i = 0; i < paraula.length(); i++) {
            int novaFila = fila + (direccio[0] * i);
            int novaCol = col + (direccio[1] * i);
            sopa[novaFila][novaCol] = Character.toUpperCase(sopa[novaFila][novaCol]);
        }
    }

    public boolean sortir() {
        return menuPrincipal == 3;
    }

    public void comprobarParaulesPerFinalitzar() {
        int paraulesTrobades = 0;
        for (String paraula : paraulaCorrecta) {
            if (paraula != null) {
                paraulesTrobades++;
            }
        }
        if (paraulesTrobades == paraulesSeleccionades.length) {
            paraulesEncertades = true;
            menuPrincipal = 0;
        }
    }

    public void marcarParaulaCorrecta(String[] paraulaSeparada, int inicialFila, int inicialColumna) {
        int longitud = paraulaSeparada[0].length();
        switch (opcio_correcte) {
            case 0: // Horitzontal dreta
                for (int i = 0; i < longitud; i++) {
                    if (inicialColumna + i < sopa[0].length) {
                        sopa[inicialFila][inicialColumna + i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 1: // Horitzontal esquerra
                for (int i = 0; i < longitud; i++) {
                    if (inicialColumna - i >= 0) {
                        sopa[inicialFila][inicialColumna - i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 2: // Vertical amunt
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila - i >= 0) {
                        sopa[inicialFila - i][inicialColumna] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 3: // Vertical avall
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila + i < sopa.length) {
                        sopa[inicialFila + i][inicialColumna] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 4: // Diagonal dreta amunt
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila - i >= 0 && inicialColumna + i < sopa[0].length) {
                        sopa[inicialFila - i][inicialColumna + i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 5: // Diagonal dreta avall
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila + i < sopa.length && inicialColumna + i < sopa[0].length) {
                        sopa[inicialFila + i][inicialColumna + i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 6: // Diagonal esquerra amunt
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila - i >= 0 && inicialColumna - i >= 0) {
                        sopa[inicialFila - i][inicialColumna - i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
            case 7: // Diagonal esquerra avall
                for (int i = 0; i < longitud; i++) {
                    if (inicialFila + i < sopa.length && inicialColumna - i >= 0) {
                        sopa[inicialFila + i][inicialColumna - i] = Character.toUpperCase(paraulaSeparada[0].charAt(i));
                    }
                }
                break;
        }
    }

    public void comencarCronometre() {
        tempsInicial = System.currentTimeMillis();
    }

    public void pararCronometre() {
        tempsFinal = System.currentTimeMillis();
        long tempsTotal = (tempsFinal - tempsInicial) / 1000;
        System.out.println("Has trigat " + tempsTotal + " segons en trobar totes les paraules");
        paraulesEncertades = false;
    }
}
