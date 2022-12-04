package ui.swing.tree.nodes;

import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.Consumable;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class ShopListNode implements TreeNode {

    private final ShopWeaponListNode weaponListNode;

    private final ShopConsumableListNode consumableListNode;

    public ShopListNode(ArrayList<Item> items) {
        weaponListNode = new ShopWeaponListNode(items.stream().filter(item -> item instanceof Weapon).map(item -> (Weapon) item).toList(), this);
        consumableListNode = new ShopConsumableListNode(items.stream().filter(item -> (item instanceof Consumable)).map(item -> (Consumable) item).toList(), this);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (childIndex == 0) {
            return weaponListNode;
        } else if (childIndex == 1) {
            return consumableListNode;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        if (node == weaponListNode) {
            return 0;
        } else if (node == consumableListNode) {
            return 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return null;
    }
}
