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
                        limiparSopa();
                        break;
                        // return;
                    }
                }
                break;

            case 3:
                System.out.println("Has seleccionat sortir");
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
    }

    public void JugarSopa() {
        // 0: horitzontal dreta
        // 1: horitzontal esquerra
        // 2: vertical amunt
        // 3: vertical avall
        // 4: diagonal dreta amunt
        // 5: diagonal dreta avall
        // 6: diagonal esquerra amunt
        // 7: diagonal esquerra avall

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

                // Col·locar la paraula si l'espai està lliure
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
                    colocada = true; // Marcar com a colocada
                }
            }
        }
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
                System.out.print(sopa[i][j] + "\t");
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
            int inicialColumna = paraulaSeparada[1].charAt(0) - 'a';
            int finalFila = Integer.parseInt(paraulaSeparada[2].substring(1)) - 1;
            int finalColumna = paraulaSeparada[2].charAt(0) - 'a';
    
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
                    case 1 -> { // Vertical avall
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length
                                    || sopa[inicialFila + x][inicialColumna] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 3;
                            }
                        }
    
                        if (correcte) {
                            break;
                        } else {
                            correcte = true;
                        }
    
                        // Vertical amunt
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x >= sopa.length
                                    || sopa[inicialFila - x][inicialColumna] != paraulaSeparada[0].charAt(x)) {
                                opcio_correcte = 2;
                                correcte = false;
                                break;
                            }
                        }
                    }
    
                    case 2 -> { // Horitzontal Dreta
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialColumna + x >= sopa[0].length
                                    || sopa[inicialFila][inicialColumna + x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 0;
                            }
                        }
    
                        if (correcte) {
                            break;
                        } else {
                            correcte = true;
                        }
    
                        // Horitzontal esquerra
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialColumna - x < 0
                                    || sopa[inicialFila][inicialColumna - x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 1;
                            }
                        }
                    }
    
                    case 3 -> { // Diagonal
    
                        correcte = true;
                        // De dalt a l'esquerra a baix a la dreta
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length || inicialColumna + x >= sopa[0].length
                                    || sopa[inicialFila + x][inicialColumna + x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 4;
                            }
                        }
    
                        if (correcte) {
                            break;
                        } else {
                            correcte = true;
                        }
    
                        // De dalt a la dreta a baix a l'esquerra
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila + x >= sopa.length || inicialColumna - x < 0
                                    || sopa[inicialFila + x][inicialColumna - x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 5;
                            }
                        }
    
                        if (correcte) {
                            break;
                        } else {
                            correcte = true;
                        }
    
                        // De baix a l'esquerra a dalt a la dreta
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x < 0 || inicialColumna + x >= sopa[0].length
                                    || sopa[inicialFila - x][inicialColumna + x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 6;
                            }
                        }
    
                        if (correcte) {
                            break;
                        } else {
                            correcte = true;
                        }
    
                        // De baix a la dreta a dalt a l'esquerra
                        for (int x = 0; x < paraulaSeparada[0].length(); x++) {
                            if (inicialFila - x < 0 || inicialColumna - x < 0
                                    || sopa[inicialFila - x][inicialColumna - x] != paraulaSeparada[0].charAt(x)) {
                                correcte = false;
                                break;
                            } else {
                                opcio_correcte = 7;
                            }
                        }
                    }
                    default -> correcte = false;
                }
                if (correcte) {
                    System.out.println("La paraula: " + paraulaSeparada[0] + ", és correcta");
                    paraulaCorrecta[punter] = paraulaSeparada[0];
                    paraulaCorrecta(paraulaSeparada, inicialFila, inicialColumna);
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
                boolean trobat = false;
                int punt = 0;
                for (int f = 0; f < sopa.length && !trobat; f++) {
                    for (int c = 0; c < sopa[f].length && !trobat; c++) {
                        if (sopa[f][c] == paraula.charAt(0)) {
                            // Revisa les 8 direccions possibles
                            int[] direccio = trobarDireccio(paraula, f, c);
                            if (direccio != null) {
                                // Determinar opcio_correcte basado en la dirección
                                int filaDir = direccio[0];
                                int colDir = direccio[1];
                                if (filaDir == 0 && colDir == 1) {
                                    opcio_correcte = 0; // Horitzontal dreta
                                } else if (filaDir == 0 && colDir == -1) {
                                    opcio_correcte = 1; // Horitzontal esquerra
                                } else if (filaDir == 1 && colDir == 0) {
                                    opcio_correcte = 3; // Vertical avall
                                } else if (filaDir == -1 && colDir == 0) {
                                    opcio_correcte = 2; // Vertical amunt
                                } else if (filaDir == 1 && colDir == 1) {
                                    opcio_correcte = 5; // Diagonal dreta avall
                                } else if (filaDir == 1 && colDir == -1) {
                                    opcio_correcte = 7; // Diagonal esquerra avall
                                } else if (filaDir == -1 && colDir == 1) {
                                    opcio_correcte = 4; // Diagonal dreta amunt
                                } else if (filaDir == -1 && colDir == -1) {
                                    opcio_correcte = 6; // Diagonal esquerra amunt
                                }
        
                                paraulaCorrecta[punter++] = paraula;
                                trobat = true;
                                // Construir paraulaSeparada
                                String[] paraulaSeparada = { paraula, 
                                    (char) (c + 'A') + "" + (f + 1), 
                                    (char) (c + colDir * (paraula.length() - 1) + 'A') + "" + (f + filaDir * (paraula.length() - 1) + 1) 
                                };
                                paraulaCorrecta(paraulaSeparada, f, c);
                                printarSopa();
                            }
                        }
                    }
                }
                
            }
            comprobarParaulesPerFinalitzar();
        }
        
        private int[] trobarDireccio(String paraula, int f, int c) {
            // Definir les direccions com arrays de dos elements: [direcció de fila, direcció de columna]
            int[][] direccions = {
                {0, 1},  // Horitzontal dreta
                {0, -1}, // Horitzontal esquerra
                {1, 0},  // Vertical avall
                {-1, 0}, // Vertical amunt
                {1, 1},  // Diagonal dreta avall
                {1, -1}, // Diagonal esquerra avall
                {-1, 1}, // Diagonal dreta amunt
                {-1, -1} // Diagonal esquerra amunt
            };
        
            for (int[] direccio : direccions) {
                if (revisarDireccio(paraula, f, c, direccio[0], direccio[1])) {
                    return direccio; // Retorna la direcció si la paraula és trobada
                }
            }
            return null; // Retorna null si la paraula no es troba en cap direcció
        }
        
        public boolean revisarDireccio(String paraula, int fila, int columna, int filaDir, int columnaDir) {
            int llargada = paraula.length();
            for (int k = 0; k < llargada; k++) {
                int novaFila = fila + k * filaDir;
                int novaColumna = columna + k * columnaDir;
                if (novaFila < 0 || novaFila >= sopa.length || novaColumna < 0 || novaColumna >= sopa[0].length || sopa[novaFila][novaColumna] != paraula.charAt(k)) {
                    return false;
                }
            }
            return true;
        }

    public void paraulaCorrecta(String paraulaSeparada[], int inicialFila, int inicialColumna) {
        opcio_correcte = opcio_correcte;
        int longitud = paraulaSeparada[0].length();
        switch (opcio_correcte) {
            case 0 -> { // Horitzontal cap a la dreta
                for (int x = 0; x < longitud; x++) {
                    if (inicialColumna + x < sopa[0].length) {
                        sopa[inicialFila][inicialColumna + x] = Character
                                .toUpperCase(sopa[inicialFila][inicialColumna + x]);
                    }
                }
            }
            case 1 -> { // Horitzontal cap a l'esquerra
                for (int x = 0; x < longitud; x++) {
                    if (inicialColumna - x >= 0) {
                        sopa[inicialFila][inicialColumna - x] = Character
                                .toUpperCase(sopa[inicialFila][inicialColumna - x]);
                    }
                }

            }
            case 2 -> { // Vertical amunt
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila - x >= 0) {
                        sopa[inicialFila - x][inicialColumna] = Character
                                .toUpperCase(sopa[inicialFila - x][inicialColumna]);
                    }
                }
            }
            case 3 -> { // Vertical avall
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila + x < sopa.length) {
                        sopa[inicialFila + x][inicialColumna] = Character
                                .toUpperCase(sopa[inicialFila + x][inicialColumna]);
                    }
                }
            }
            case 4 -> { // Diagonal de dalt a l'esquerra a baix a la dreta
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila + x < sopa.length && inicialColumna + x < sopa[0].length) {
                        sopa[inicialFila + x][inicialColumna + x] = Character
                                .toUpperCase(sopa[inicialFila + x][inicialColumna + x]);
                    }
                }
            }
            case 5 -> { // Diagonal de dalt a la dreta a baix a l'esquerra
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila + x < sopa.length && inicialColumna - x >= 0) {
                        sopa[inicialFila + x][inicialColumna - x] = Character
                                .toUpperCase(sopa[inicialFila + x][inicialColumna - x]);
                    }
                }
            }
            case 6 -> { // Diagonal de baix a l'esquerra a dalt a la dreta
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila - x >= 0 && inicialColumna + x < sopa[0].length) {
                        sopa[inicialFila - x][inicialColumna + x] = Character
                                .toUpperCase(sopa[inicialFila - x][inicialColumna + x]);
                    }
                }
            }
            case 7 -> { // Diagonal de baix a la dreta a dalt a l'esquerra
                for (int x = 0; x < longitud; x++) {
                    if (inicialFila - x >= 0 && inicialColumna - x >= 0) {
                        sopa[inicialFila - x][inicialColumna - x] = Character
                                .toUpperCase(sopa[inicialFila - x][inicialColumna - x]);
                    }
                }

            }
        }
    }

    public void comprobarParaulesPerFinalitzar() {
        if (paraulaCorrecta[4] != null) {
            paraulesEncertades = true;
            menuPrincipal = 0; // Volver al menú principal
        }
    }

    public boolean sortir() {
        return menuPrincipal == 3;
    }


    
}
