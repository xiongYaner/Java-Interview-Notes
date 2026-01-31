# Java基础

Java是一种面向对象的编程语言，具有跨平台、安全性高、稳定性好等特点。Java基础是Java开发的核心，是每个Java开发者必须掌握的内容。

## 基本数据类型

Java有8种基本数据类型，分为数值类型、字符类型和布尔类型。

### 数值类型

| 类型 | 大小 | 范围 | 默认值 |
| ---- | ---- | ---- | ---- |
| byte | 1字节 | -128 ~ 127 | 0 |
| short | 2字节 | -32768 ~ 32767 | 0 |
| int | 4字节 | -2^31 ~ 2^31-1 | 0 |
| long | 8字节 | -2^63 ~ 2^63-1 | 0L |
| float | 4字节 | 1.4e-45 ~ 3.4e38 | 0.0f |
| double | 8字节 | 4.9e-324 ~ 1.8e308 | 0.0d |

### 字符类型

| 类型 | 大小 | 范围 | 默认值 |
| ---- | ---- | ---- | ---- |
| char | 2字节 | 0 ~ 65535 | '\u0000' |

### 布尔类型

| 类型 | 大小 | 范围 | 默认值 |
| ---- | ---- | ---- | ---- |
| boolean | 1字节 | true/false | false |

## 运算符

Java支持多种运算符，包括算术运算符、关系运算符、逻辑运算符、位运算符等。

### 算术运算符

| 运算符 | 描述 | 示例 |
| ---- | ---- | ---- |
| + | 加法 | a + b |
| - | 减法 | a - b |
| * | 乘法 | a * b |
| / | 除法 | a / b |
| % | 取模 | a % b |
| ++ | 自增 | a++ |
| -- | 自减 | a-- |

### 关系运算符

| 运算符 | 描述 | 示例 |
| ---- | ---- | ---- |
| == | 等于 | a == b |
| != | 不等于 | a != b |
| > | 大于 | a > b |
| < | 小于 | a < b |
| >= | 大于等于 | a >= b |
| <= | 小于等于 | a <= b |

### 逻辑运算符

| 运算符 | 描述 | 示例 |
| ---- | ---- | ---- |
| && | 逻辑与 | a && b |
| || | 逻辑或 | a || b |
| ! | 逻辑非 | !a |

### 位运算符

| 运算符 | 描述 | 示例 |
| ---- | ---- | ---- |
| & | 按位与 | a & b |
| | | 按位或 | a | b |
| ^ | 按位异或 | a ^ b |
| ~ | 按位取反 | ~a |
| << | 左移 | a << b |
| >> | 右移 | a >> b |
| >>> | 无符号右移 | a >>> b |

## 控制流程

Java支持多种控制流程语句，包括条件语句、循环语句和跳转语句。

### 条件语句

```java
// if语句
if (condition) {
    // 代码块
} else if (condition) {
    // 代码块
} else {
    // 代码块
}

// switch语句
switch (expression) {
    case value1:
        // 代码块
        break;
    case value2:
        // 代码块
        break;
    default:
        // 代码块
        break;
}
```

### 循环语句

```java
// for循环
for (int i = 0; i < 10; i++) {
    // 代码块
}

// while循环
while (condition) {
    // 代码块
}

// do-while循环
do {
    // 代码块
} while (condition);
```

### 跳转语句

```java
// break语句
break;

// continue语句
continue;

// return语句
return value;
```

## 数组

Java数组是一种固定长度的数据结构，用于存储相同类型的元素。

### 数组声明和初始化

```java
// 声明数组
int[] arr;
int arr[];

// 初始化数组
arr = new int[5];
int[] arr = new int[5];
int[] arr = {1, 2, 3, 4, 5};
```

### 数组遍历

```java
// for循环遍历
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// 增强for循环遍历
for (int num : arr) {
    System.out.println(num);
}
```

### 二维数组

```java
// 声明和初始化二维数组
int[][] arr = new int[2][3];
int[][] arr = {{1, 2, 3}, {4, 5, 6}};

// 遍历二维数组
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr[i].length; j++) {
        System.out.println(arr[i][j]);
    }
}
```

## 字符串

Java字符串是不可变的字符序列，使用String类表示。

### 字符串声明和初始化

```java
// 声明字符串
String str;

// 初始化字符串
str = "Hello World";
String str = "Hello World";
String str = new String("Hello World");
```

### 字符串常用方法

```java
// 获取字符串长度
int length = str.length();

// 连接字符串
String newStr = str1.concat(str2);
String newStr = str1 + str2;

// 比较字符串
boolean equals = str1.equals(str2);
int compare = str1.compareTo(str2);

// 查找子字符串
int index = str.indexOf("substring");
int lastIndex = str.lastIndexOf("substring");

// 截取子字符串
String substring = str.substring(startIndex);
String substring = str.substring(startIndex, endIndex);

// 替换子字符串
String newStr = str.replace("old", "new");

// 转换大小写
String upperCase = str.toUpperCase();
String lowerCase = str.toLowerCase();

// 去除空格
String trimStr = str.trim();
```

## 异常处理

Java异常处理是一种错误处理机制，用于处理程序运行时的错误。

### 异常分类

Java异常分为两类：
- 受检异常（Checked Exception）：必须在代码中处理的异常，例如IOException、SQLException等。
- 非受检异常（Unchecked Exception）：不需要在代码中处理的异常，例如NullPointerException、ArrayIndexOutOfBoundsException等。

### 异常处理语句

```java
// try-catch语句
try {
    // 可能抛出异常的代码
} catch (Exception e) {
    // 异常处理代码
} finally {
    // 无论是否抛出异常都会执行的代码
}

// throw语句
throw new Exception("异常信息");

// throws声明
public void method() throws Exception {
    // 可能抛出异常的代码
}
```

## 总结

Java基础是Java开发的核心，是每个Java开发者必须掌握的内容。本文介绍了Java基础的基本数据类型、运算符、控制流程、数组、字符串和异常处理等内容，希望对大家有所帮助。