package DropParty;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.util.node.Deque;
import org.powerbot.game.api.util.node.Queue;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

@Manifest(authors = {"Cup"}, name = "c[DropParty]", description = "Drop Party Tool")
public class DropParty extends ScriptWrapper implements PaintListener {
    private ArrayList<Tile> locations = new ArrayList<Tile>();
    private ArrayList<Long> times = new ArrayList<Long>();
    private Timer timer = new Timer(0);
    private Player player;

    @Override
    public void onStart() {
        provide(new VexillumCloser());
        provide(new StatueCloser());
        provide(new Walk());
        provide(new Pickup());
        provide(new Tracking());
    }

    private class VexillumCloser extends Node {
        @Override
        public boolean activate() {
            return Widgets.get(1107, 156).validate();
        }

        @Override
        public void execute() {
            Mouse.hop((int) Widgets.get(1107, 156).getBoundingRectangle().getCenterX(), (int) Widgets.get(1107, 156).getBoundingRectangle().getCenterY());
            Mouse.click(true);
        }
    }

    private class StatueCloser extends Node {
        @Override
        public boolean activate() {
            return Widgets.get(21, 42).validate();
        }

        @Override
        public void execute() {
            Mouse.hop((int) Widgets.get(21, 42).getBoundingRectangle().getCenterX(), (int) Widgets.get(21, 42).getBoundingRectangle().getCenterY());
            Mouse.click(true);
        }
    }

    private class Walk extends Node {
        @Override
        public boolean activate() {
            return Menu.isOpen() && Menu.contains("Walk");
        }

        @Override
        public void execute() {
            if (Menu.isOpen()) {
                select(Menu.contains("Take") ? "Take" : "Walk");
            }
        }
    }

    private class Pickup extends Node {
        @Override
        public boolean activate() {
            return GroundItems.getNearest(1, new Filter<GroundItem>() {
                @Override
                public boolean accept(GroundItem p) {
                    return p.getGroundItem().getName() != null && !p.getGroundItem().getName().toLowerCase().contains("flower");
                }
            }) != null;
        }

        @Override
        public void execute() {
            if (Players.getLocal().getLocation().contains(Mouse.getLocation())) {
                String s = Menu.getActions()[0];
                if (s.contains("Take")) {
                    Mouse.click(true);
                    return;
                }
                if (s.contains("Behold") || s.contains("Fire")
                        || s.contains("Interact") || s.contains("Read")
                        || s.contains("Pick-up") || s.contains("Dismantle")
                        || s.contains("Shoo") || s.contains("Pull")) {
                    Mouse.click(false);
                    select("Take");
                }
                sleep(20);
            }
        }
    }

    private class Tracking extends Node {
        @Override
        public boolean activate() {
            return true;
        }

        @Override
        public void execute() {
            if (player == null) {
                player = (Player) Players.getLocal().getInteracting();
                return;
            }
            if (!player.isMoving()) {
                if (locations.size() == 0) {
                    locations.add(player.getLocation());
                    times.add(timer.getElapsed());
                }
                if (!tilesEqual(player.getLocation(),
                        locations.get(locations.size() - 1))) {
                    locations.add(player.getLocation());
                    times.add(timer.getElapsed());
                }
            }
        }
    }

