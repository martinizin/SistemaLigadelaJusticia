public class NodoHeroes {
    Heroes heroe;
    NodoHeroes izquierda;
    NodoHeroes derecha;

    public NodoHeroes(Heroes heroe, NodoHeroes izquierda, NodoHeroes derecha) {
        this.heroe = heroe;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    public NodoHeroes() {
    }
}
