package fr.spectr2155e.housepurchase.systems.crochetage;

import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;

public class Crochetage {
    public static HashMap<Player, Crochetage> crochetageHashMap = new HashMap<>();

    private int idHouse;

    private List<Integer> buttonsLevel;

    private List<Boolean> buttonsState;

    private List<Integer> buttonsToReach;

    private int secondsTimer;

    private int chancePourcentageOtherSwitch;

    private int chancePourcentageCallPolice;

    public Crochetage(int idHouse, List<Integer> buttonsLevel, List<Boolean> buttonsState, List<Integer> buttonsToReach, int secondsTimer, int chancePourcentageOtherSwitch, int chancePourcentageCallPolice) {
        this.idHouse = idHouse;
        this.buttonsLevel = buttonsLevel;
        this.buttonsState = buttonsState;
        this.buttonsToReach = buttonsToReach;
        this.secondsTimer = secondsTimer;
        this.chancePourcentageOtherSwitch = chancePourcentageOtherSwitch;
        this.chancePourcentageCallPolice = chancePourcentageCallPolice;
    }

    public int getIdHouse() {
        return this.idHouse;
    }

    public List<Integer> getButtonsLevel() {
        return this.buttonsLevel;
    }

    public List<Boolean> getButtonsState() {
        return this.buttonsState;
    }

    public List<Integer> getButtonsToReach() {
        return this.buttonsToReach;
    }

    public int getSecondsTimer() {
        return this.secondsTimer;
    }

    public int getChancePourcentageOtherSwitch() {
        return this.chancePourcentageOtherSwitch;
    }

    public int getChancePourcentageCallPolice() {
        return this.chancePourcentageCallPolice;
    }

    public void setIdHouse(int idHouse) {
        this.idHouse = idHouse;
    }

    public void setButtonsLevel(List<Integer> buttonsLevel) {
        this.buttonsLevel = buttonsLevel;
    }

    public void setButtonsState(List<Boolean> buttonsState) {
        this.buttonsState = buttonsState;
    }

    public void setButtonsToReach(List<Integer> buttonsToReach) {
        this.buttonsToReach = buttonsToReach;
    }

    public void setSecondsTimer(int secondsTimer) {
        this.secondsTimer = secondsTimer;
    }

    public void setChancePourcentageOtherSwitch(int chancePourcentageOtherSwitch) {
        this.chancePourcentageOtherSwitch = chancePourcentageOtherSwitch;
    }

    public void setChancePourcentageCallPolice(int chancePourcentageCallPolice) {
        this.chancePourcentageCallPolice = chancePourcentageCallPolice;
    }
}

