package it.polimi.ingsw.cg_30.gamemanager.controller;

public enum ActionType {
    MOVE(Move.class), ATTACK(Attack.class), TURN_OVER(TurnOver.class), USE_ITEM(
            UseCard.class), NOISE_ANY(NoiseAny.class), DISCARD_CARD(
            DiscardCard.class), DRAW_CARD(DrawCard.class);

    private Class controllerClass;

    private ActionType(Class c) {
        this.controllerClass = c;
    }

    public ActionController getController() throws InstantiationException,
            IllegalAccessException {
        return (ActionController) this.controllerClass.newInstance();
    }
}
