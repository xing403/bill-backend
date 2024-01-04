package com.bill.backend.utils;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

public class MathExpressionTextCreator extends DefaultTextCreator {

    @Override
    public String getText() {
        //计算式的结果
        int result = 0;

        Random random = new Random();

        int x = random.nextInt(10);

        int y = random.nextInt(10);

        StringBuilder text = new StringBuilder();

        //随机运算符 取0 1 2 3 四个整数
        int operatorRandom = (int) Math.round(Math.random() * 3);
        //除法失败情况较多，失败就改为+
        if (operatorRandom == 0 || operatorRandom == 1) {
            if (!(y == 0) && x % y == 0) {
                result = x / y;
                text.append(x);
                text.append("/");
                text.append(y);
            } else {
                result = x + y;
                text.append(x);
                text.append("+");
                text.append(y);
            }
        } else if (operatorRandom == 2) {
            if (x >= y) {
                result = x - y;
                text.append(x);
                text.append("-");
                text.append(y);
            } else {
                result = y - x;
                text.append(y);
                text.append("-");
                text.append(x);
            }
        } else {
            result = x * y;
            text.append(x);
            text.append("*");
            text.append(y);
        }
        //返回运算表达式和结果，用@符号分割
        text.append("=?@").append(result);

        return text.toString();
    }

}