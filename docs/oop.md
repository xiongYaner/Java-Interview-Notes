# 面向对象

面向对象编程是Java的核心特性之一，它将现实世界中的事物抽象为对象，通过封装、继承和多态等特性实现代码的复用和扩展。

## 类和对象

### 类的定义

```java
public class Person {
    // 成员变量
    private String name;
    private int age;
    
    // 构造方法
    public Person() {
    }
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // 成员方法
    public void sayHello() {
        System.out.println("Hello, my name is " + name);
    }
    
    //  getter和setter方法
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
```

### 对象的创建和使用

```java
// 创建对象
Person person = new Person();

// 使用对象
person.setName("张三");
person.setAge(18);
person.sayHello();

// 使用带参数的构造方法创建对象
Person person2 = new Person("李四", 20);
person2.sayHello();
```

## 封装

封装是面向对象编程的一个重要特性，它将数据和方法封装在一个类中，通过访问修饰符控制数据的访问权限。

### 访问修饰符

Java有4种访问修饰符：

| 修饰符 | 访问范围 |
| ---- | ---- |
| private | 同一类内 |
| default | 同一包内 |
| protected | 同一包内或子类 |
| public | 所有类 |

### 封装示例

```java
public class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name != null && name.length() > 0) {
            this.name = name;
        }
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age > 0 && age < 120) {
            this.age = age;
        }
    }
}
```

## 继承

继承是面向对象编程的另一个重要特性，它允许一个类继承另一个类的属性和方法。

### 继承的定义

```java
// 父类
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// 子类
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    public void bark() {
        System.out.println(name + " is barking");
    }
}
```

### 继承的使用

```java
Dog dog = new Dog("旺财");
dog.eat();
dog.sleep();
dog.bark();
```

### 方法重写

```java
public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }
    
    @Override
    public void eat() {
        System.out.println(name + " is eating fish");
    }
    
    public void meow() {
        System.out.println(name + " is meowing");
    }
}

// 使用
Cat cat = new Cat("咪咪");
cat.eat(); // 输出：咪咪 is eating fish
cat.sleep();
cat.meow();
```

## 多态

多态是面向对象编程的核心特性，它允许不同类的对象对同一方法做出不同的响应。

### 多态的实现

```java
// 父类
public class Shape {
    public void draw() {
        System.out.println("Drawing a shape");
    }
}

// 子类
public class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

public class Rectangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}

public class Triangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a triangle");
    }
}
```

### 多态的使用

```java
Shape[] shapes = new Shape[3];
shapes[0] = new Circle();
shapes[1] = new Rectangle();
shapes[2] = new Triangle();

for (Shape shape : shapes) {
    shape.draw();
}

// 输出：
// Drawing a circle
// Drawing a rectangle
// Drawing a triangle
```

## 抽象类和接口

### 抽象类

```java
public abstract class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public abstract void makeSound();
    
    public void eat() {
        System.out.println(name + " is eating");
    }
}

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " is barking");
    }
}
```

### 接口

```java
public interface Flyable {
    void fly();
}

public interface Swimmable {
    void swim();
}

public class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Fish implements Swimmable {
    @Override
    public void swim() {
        System.out.println("Fish is swimming");
    }
}

public class Duck implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("Duck is flying");
    }
    
    @Override
    public void swim() {
        System.out.println("Duck is swimming");
    }
}
```

## 内部类

### 成员内部类

```java
public class OuterClass {
    private int outerVar;
    
    public class InnerClass {
        private int innerVar;
        
        public void innerMethod() {
            System.out.println("Outer variable: " + outerVar);
            System.out.println("Inner variable: " + innerVar);
        }
    }
    
    public void outerMethod() {
        InnerClass inner = new InnerClass();
        inner.innerVar = 10;
        inner.innerMethod();
    }
}

// 使用
OuterClass outer = new OuterClass();
outer.outerVar = 20;
outer.outerMethod();
```

### 静态内部类

```java
public class OuterClass {
    private static int staticVar;
    
    public static class StaticInnerClass {
        private int innerVar;
        
        public void innerMethod() {
            System.out.println("Static variable: " + staticVar);
            System.out.println("Inner variable: " + innerVar);
        }
    }
    
    public static void outerMethod() {
        StaticInnerClass inner = new StaticInnerClass();
        inner.innerVar = 10;
        inner.innerMethod();
    }
}

// 使用
OuterClass.staticVar = 20;
OuterClass.outerMethod();
```

### 局部内部类

```java
public class OuterClass {
    public void outerMethod() {
        final int localVar = 10;
        
        class LocalInnerClass {
            private int innerVar;
            
            public void innerMethod() {
                System.out.println("Local variable: " + localVar);
                System.out.println("Inner variable: " + innerVar);
            }
        }
        
        LocalInnerClass inner = new LocalInnerClass();
        inner.innerVar = 20;
        inner.innerMethod();
    }
}

// 使用
OuterClass outer = new OuterClass();
outer.outerMethod();
```

### 匿名内部类

```java
public interface Greeting {
    void sayHello();
}

public class Main {
    public static void main(String[] args) {
        Greeting greeting = new Greeting() {
            @Override
            public void sayHello() {
                System.out.println("Hello from anonymous inner class");
            }
        };
        
        greeting.sayHello();
    }
}
```

## 总结

面向对象编程是Java的核心特性之一，它将现实世界中的事物抽象为对象，通过封装、继承和多态等特性实现代码的复用和扩展。本文介绍了面向对象编程的基本概念，包括类和对象、封装、继承、多态、抽象类和接口、内部类等内容，希望对大家有所帮助。