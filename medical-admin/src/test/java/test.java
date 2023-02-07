import java.util.*;

public class test {
    public static void main(String[] args) {
        String str = new Scanner(System.in).next();
        System.out.println(Count(str));
    }
    public static String Count(String str) {
        StringBuilder answer = new StringBuilder();
        HashMap<String, Integer> element = new HashMap<>();
        List<String> lst = new ArrayList<>();
        List<Integer> lstNum = new ArrayList<>();
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                int tmp = i;
                ++i;
                while (i < str.length() && (str.charAt(i) <= 'z' && str.charAt(i) >= 'a')) {
                    ++i;
                }
                String subStr = str.substring(tmp, i);
                if (!element.containsKey(subStr)) {
                    element.put(subStr, 0);
                }
                lst.add(subStr);
                if (i < str.length() && (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                    int sum = 0;
                    while (i < str.length() && (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {

                        sum = sum * 10 + str.charAt(i) - '0';
                        ++i;
                    }
                    Integer value = element.get(subStr);
                    element.put(subStr, value + sum);
                    lstNum.add(sum);
                } else {
                    Integer value = element.get(subStr);
                    element.put(subStr, value + 1);
                    lstNum.add(1);
                }
                --i;
            } else if (str.charAt(i) == '(') {
                s.push(lst.size());
            } else if (str.charAt(i) == ')') {
                ++i;
                int sum;
                if (i < str.length() && (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                    sum = 0;
                    while (i < str.length() && (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                        sum = sum * 10 + str.charAt(i) - '0';
                        ++i;
                    }
                } else {
                    continue;
                }
                sum--;
                for (int j = s.pop(); j < lst.size(); ++j) {
                    Integer value = element.get(lst.get(j));
                    element.put(lst.get(j), value + sum * lstNum.get(j));
                    Integer li = lstNum.get(j);
                    li = li + sum * li;
                    lstNum.set(j, li);
                }
                --i;
            }
        }
        for (int i = 0; i < lst.size(); ++i) {
            if (element.containsKey(lst.get(i))) {
                Integer value = element.get(lst.get(i));
                answer.append(lst.get(i) + value);
                element.remove(lst.get(i));
            }
        }
        return answer.toString();
    }
}