    private boolean tilesEqual(Tile a, Tile b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    @Override
    public void onRepaint(Graphics g) {
        for (int i = 0; i < locations.size(); i++) {
            if ((times.get(i) + 61000) - timer.getElapsed() < 10000) {
                if ((times.get(i) + 64000) - timer.getElapsed() < 0) {
                    locations.remove(i);
                    times.remove(i);
                    return;
                }
                g.setColor(Color.green);
                Tile t = locations.get(i);
                t.draw(g);
                Point p = t.getCentralPoint();
                long time = ((times.get(i) + 61000) - timer.getElapsed()) / 1000;
                if (time < 0) time = 0;
                g.drawString("" + time, p.x, p.y);
                if (i + 1 < locations.size()) {
                    if (t.isOnScreen() && locations.get(i + 1).isOnScreen()) {
                        g.drawLine(p.x, p.y, locations.get(i + 1).getCentralPoint().x, locations.get(i + 1).getCentralPoint().y);
                    }
                }
                int alpha = (int) (50 - 5 * ((times.get(i) + 61000) - timer.getElapsed()) / 1000);
                g.setColor(new Color(0, 255, 0, alpha));
                if (t.getBounds().length > 0) g.fillPolygon(t.getBounds()[0]);
                g.setColor(Color.green);
                Point mp = t.getMapPoint();
                g.drawRect(mp.x - 2, mp.y - 2, 4, 4);
            }
        }
    }

    public static boolean select(final String action) {
        return select(action, null);
    }

    public static boolean select(final String action, final String option) {
        int idx = getIndex(action, option);
        if (!Menu.isOpen()) {
            if (idx == -1) {
                return false;
            }
            if (idx == 0) {
                Mouse.click(true);
                return true;
            }
            Mouse.click(false);
            final Timer t = new Timer(100);
            while (t.isRunning() && !Menu.isOpen()) {
                sleep(5);
            }
            idx = getIndex(action, option);

            return idx != -1 && clickIndex(idx);
        } else if (idx == -1) {
            while (Menu.isOpen()) {
                Mouse.move(0, 0);
                sleep(Random.nextInt(100, 500));
            }
            return false;
        }
        return clickIndex(idx);
    }

    public static Point getLocation() {
        final Client client = Context.client();
        return new Point(client.getMenuX(), client.getMenuY());
    }

    public static Point getSubLocation() {
        final Client client = Context.client();
        return new Point(client.getSubMenuX(), client.getSubMenuY());
    }

    private static boolean clickIndex(final int i) {
        if (!Menu.isOpen()) {
            return false;
        }
        final String[] items = Menu.getItems();
        if (items.length <= i) {
            return false;
        }
        if (Menu.isCollapsed()) {
            final Queue<MenuGroupNode> groups = new Queue<MenuGroupNode>((NodeSubQueue) Context.client().getCollapsedMenuItems());
            int idx = 0, mainIdx = 0;
            for (MenuGroupNode g = groups.getHead(); g != null; g = groups.getNext(), ++mainIdx) {
                final Queue<MenuItemNode> subItems = new Queue<MenuItemNode>((NodeSubQueue) g.getItems());
                int subIdx = 0;
                for (MenuItemNode item = subItems.getHead(); item != null; item = subItems.getNext(), ++subIdx) {
                    if (idx++ == i) {
                        return subIdx == 0 ? clickMain(items, mainIdx) : clickSub(items, mainIdx, subIdx);
                    }
                }
            }
            return false;
        } else {
            return clickMain(items, i);
        }
    }

    private static boolean clickMain(final String[] items, final int i) {
        final Point menu = getLocation();
        final int xOff = Random.nextInt(4, items[i].length() * 4);
        final int yOff = 21 + 16 * i + Random.nextInt(3, 12);
        Mouse.hop(menu.x + xOff, menu.y + yOff, 2, 2);
        if (Menu.isOpen()) {
            Mouse.click(true);
            return true;
        }
        return false;
    }

    private static boolean clickSub(final String[] items, final int mIdx, final int sIdx) {
        final Point menuLoc = getLocation();
        int x = Random.nextInt(4, items[mIdx].length() * 4);
        int y = 21 + 16 * mIdx + Random.nextInt(3, 12);
        Mouse.hop(menuLoc.x + x, menuLoc.y + y, 2, 2);
        sleep(Random.nextInt(125, 150));
        if (Menu.isOpen()) {
            final Point subLoc = getSubLocation();
            final Point start = Mouse.getLocation();
            final int subOff = subLoc.x - start.x;
            x = Random.nextInt(4, items[sIdx].length() * 4);
            if (subOff > 0) {
                Mouse.hop(Random.nextInt(subOff + 4, subOff + Random.nextInt(4, items[sIdx].length() * 2)), start.y);
            } else {
                Mouse.hop(subLoc.x + x, Mouse.getLocation().y, 2, 0);
            }
            sleep(Random.nextInt(50, 100));
            if (Menu.isOpen()) {
                y = 16 * sIdx + Random.nextInt(3, 12) + 21;
                Mouse.hop(subLoc.x + x, subLoc.y + y, 0, 2);
                sleep(Random.nextInt(50, 100));
                if (Menu.isOpen()) {
                    Mouse.click(true);
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] getActions() {
        return getMenuItemPart(true);
    }

    public static String[] getOptions() {
        return getMenuItemPart(false);
    }

    private static int getIndex(String action) {
        action = action.toLowerCase();
        final String[] items = getActions();
        for (int i = 0; i < items.length; i++) {
            if (items[i].toLowerCase().contains(action)) {
                return i;
            }
        }
        return -1;
    }

    private static int getIndex(String action, String option) {
        if (option == null) {
            return getIndex(action);
        }
        action = action.toLowerCase();
        option = option.toLowerCase();
        final String[] actions = getActions();
        final String[] options = getOptions();
        for (int i = 0; i < Math.min(actions.length, options.length); i++) {
            if (actions[i].toLowerCase().contains(action) && options[i].toLowerCase().contains(option)) {
                return i;
            }
        }
        return -1;
    }

    private static String[] getMenuItemPart(final boolean firstPart) {
        final LinkedList<String> itemsList = new LinkedList<String>();
        final Client client = Context.client();
        String firstAction = "";
        if (Menu.isCollapsed()) {
            final Queue<MenuGroupNode> menu = new Queue<MenuGroupNode>((NodeSubQueue) client.getCollapsedMenuItems());
            try {
                for (MenuGroupNode mgn = menu.getHead(); mgn != null; mgn = menu.getNext()) {
                    final Queue<MenuItemNode> submenu = new Queue<MenuItemNode>((NodeSubQueue) mgn.getItems());
                    for (MenuItemNode min = submenu.getHead(); min != null; min = submenu.getNext()) {
                        if (firstAction != null) {
                            firstAction = (String) min.getAction();
                        }
                        itemsList.addLast(firstPart ?
                                (String) min.getAction() :
                                (String) min.getOption());
                    }
                }
            } catch (final NullPointerException ignored) {
            }
        } else {
            try {
                final Deque<MenuItemNode> menu = new Deque<MenuItemNode>((NodeDeque) client.getMenuItems());
                for (MenuItemNode min = menu.getHead(); min != null; min = menu.getNext()) {
                    if (firstAction != null) {
                        firstAction = (String) min.getAction();
                    }
                    itemsList.addLast(firstPart ?
                            (String) min.getAction() :
                            (String) min.getOption());
                }
            } catch (final Throwable ignored) {
            }
        }
        final String[] items = itemsList.toArray(new String[itemsList.size()]);
        final LinkedList<String> output = new LinkedList<String>();
        for (int i = items.length - 1; i >= 0; i--) {
            final String item = items[i];
            output.add(item == null ? "" : stripFormatting(item));
        }
        if (output.size() > 1 && firstAction.equals(Menu.isCollapsed() ? "Walk here" : "Cancel")) {
            Collections.reverse(output);
        }
        return output.toArray(new String[output.size()]);
    }

    private static String stripFormatting(final String input) {
        final Pattern HTML_TAG = Pattern.compile("(^[^<]+>|<[^>]+>|<[^>]+$)");
        return HTML_TAG.matcher(input).replaceAll("");
    }

    public static abstract interface WaitCondition {
        public boolean isValid();
    }

    public static boolean waitFor(final WaitCondition condition, final long timeOut) {
        Timer timer = new Timer(timeOut);
        while (timer.isRunning()) {
            if (condition.isValid()) return true;
        }
        return false;
    }
}