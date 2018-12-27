package utilities;

import objects.Partiсipant;

import java.util.List;

public class ChangeParticipant {


    /** Метод добавления карт в hand Participant
     *
     * @param id - participants id
     * @param parList - list of participants
     * @param ii - new card number to add
     */
    public static void addCard (String id, List<Partiсipant> parList, Integer ii){
      for (Partiсipant p: parList){
            if (p.uuid.equals(id)){
                    p.hand.add(ii);
            }
      }
    }

    /** Метод добавления роли для Participant
     *
     * @param id
     * @param parList
     * @param ii
     */
    public static void addRole (String id, List<Partiсipant> parList, Integer ii){
        for (Partiсipant p: parList){
            if (p.uuid.equals(id)){
                p.role = ii;
            }
        }
    }


}
