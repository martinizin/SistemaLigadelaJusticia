import java.util.*;

public class GestionHeroes {
    private NodoHeroes raiz; // Nodo raíz del árbol binario de búsqueda

    public GestionHeroes() {
        this.raiz = null;
    }


    public void registrarHeroe(Heroes heroe) throws Exception {
        if (heroe.getId() == null || heroe.getId().isEmpty()) {
            throw new Exception("El ID del héroe no puede estar vacío.");
        }
        if (heroe.getNombre() == null || heroe.getNombre().isEmpty()) {
            throw new Exception("El nombre del héroe no puede estar vacío.");
        }
        if (heroe.getSuperpoder() == null || heroe.getSuperpoder().isEmpty()) {
            throw new Exception("El superpoder del héroe no puede estar vacío.");
        }
        if (heroe.getMision() == null || heroe.getMision().isEmpty()) {
            throw new Exception("La misión del héroe no puede estar vacía.");
        }
        if (heroe.getPagoMensual() <= 0) {
            throw new Exception("El pago mensual debe ser mayor que cero.");
        }

        raiz = insertar(raiz, heroe);
    }

    private NodoHeroes insertar(NodoHeroes nodo, Heroes heroe) throws Exception {
        if (nodo == null) {
            return new NodoHeroes(heroe, null, null);
        }

        // Validación de duplicados
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
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del héroe no puede estar vacío.");
        }
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
        if (id == null || id.isEmpty()) {
            return "El ID del héroe no puede estar vacío.";
        }

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
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del héroe no puede estar vacío.");
        }

        if (nuevoPago <= 0) {
            throw new IllegalArgumentException("El nuevo pago mensual debe ser mayor que cero.");
        }

        Heroes heroe = buscarHeroe(id);
        if (heroe == null) {
            throw new IllegalArgumentException("Héroe no encontrado con ID: " + id);
        }

        heroe.setPagoMensual(nuevoPago);
    }


    public void listarHeroes(StringBuilder sb) {
        if (sb == null) {
            throw new IllegalArgumentException("El StringBuilder no puede ser nulo.");
        }

        if (raiz == null) {
            sb.append("No hay héroes registrados.");
            return;
        }

        listarHeroesRec(raiz, sb);
    }

    private void listarHeroesRec(NodoHeroes nodo, StringBuilder sb) {
        if (nodo != null) {
            listarHeroesRec(nodo.izquierda, sb);
            sb.append(String.format("ID: %s\nNombre: %s\nSuperpoder: %s\nMisión: %s\nNivel: %d\nPago: %.2f\n",
                    nodo.heroe.getId(), nodo.heroe.getNombre(), nodo.heroe.getSuperpoder(),
                    nodo.heroe.getMision(), nodo.heroe.getNivelDificultad(), nodo.heroe.getPagoMensual()));
            listarHeroesRec(nodo.derecha, sb);
        }
    }
}
