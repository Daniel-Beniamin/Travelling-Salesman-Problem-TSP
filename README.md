# Travelling-Salesman-Problem-TSP

## Descriere
Acest proiect implementează diversi algoritmi pentru rezolvarea problemei Traveling Salesman Problem (TSP). Algoritmii includ Depth-First Search (DFS), Uniform Cost Search (UCS) și A*.

## Structura proiectului

- **InputData**: Conține clase și metode pentru generarea și citirea grafului de intrare.
- **TSPAlgorithms**: Conține implementările algoritmilor DFS, UCS și A*.
- **TSPMain**: Conține clasa principală `Main` care permite utilizatorului să selecteze și să testeze algoritmii.

## Algoritmi implementați
- **Depth-First Search (DFS)**
- **Uniform Cost Search (UCS)**
- **A\*** (cu o funcție euristică definită de utilizator)

## Specificația datelor de intrare
Fișierul de intrare trebuie să conțină un graf reprezentat printr-o matrice de distanțe între orașe. Fiecare indice al unei linii sau coloane corespunde unui oraș, iar valoarea din celulă reprezintă distanța dintre orașele respective.

## Specificația datelor de ieșire
Algoritmul va returna:
- Drumul cel mai scurt care vizitează fiecare oraș o singură dată și se întoarce la orașul de origine.
- Costul total al acestui drum.

## Instalare
1. Clonează acest repository:
    ```bash
    git clone https://github.com/username/repo-name.git
    ```
2. Navighează în directorul proiectului:
    ```bash
    cd repo-name
    ```
3. Compilează proiectul folosind un IDE Java sau din linia de comandă:
    ```bash
    javac -d bin src/**/*.java
    ```

## Utilizare
1. Rulează clasa principală `Main` din pachetul `TSPMain`:
    ```bash
    java -cp bin TSPMain.Main
    ```
2. Selectează algoritmul dorit și metoda de generare a grafului de intrare.

## Exemplu de utilizare
După rularea programului, utilizatorul va fi rugat să selecteze un algoritm și un mod de generare a grafului. După selectarea opțiunilor, algoritmul va calcula drumul cel mai scurt și va afișa rezultatul.

