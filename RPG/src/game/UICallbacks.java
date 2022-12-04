package game;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.entities.Entity;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.fight.attacks.Attack;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.world.Map;
import game.world.tile.Tile;

public interface UICallbacks {

    // GAME
    void onGameStart(Map map);

    void onGameEnd(boolean victory);

    void onEndTileReached();

    void onNextLevel(int level);

    // FIGHT
    void onFightStart(Fight fight);

    void onFightEnd();

    void onTurnStart(Entity entity, int turn);

    void onTurnEnd(Fight fight);

    void onPlayerDeath(Player player);

    void onMonsterDeath(Monster monster);

    void onFightVictory();

    void onFightDefeat();

    void onStatUpgrade(Stats stat, int amount);

    void onAttack(Entity attacker, Entity receiver, Attack damage);

    void onDamage(Entity receiver, int physicalDamage, int magicalDamage);

    void onHeal(Entity receiver, int heal);

    // EFFECT
    void onEffectApplied(Entity receiver, Effect effect);

    void onEffectFailedApply(Entity receiver, Effect effect);

    void onEffectActivation(Effect effect);

    void onEffectEnd(Entity entity, Effect effect);

    // ITEM
    void onItemFound(Item item);

    void onGoldFound(int amount);

    void onConsumableUse(Item item);

    // INVENTORY

    void onWeaponEquipped(Weapon weapon);

    void onWeaponUnequipped(Weapon weapon);

    // SHOP

    void onItemBuy(Item item);

    void onItemBuyFail(Item item);

    FightAction getFightAction(Player player, Fight fight);

    Action getAction(Game game);

    void onInvalidMove();

    void onMove(Map map, Tile tile);
}
