import java.util.*;

public class GestionHeroes {
    private NodoHeroes raiz; // Nodo raíz del árbol binario de búsqueda

    public GestionHeroes() {
        this.raiz = null;
    }


    public void registrarHeroe(Heroes heroe) throws Exception {
        raiz = insertar(raiz, heroe);
    }


    private NodoHeroes insertar(NodoHeroes nodo, Heroes heroe) throws Exception {
        if (nodo == null) {
            return new NodoHeroes(heroe, null, null);
        }

        if (heroe.getId().compareTo(nodo.heroe.getId()) < 0) {
            nodo.izquierda = insertar(nodo.izquierda, heroe);
        } else if (heroe.getId().compareTo(nodo.heroe.getId()) > 0) {
            nodo.derecha = insertar(nodo.derecha, heroe);
        } else {
            throw new Exception("El héroe con este ID ya está registrado.");
        }
        return nodo;
    }


    public Heroes buscarHeroe(String id) {
        return buscar(raiz, id);
    }

    private Heroes buscar(NodoHeroes nodo, String id) {
        if (nodo == null) return null;

        if (id.equals(nodo.heroe.getId())) {
            return nodo.heroe;
        } else if (id.compareTo(nodo.heroe.getId()) < 0) {
            return buscar(nodo.izquierda, id);
        } else {
            return buscar(nodo.derecha, id);
        }
    }

    public String calcularAporteImpuestos(String id) {
        Heroes heroe = buscarHeroe(id);
        if (heroe == null) {
            return "Héroe no encontrado con ID: " + id;
        }

        double impuesto = calcularImpuesto(heroe.getNivelDificultad(), heroe.getPagoMensual());
        double pagoTotal = heroe.getPagoMensual() - impuesto;

        return String.format("Héroe: %s\nID: %s\nNivel de Dificultad: %d\nPago Mensual: %.2f\nImpuesto: %.2f\nPago Neto: %.2f",
                heroe.getNombre(), heroe.getId(), heroe.getNivelDificultad(), heroe.getPagoMensual(), impuesto, pagoTotal);
    }

    private double calcularImpuesto(int nivelDificultad, double pagoMensual) {
        double porcentaje = 0;

        switch (nivelDificultad) {
            case 1:
                porcentaje = 0.05; // 5%
                break;
            case 2:
                porcentaje = 0.10; // 10%
                break;
            case 3:
                porcentaje = 0.15; // 15%
                break;
            case 4:
                porcentaje = 0.20; // 20%
                break;
            case 5:
                porcentaje = 0.25; // 25%
                break;
            default:
                porcentaje = 0;
                break;
        }

        return pagoMensual * porcentaje;
    }


    public void modificarHeroe(String id, double nuevoPago) {
        Heroes heroe = buscarHeroe(id);
        if (heroe != null) {
            heroe.setPagoMensual(nuevoPago);
        }
    }


    public void listarHeroes(StringBuilder sb) {
        listarHeroesRec(raiz, sb);
    }

    private void listarHeroesRec(NodoHeroes nodo, StringBuilder sb) {
        if (nodo != null) {
            listarHeroesRec(nodo.izquierda, sb);
            sb.append(String.format("ID: %s, Nombre: %s, Superpoder: %s, Misión: %s, Nivel: %d, Pago: %.2f\n",
                    nodo.heroe.getId(), nodo.heroe.getNombre(), nodo.heroe.getSuperpoder(),
                    nodo.heroe.getMision(), nodo.heroe.getNivelDificultad(), nodo.heroe.getPagoMensual()));
            listarHeroesRec(nodo.derecha, sb);
        }
    }
}
