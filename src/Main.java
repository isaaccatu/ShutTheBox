import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private final GestionJugador gestor;

    public static void main(String[] args)
    {
        new Main();
    }

    public Main(){
        this.gestor =new GestionJugador();
        this.menuPrincipal();
        System.out.println("Juego Finalizado");
    }

    private void menuPrincipal(){
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                    ==Opciones==\s
                    1. Nuevo Juego
                    0. SALIR
                    """));

            switch (opcion) {

                case 1:
                    this.gestor.nuevoJuego();
                    this.menuSecundario();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "OPCIÓN NO VALIDA!!...");
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Hasta la próxima...");
                    break;
            }
        }
        while (opcion != 0);
    }

    private void menuSecundario()
    {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                    ==Opciones==\s
                    1. Siguiente Tiro
                    0. SALIR
                    """));

            switch (opcion) {

                case 1:
                    if (!this.gestor.Lost())
                    {
                        this.gestor.siguienteTiro();
                        break;
                    }
                    else {
                        this.menuPrincipal();
                        break;
                    }

                default:
                    JOptionPane.showMessageDialog(null, "OPCIÓN NO VALIDA!!...");
                    break;

                case 0:
                    break;

            }
        }
        while (opcion != 0);
    }

}