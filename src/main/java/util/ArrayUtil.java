//package util;
//
////import model.MovieDTO;
//
//public class ArrayUtil {
//
//    // 1. int[]
//    // A. size()
//    public static int size(int[] array) {
//        return array.length;
//    }
//
//    public static int size(MovieDTO[] array) {
//        return array.length;
//    }
//
//    // B. isEmpty()
//    public static boolean isEmpty(int[] array) {
//        return size(array) == 0;
//    }
//
//    // C. get()
//    public static int get(int[] array, int index) {
//        return array[index];
//    }
//
//    // C-1. getBoard()
//    public static MovieDTO get(MovieDTO[] array, int index) {
//        return array[index];
//    }
//
//    // D. contains()
//    public static boolean contains(int[] array, int element) {
//        for (int i = 0; i < size(array); i++) {
//            if (element == get(array, i)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // D. contains() , element 는 있는지 검사할 친구
//    public static boolean contains(MovieDTO[] array, MovieDTO element) {
//        for (MovieDTO b : array) {
//            if (element.equals(b)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    // E. indexOf()
//    public static int indexOf(int[] array, int element) {
//        for (int i = 0; i < size(array); i++) {
//            if (element == get(array, i)) {
//                return i;
//            }
//        }
//
//        return -1;
//    }
//
//    // E. indexOf()
//    public static int indexOf(MovieDTO[] array, MovieDTO element) {
//        for (int i = 0; i < size(array); i++) {
//            if (element.equals(get(array, i))) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    // F. add()
//    public static int[] add(int[] array, int element) {
//        int[] temp = new int[size(array) + 1];
//        for (int i = 0; i < size(array); i++) {
//            temp[i] = get(array, i);
//        }
//        temp[size(array)] = element;
//
//        return temp;
//    }
//
//
//    // G. add()
//    public static int[] add(int[] array, int index, int element) {
//        int[] temp = new int[size(array) + 1];
//        for (int i = 0; i < index; i++) {
//            temp[i] = get(array, i);
//        }
//        temp[index] = element;
//        for (int i = index; i < size(array); i++) {
//            temp[i + 1] = get(array, i);
//        }
//
//        temp = new int[0];
//        for (int i = 0; i < size(array); ) {
//            if (i != index) {
//                temp = add(temp, get(array, i));
//                i++;
//            } else {
//                temp = add(temp, element);
//            }
//        }
//
//        return temp;
//    }
//
//    // H. set()
//    public static int set(int[] array, int index, int element) {
//        int original = get(array, index);
//        array[index] = element;
//
//        return original;
//    }
//
//    // I. removeByIndex()
//    public static int[] removeByIndex(int[] array, int index) {
//        int[] temp = new int[0];
//        for (int i = 0; i < size(array); i++) {
//            if (i != index) {
//                temp = add(temp, get(array, i));
//            }
//        }
//
//        return temp;
//    }
//
//    // I-1. removeByIndex() - Board
//    public static MovieDTO[] removeByIndex(MovieDTO[] array, int index) {
//        MovieDTO[] temp = new MovieDTO[0];
//        for (int i = 0; i < size(array); i++) {
//            if (i != index) {
//                temp = add(temp, get(array, i));
//            }
//        }
//        return temp;
//    }
//
//
//
//    // J. removeByElement
//    public static int[] removeByElement(int[] array, int element) {
//        return removeByIndex(array, indexOf(array, element));
//    }
//
//    // K. sort()
//    public static void sort(int[] array) {
//        for (int i = 0; i < array.length - 1; i++) {
//            if (array[i] > array[i + 1]) {
//                int temp = array[i];
//                array[i] = array[i + 1];
//                array[i + 1] = temp;
//                i = -1;
//            }
//        }
//    }
//
//
//    // F-1. addBoard()
//    public static MovieDTO[] add(MovieDTO[] array, MovieDTO element) {
//        MovieDTO[] temp = new MovieDTO[size(array) + 1];
//        for (int i = 0; i < size(array); i++) {
//            temp[i] = get(array, i);
//        }
//        temp[size(array)] = element;
//        return temp;
//    }
//
//    // G. add()
//    public static MovieDTO[] add(MovieDTO[] array, int index, MovieDTO element) {
//        MovieDTO[] temp = new MovieDTO[size(array) + 1];
//        for (int i = 0; i < size(array); ) {
//            if (i != index) {
//                temp = add(temp, get(array, i));
//                i++;
//            } else {
//                temp = add(temp, element);
//            }
//        }
//        return temp;
//    }
//
//    public static MovieDTO set(MovieDTO[] array, int index, MovieDTO element) {
//        MovieDTO temp = get(array, index);
//        array[index] = element;
//        return temp;
//
//    }
//
//    public static MovieDTO[] remove(MovieDTO[] array, int index) {
//        MovieDTO[] temp = new MovieDTO[0];
//        for (int i = 0; i < size(array); i++) {
//            if (i != index) {
//                temp = add(temp, get(array, i));
//            }
//        }
//        return temp;
//    }
//
//    public static MovieDTO[] remove(MovieDTO[] array, int index, MovieDTO element) {
//        return remove(array, indexOf(array, element));
//    }
//
//
//}
//
//
//
