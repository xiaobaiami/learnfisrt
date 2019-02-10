package algorithm.other;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class 大楼轮廓线 {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 6, 4},
                {2, 4, 3},
                {5, 8, 5},
                {7, 10, 3}
        };
        List<Item> ls = new ArrayList<>();
        for (int[] ints : arr) {
            ls.add(new Item(ints[0], ints[2], true));
            ls.add(new Item(ints[1], ints[2], false));
        }
        ls.sort(Comparator.comparingInt(a -> a.locate));
        ls.forEach(System.out::println);
        System.out.println("--------------------------------");

        TreeMap<Integer, Integer> map = new TreeMap<>();
        int maxHight = 0;
        for (Item item : ls) {
            maxHight = map.isEmpty() ? 0 : map.lastKey();
            if (item.isUp)
                map.put(item.hi, map.get(item.hi) == null ? 1 : map.get(item.hi) + 1);
            else {
                if (map.get(item.hi) == 1)
                    map.remove(item.hi);
                else
                    map.put(item.hi, map.get(item.hi) - 1);
            }
            if (maxHight != (map.isEmpty() ? 0 : map.lastKey())) {
                System.out.println(String.format("%d 位置的高度从 %d 变到了 %d",
                        item.locate,
                        maxHight,
                        map.isEmpty() ? 0 : map.lastKey()));
            }
        }
    }

    static class Item {
        private int locate;
        private int hi;
        private boolean isUp;

        public Item(int locate, int hi, boolean isUp) {
            this.locate = locate;
            this.hi = hi;
            this.isUp = isUp;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "locate=" + locate +
                    ", hi=" + hi +
                    ", isUp=" + isUp +
                    '}';
        }
    }
}
