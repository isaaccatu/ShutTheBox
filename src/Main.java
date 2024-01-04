import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private final Manager manager;

    public static void main(String[] args)
    {
        new Main();
    }

    public Main(){
        this.manager =new Manager();
        this.mainMenu();
        System.out.println("Game Finished");
    }

    private void mainMenu(){
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                    ==Options==\s
                    1. New Game
                    0. EXIT
                    """));

            switch (opcion) {

                case 1:
                    this.manager.newGame();
                    this.secondaryMenu();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "INVALID OPTION!!...");
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "See you next time...");
                    break;
            }
        }
        while (opcion != 0);
    }

    private void secondaryMenu()
    {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                    ==Options==\s
                    1. Next Shot
                    0. EXIT
                    """));

            switch (opcion) {

                case 1:
                    if (!this.manager.Lost())
                    {
                        this.manager.nextShot();
                        break;
                    }
                    else {
                        this.mainMenu();
                        break;
                    }

                default:
                    JOptionPane.showMessageDialog(null, "INVALID OPTION!!...");
                    break;

                case 0:
                    break;

            }
        }
        while (opcion != 0);
    }

}