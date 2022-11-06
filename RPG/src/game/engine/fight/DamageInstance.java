package game.engine.fight;

import game.engine.entities.Entity;

public class DamageInstance {

    private final Entity source;

    private int physicalDamage;

    private int magicalDamage;

    public DamageInstance(int physicalDamage, int magicalDamage, Entity source) {
        this.physicalDamage = physicalDamage;
        this.magicalDamage = magicalDamage;
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }

    public int getPhysicalDamage(int physicalDefense) {
        return Math.max(0, physicalDamage - physicalDefense);
    }

    public int getMagicalDamage(int magicalDefense) {
        return Math.max(0, magicalDamage - magicalDefense);
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public void setMagicalDamage(int magicalDamage) {
        this.magicalDamage = magicalDamage;
    }

    @Override
    public String toString() {
        return "DamageInstance{" +
                "physicalDamage=" + physicalDamage +
                ", magicalDamage=" + magicalDamage +
                '}';
    }
}
