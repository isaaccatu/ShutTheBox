import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class GestionJugador {

    List<Integer> completo = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    private final List<Integer> eliminados = new ArrayList<>();
    private final List<Integer> toShow = new ArrayList<>();
    private boolean Lose = false;

    //métodos

    public void nuevoJuego() {
        int n;
        List<List<Integer>> result = new ArrayList<>();
        n = Integer.parseInt(JOptionPane.showInputDialog("Primer Lanzamiento: "));

        while (true){
            if(1<n && n<13)
            {
                decompose(n, n, 1, new ArrayList<>(), result); //Obtain all the possible combinations of n
                result = iterator(result); //Correction for decomposing 11 and 12

                // Create a list of lists, where each inner list is a list of integers from 1 to 10
                List<List<Integer>> lists = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    List<Integer> list = new ArrayList<>();
                    for (int j = 1; j <= 10; j++) {
                        list.add(j);
                    }
                    lists.add(list);
                }

                // Iterate over the result
                for (int i = 0; i < result.size(); i++) {
                    List<Integer> combination = result.get(i);
                    List<Integer> list = lists.get(i);

                    // Remove the combination from the corresponding list
                    list.removeAll(combination);
                }

                //The idea of the previous code is to create a list of lists with the combinations of non-repeating numbers
                //that add up to the input value. Then eliminate those combinations from the original complete list, having
                //as a result several lists without those possible combinations.

                //I did it this way to be able to calculate all the possible combinations available with the remaining numbers

                // Print the lists after removal
                for (int i = 0; i < lists.size(); i++) {
                    System.out.println("List " + (i + 1) + " after removal: " + lists.get(i));
                }

                // Analyze which is the most probable outcome
                List<List<Integer>> highestProbList = getHighestProbabilityLists(lists);
                System.out.println("The list with the highest probability is: " + highestProbList);

                //If more than one outcome has the highest probability of success:
                //Select the one with the most amount of numbers going from 1 to 6
                List<List<Integer>> listsWithMostNumbersFromOneToSix = getMostNumbersFromOneToSix(highestProbList);
                System.out.println("The lists with the most numbers from 1 to 6 are: " + listsWithMostNumbersFromOneToSix);
                eliminados.addAll(listsWithMostNumbersFromOneToSix.getFirst());

                //If more than one outcome has the same amount of numbers from 1 to 6:
                //Select the one that has the lowest highest number
                if (listsWithMostNumbersFromOneToSix.size() > 1) {
                    List<Integer> listWithoutCertainNumbers = getListWithoutCertainNumbers(listsWithMostNumbersFromOneToSix);
                    System.out.println("The list without 10 and 9 is: " + listWithoutCertainNumbers);

                    eliminados.clear();
                    eliminados.addAll(listWithoutCertainNumbers);
                }


                completo.removeAll(eliminados);
                toShow.addAll(completo);
                JOptionPane.showMessageDialog(null,"The best option for you to choose is: "+ completo);
                resetCompleto();
                //Repeat the process until winning or losing
                break;
            }

            else {
                JOptionPane.showMessageDialog(null, "Valor incorrecto");
                n = Integer.parseInt(JOptionPane.showInputDialog("Primer Lanzamiento: "));
            }

        }

    }

    public void siguienteTiro() {
        int n;
        List<List<Integer>> result = new ArrayList<>();
        n = Integer.parseInt(JOptionPane.showInputDialog("Siguiente Lanzamiento: "));
        while (true) {
            if (1 < n && n < 13) {
                decompose(n, n, 1, new ArrayList<>(), result); //Obtain all the possible combinations of n
                result = iterator(result); //Correction for decomposing 11 and 12

                removeLists(result, eliminados);

                // Create a list of lists, where each inner list is a list of integers from 1 to 10
                List<List<Integer>> lists = new ArrayList<>();
                completo.removeAll(eliminados);

                for (int i = 0; i < result.size(); i++) {
                    List<Integer> list = new ArrayList<>();
                    for (int j = 1; j <= 10; j++) {
                        if (!completo.contains(j)) {
                            list.add(j);
                        }
                    }
                    lists.add(list);
                }

                resetCompleto();
                // Iterate over the result
                for (int i = 0; i < result.size(); i++) {
                    List<Integer> combination = result.get(i);
                    List<Integer> list = lists.get(i);

                    // Remove the combination from the corresponding list
                    list.removeAll(combination);
                }

                //The idea of the previous code is to create a list of lists with the combinations of non-repeating numbers
                //that add up to the input value. Then eliminate those combinations from the original complete list, having
                //as a result several lists without those possible combinations.

                //I did it this way to be able to calculate all the possible combinations available with the remaining numbers

                if (!lists.isEmpty()) {
                    // Print the lists after removal
                    for (int i = 0; i < lists.size(); i++) {
                        System.out.println("List " + (i + 1) + " after removal: " + lists.get(i));
                    }

                    // Analyze which is the most probable outcome
                    List<List<Integer>> highestProbList = getHighestProbabilityLists(lists);
                    System.out.println("The list with the highest probability is: " + highestProbList);

                    //If more than one outcome has the highest probability of success:
                    //Select the one with the most amount of numbers going from 1 to 6
                    List<List<Integer>> listsWithMostNumbersFromOneToSix = getMostNumbersFromOneToSix(highestProbList);
                    System.out.println("The lists with the most numbers from 1 to 6 are: " + listsWithMostNumbersFromOneToSix);
                    eliminados.clear();
                    eliminados.addAll(listsWithMostNumbersFromOneToSix.getFirst());

                    //If more than one outcome has the same amount of numbers from 1 to 6:
                    //Select the one that has the lowest highest number
                    if (listsWithMostNumbersFromOneToSix.size() > 1) {
                        List<Integer> listWithoutCertainNumbers = getListWithoutCertainNumbers(listsWithMostNumbersFromOneToSix);
                        System.out.println("The list without 10 and 9 is: " + listWithoutCertainNumbers);

                        eliminados.clear();
                        eliminados.addAll(listWithoutCertainNumbers);
                    }

                    completo.removeAll(eliminados);
                    completo.removeAll(toShow);
                    toShow.addAll(completo);
                    JOptionPane.showMessageDialog(null, "The best option for you to choose is: " + completo);
                    resetCompleto();
                    //Repeat the process until winning or losing
                } else {
                    JOptionPane.showMessageDialog(null, "Perdiste");
                    Lose = true;
                }

                break;
            } else {
                JOptionPane.showMessageDialog(null, "Valor incorrecto");
                n = Integer.parseInt(JOptionPane.showInputDialog("Siguiente Lanzamiento: "));
            }
        }
    }

    public boolean Lost()
    {
        return this.Lose;
    }

    private static void decompose(int n, int max, int start, List<Integer> current, List<List<Integer>> result) {
        if (n == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i <= max; i++) {
            if (n - i >= 0) {
                current.add(i);
                decompose(n - i, i - 1, start, current, result);
                current.removeLast();
            }
        }
    }

    private static List<List<Integer>> iterator(List<List<Integer>> lists){
        Iterator<List<Integer>> iterator = lists.iterator();
        while (iterator.hasNext()) {
            List<Integer> list = iterator.next();
            for (int num : list) {
                if (num > 10) {
                    iterator.remove();
                    break;
                }
            }
        }
        return lists;
    }

    private static List<List<Integer>> getHighestProbabilityLists(List<List<Integer>> lists) {
        double highestProbability = 0;
        List<List<Integer>> highestProbLists = new ArrayList<>();

        for (List<Integer> list : lists) {
            double probability = calculateProbability(list);
            System.out.println(probability);
            if (probability > highestProbability) {
                highestProbability = probability;
                highestProbLists.clear();
                highestProbLists.add(list);
            } else if (probability == highestProbability) {
                highestProbLists.add(list);
            }
        }

        return highestProbLists;
    }

    private static double calculateProbability(List<Integer> list) {
        int favorableOutcomes = 0;
        int totalOutcomes = 36; // As there are a total of 36 outcomes when two dice are thrown

        for (int i = 2; i <= 12; i++) {
            if (canSumTo(list, i)) {
                favorableOutcomes += getDiceOutcomes(i);
            }
        }

        return (double) favorableOutcomes / totalOutcomes;
    }

    private static boolean canSumTo(List<Integer> list, int target) {
        return canSumTo(list, target, 0, new boolean[list.size()]);
    }

    private static boolean canSumTo(List<Integer> list, int target, int start, boolean[] used) {
        if (target == 0) {
            return true;
        }
        if (start == list.size()) {
            return false;
        }
        if (!used[start] && list.get(start) <= target) {
            used[start] = true;
            if (canSumTo(list, target - list.get(start), start + 1, used)) {
                return true;
            }
            used[start] = false;
        }
        return canSumTo(list, target, start + 1, used);
    }

    private static int getDiceOutcomes(int sum) {
        int outcomes = 0;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i + j == sum) {
                    outcomes++;
                }
            }
        }
        return outcomes;
    }

    private static List<List<Integer>> getMostNumbersFromOneToSix(List<List<Integer>> lists) {
        int maxCount = 0;
        List<List<Integer>> listsWithMostNumbersFromOneToSix = new ArrayList<>();

        for (List<Integer> list : lists) {
            int count = 0;
            for (Integer number : list) {
                if (number >= 1 && number <= 6) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                listsWithMostNumbersFromOneToSix.clear();
                listsWithMostNumbersFromOneToSix.add(list);
            } else if (count == maxCount) {
                listsWithMostNumbersFromOneToSix.add(list);
            }
        }

        return listsWithMostNumbersFromOneToSix;
    }

    private static List<Integer> getListWithoutCertainNumbers(List<List<Integer>> lists) {
        List<List<Integer>> listsWithoutTen = new ArrayList<>();
        List<List<Integer>> listsWithoutNine = new ArrayList<>();

        for (List<Integer> list : lists) {
            if (!list.contains(10)) {
                listsWithoutTen.add(list);
            }
        }
        if (listsWithoutTen.size() == 1) {
            return listsWithoutTen.getFirst();

        } else {
            for (List<Integer> list : lists) {
                if (!list.contains(9)) {
                    listsWithoutNine.add(list);
                }
            }
        }

        return listsWithoutNine.getFirst();
    }

    private void resetCompleto(){
        this.completo.clear();
        this.completo.add(1);
        this.completo.add(2);
        this.completo.add(3);
        this.completo.add(4);
        this.completo.add(5);
        this.completo.add(6);
        this.completo.add(7);
        this.completo.add(8);
        this.completo.add(9);
        this.completo.add(10);
    }

    private static void removeLists(List<List<Integer>> listOfLists, List<Integer> anotherList) {
        Iterator<List<Integer>> iterator = listOfLists.iterator();

        while (iterator.hasNext()) {
            List<Integer> list = iterator.next();
            for (Integer number : list) {
                if (!anotherList.contains(number)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }


}
