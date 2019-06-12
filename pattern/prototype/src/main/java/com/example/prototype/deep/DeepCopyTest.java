package com.example.prototype.deep;

/**
 * lingfan
 * 2019-06-12 20:14
 */
public class DeepCopyTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Monkey monkey = new Monkey();

        Bangzi bangzi = new Bangzi();
        bangzi.setHigh(11);
        bangzi.setWidth(22);

        JInGuZhou jInGuZhou = new JInGuZhou();
        jInGuZhou.setColor("red");

        monkey.setBangzi(bangzi);
        monkey.setJInGuZhou(jInGuZhou);

        Monkey clone = (Monkey) monkey.clone();

        System.out.println(monkey);
        System.out.println(clone);



        System.out.println("-------------");


        System.out.println(monkey.getBangzi());
        System.out.println(clone.getBangzi());



        Monkey deepClone =  monkey.deepClone();

        System.out.println(monkey);
        System.out.println(deepClone);



        System.out.println("-------------");


        System.out.println(monkey.getBangzi());
        System.out.println(deepClone.getBangzi());
    }
}
