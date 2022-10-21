package com.comtrade.yamb;

import com.comtrade.yamb.implementation.YambMatch;
import com.comtrade.yamb.player.Player;

/**
 * Organizator igre. Objekat koji pokreće "meč" (kolekciju od mnogo partija Yamb-a).<br>
 * Svaka partija, tokom odigravanja, konstantno poziva metodu Igrača koja treba da dostavi instrukcije o načinu na koji Igrač igra partiju.<br>
 * <br>
 * Svaka partija kreira posebnu log-datoteku (ime svake od tih datoteka je {redniBrojPartije}.ymb). <br>
 * Svaka log-datoteteka se sastoji od kolekcije redova, a u svakom od redova su upisane relevantne informacije.<br>
 * Svaki od redova počinje slovom (za kojim sledi dvotačka i jedna praznina) koje definiše prirodu informacije upisane u tekući red.<br>
 * <br>
 * Prefix "T: " označava red u koji su upisane vrednosti kockica sa informacijom da li su te vrednosti fiksirane (znak "." označava da kocka nije fiksirana, a znak "x" da jeste).
 * Primeri : <br>
 * T: 2. 1x 1x 5. 2. 5. govori da su se na kockicama pojavile vrednosti 2, 1, 1, 5, 2, 5 i da je igrač odlučio da fiksira jedinice.<br>
 * T: 2. 1. 1. 5. 2. 5. govori da su se na kockicama pojavile vrednosti 2, 1, 1, 5, 2, 5 i da je igrač odlučio da ponovo baci sve kockice.<br>
 * T: 2x 1x 1x 5x 2x 5x govori da su se na kockicama pojavile vrednosti 2, 1, 1, 5, 2, 5 i da je igrač odlučio da fiksira sve kockice.<br>
 * <br>
 * Prefix "N: " označava red u koji se upisuje informacija o koordinatama polja koje je Igrač najavio za upis.<br>
 * Primeri : <br>
 * N: [6, 10] govori da je igrač najavio upis u polje sa koordinatama (6, 10) (polje KENTA u koloni NAJAVA)<br>
 * N: [1, 0] govori da je igrač najavio upis u polje sa koordinatama (1, 0) (što je sasvim sigurno greška, jer kolona sa indeksom 1 ne iziskuje najavljivanje).<br>
 * <br>
 * Prefix "D: " označava red u koji se upisuje informacija o koordinatama polja u koje će biti upisana vrednost. To je polje koje je igrač najavio nakon prvog bacanja (ako je najavio)
 * ili polje čije je koordinate Igrač prijavio nakon trećeg bacanja kao polje u koje želi da upiše vrednost. Ako Igrač ne dostavi ovu vrednost, Organizator igre će upisati (odgovarajuću)
 * vrednost u prvo slobodno polje u tabeli.<br>
 * Primeri : <br>
 * D: [0, 0] govori da će vrednost biti upisana u polje sa koordinatama (0, 0) (polje FIELD_1 u koloni TOP_DOWN).<br>
 * <br>
 * Prefix "B: " označava red u koji se upisuje informacija o tekućim vrednostima u tabeli partije. Informacije su zapisane kao niz vrednosti po kolonama, međusobno razdvojenih znakom " / ".
 * Svaka kolona je zapisana kao niz vrednosti polja, međusobno razdvojenih jednom prazninom. Svako polje je predstavljeno svojom vrednošću (ako je polje popunjeno) ili tačkom (ako nije odigrano).
 * Primeri : <br>
 * B: . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 . . . . . 0 0 0 / . . . . . . 0 . . 0 66 . . . . 66 66 66<br> 
 * U prethodnom redu je upisana informacija nakon odigranog prvog poteza gde je Igrač u prvom bacanju dobio kentu i rešio da je upiše u kolonu RUČNA.<br>
 * <br>
 * Prefix "S: " označava red u koji je upisana informacija o tekućem rezultatu partije, nakon odigranog poteza.
 * Primeri : <br>
 * S: 66 govori da je u partiju (do tog trenutka) napravljeno ukupno 66 poena.<br>
 * <br>
 * Prefix "E: " označava red u koji je upisana informacija da je Igrač dostavio neispravnu informaciju.
 * Primeri : <br>
 * E: Neophodna je bila informacija o najavi upisa. Polje će biti automatski odabrano.<br>
 * E: Neophodna je bila informacija o upisu u ručnu kolonu. Polje će biti automatski odabrano.<br>
 * E: Upis u polje sa koordinatama [1, 1] se ne najavljuje.<br>
 * E: Upis u polje [6, 9] nije dozvoljen.<br>
 */
public class Main {
   public static void main(String[] args) {
      try {
         YambMatch match = new YambMatch(new Player());
         match.play();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
