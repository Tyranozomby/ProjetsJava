package game.engine.items;

import game.engine.fight.attacks.Attack;

import java.util.List;
import java.util.Objects;

public class Weapon extends Item {

    private final List<Attack> attacks;

    public Weapon(String name, String description, int price, List<Attack> attacks) {
        super(name, description, price);
        this.attacks = attacks;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", attacks=" + attacks +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Weapon weapon = (Weapon) obj;
        return Objects.equals(attacks, weapon.attacks);
    }
}
