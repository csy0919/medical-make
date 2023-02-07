import java.util.Scanner;

/**
 * @author Csy
 * @date 2022/12/20 22:17
 */
public class test1 {
    public static void main(String[] args) {
        char[] zm = new char[26];
        for (int i = 0; i < zm.length; i++) {
            zm[i] = (char) ('a' + i);
        }
        System.out.println("请输入要输出的长度");
        int input = new Scanner(System.in).nextInt();
        Dg(new char[input], zm, input-1);
    }
    public static void Dg(char[] bf, char[] zm, int len){
        if(len == -1){
            for(int i=bf.length-1; i>=0; i--) {
                System.out.print(bf[i]);
            }
            System.out.println();
            return;
        }
        for(int i=0; i<zm.length; i++){
            bf[len] = zm[i];
            Dg(bf, zm, len-1);
        }
    }
}



