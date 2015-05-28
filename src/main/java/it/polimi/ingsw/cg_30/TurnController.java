package it.polimi.ingsw.cg_30;

public class TurnController {

    private Turn turn;

    public TurnController() {
        // TO DO
        // this.turn = new Turn();
    }

    public Player getCurrentPlayer() {
        return turn.getCurrentPlayer();
    }

    public Turn getTurn() {
        return turn;
    }

    public void playRuleModifier(ItemCard item) {
        throw new UnsupportedOperationException();
    }

}
