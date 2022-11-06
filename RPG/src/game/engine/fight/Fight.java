package game.engine.fight;

import game.Stats;
import game.engine.Game;
import game.engine.effects.interfaces.BeforeActionEffect;
import game.engine.effects.interfaces.BeforePlayEffect;
import game.engine.effects.interfaces.EndTurnEffect;
import game.engine.effects.interfaces.StartTurnEffect;
import game.engine.entities.Entity;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.actions.FightAction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Fight {

    private final Logger LOGGER = Logger.getLogger(Fight.class.getName());

    private final ArrayList<Entity> playOrder;

    private final Player player;

    private final List<Monster> monsters;

    private int turns = 1;

    private FightWinner winner = FightWinner.NONE;

    private int currentEntityTurnIndex = 0;

    public Fight(Player player, Monster monster) {
        this.player = player;
        this.monsters = List.of(monster);

        playOrder = new ArrayList<>();
        playOrder.add(player);
        playOrder.add(monster);
        playOrder.sort((e1, e2) -> e2.getStat(Stats.SPEED) - e1.getStat(Stats.SPEED));
    }

    public Fight(Player player, Monster monster1, Monster monster2) {
        this.player = player;
        this.monsters = List.of(monster1, monster2);

        playOrder = new ArrayList<>();
        playOrder.add(player);
        playOrder.add(monster1);
        playOrder.add(monster2);
        playOrder.sort((e1, e2) -> e2.getStat(Stats.SPEED) - e1.getStat(Stats.SPEED));
    }

    public void doFight() {
        LOGGER.info("Fight between " + player + " and " + monsters + ".");
        LOGGER.info("\033[4mPlayer Order :");
        for (Entity entity : playOrder) {
            LOGGER.info(entity + " (" + entity.getHealth() + "hp, " + entity.getStat(Stats.SPEED) + "speed)");
        }
        LOGGER.info("--- STARTING FIGHT ---");
        Game.UI_CALLBACKS.onFightStart(this);

        while (winner == FightWinner.NONE) {
            Entity currentEntity = playOrder.get(currentEntityTurnIndex);

            LOGGER.info("Turn " + turns);
            Game.UI_CALLBACKS.onTurnStart(currentEntity, turns);

            doTurn(currentEntity);

            LOGGER.info("Turn " + turns + " ended.");
            Game.UI_CALLBACKS.onTurnEnd();

            List<Monster> deadMonsters = monsters.stream().filter(m -> !m.isAlive()).toList();
            playOrder.removeAll(deadMonsters);

            if (!player.isAlive()) {
                winner = FightWinner.MONSTERS;
            } else if (monsters.stream().noneMatch(Monster::isAlive)) {
                winner = FightWinner.PLAYER;
            }

            turns++;
            currentEntityTurnIndex = ++currentEntityTurnIndex % playOrder.size();
        }

        LOGGER.info("--- FIGHT ENDED ---");
        LOGGER.info("Winner : " + winner);
        Game.UI_CALLBACKS.onFightEnd();
    }

    private void doTurn(Entity currentEntity) {
        currentEntity.triggerEffect(StartTurnEffect.class, StartTurnEffect::startTurnApply);

        LOGGER.info(String.format("%s's turn", currentEntity));

        FightAction forcedAction = currentEntity.triggerEffectWithReturn(BeforePlayEffect.class, BeforePlayEffect::beforePlayAction);

        FightAction originalAction = forcedAction != null ? forcedAction : currentEntity.getFightAction(this);

        if (originalAction == null) {
            LOGGER.severe("No action");
            throw new IllegalStateException("No action");
        }

        FightAction action = currentEntity.triggerEffectWithReturn(BeforeActionEffect.class, e -> e.beforeActionAction(originalAction));

        if (action == null)
            action = originalAction;

        action.doAction(this, currentEntity);

        currentEntity.triggerEffect(EndTurnEffect.class, EndTurnEffect::endTurnApply);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEnemies(Entity from) {
        if (from == player) {
            return monsters.stream().filter(Entity::isAlive).map(Entity.class::cast).toList();
        } else {
            return List.of(player);
        }
    }

    public List<Entity> getMyself(Entity from) {
        return List.of(from);
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    enum FightWinner {
        PLAYER, MONSTERS, NONE
    }
}