# 集合框架

Java集合框架是Java提供的一组用于存储和操作对象的类和接口。它提供了各种数据结构，如列表、集合、映射、队列和栈，以及用于操作这些数据结构的方法。

## 集合框架的层次结构

Java集合框架分为三个主要部分：
- Collection接口：用于存储单个元素的集合
- Map接口：用于存储键值对的集合
- Iterable接口：用于遍历集合的接口

## Collection接口

Collection接口是Java集合框架的根接口，它定义了所有集合的基本操作。Collection接口有三个主要的子接口：
- List接口：有序集合，允许重复元素
- Set接口：无序集合，不允许重复元素
- Queue接口：队列，用于存储和操作队列数据结构

### List接口

List接口是有序集合，允许重复元素。List接口有两个主要的实现类：
- ArrayList：基于动态数组的实现
- LinkedList：基于链表的实现

#### ArrayList

```java
// 创建ArrayList
List<String> list = new ArrayList<>();

// 添加元素
list.add("Java");
list.add("Python");
list.add("C++");

// 访问元素
String element = list.get(0);

// 修改元素
list.set(0, "JavaScript");

// 删除元素
list.remove(1);

// 遍历元素
for (String str : list) {
    System.out.println(str);
}
```

#### LinkedList

```java
// 创建LinkedList
List<String> list = new LinkedList<>();

// 添加元素
list.add("Java");
list.add("Python");
list.add("C++");

// 访问元素
String element = list.get(0);

// 修改元素
list.set(0, "JavaScript");

// 删除元素
list.remove(1);

// 遍历元素
for (String str : list) {
    System.out.println(str);
}

// 作为队列使用
Queue<String> queue = new LinkedList<>();
queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

while (!queue.isEmpty()) {
    System.out.println(queue.poll());
}

// 作为栈使用
Deque<String> stack = new LinkedList<>();
stack.push("Java");
stack.push("Python");
stack.push("C++");

while (!stack.isEmpty()) {
    System.out.println(stack.pop());
}
```

### Set接口

Set接口是无序集合，不允许重复元素。Set接口有三个主要的实现类：
- HashSet：基于哈希表的实现
- TreeSet：基于红黑树的实现
- LinkedHashSet：基于哈希表和链表的实现

#### HashSet

```java
// 创建HashSet
Set<String> set = new HashSet<>();

// 添加元素
set.add("Java");
set.add("Python");
set.add("C++");
set.add("Java"); // 重复元素，不会添加

// 删除元素
set.remove("Python");

// 遍历元素
for (String str : set) {
    System.out.println(str);
}

// 检查元素是否存在
boolean contains = set.contains("C++");
```

#### TreeSet

```java
// 创建TreeSet
Set<String> set = new TreeSet<>();

// 添加元素
set.add("Java");
set.add("Python");
set.add("C++");

// 删除元素
set.remove("Python");

// 遍历元素（按自然顺序）
for (String str : set) {
    System.out.println(str);
}

// 检查元素是否存在
boolean contains = set.contains("C++");

// 获取第一个和最后一个元素
String first = ((TreeSet<String>) set).first();
String last = ((TreeSet<String>) set).last();
```

#### LinkedHashSet

```java
// 创建LinkedHashSet
Set<String> set = new LinkedHashSet<>();

// 添加元素
set.add("Java");
set.add("Python");
set.add("C++");

// 删除元素
set.remove("Python");

// 遍历元素（按插入顺序）
for (String str : set) {
    System.out.println(str);
}

// 检查元素是否存在
boolean contains = set.contains("C++");
```

### Queue接口

Queue接口是队列，用于存储和操作队列数据结构。Queue接口有两个主要的实现类：
- LinkedList：基于链表的实现
- PriorityQueue：基于优先级堆的实现

#### LinkedList

```java
// 创建Queue
Queue<String> queue = new LinkedList<>();

// 添加元素
queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

// 访问队首元素
String head = queue.peek();

// 删除队首元素
String removed = queue.poll();

// 遍历元素
while (!queue.isEmpty()) {
    System.out.println(queue.poll());
}
```

#### PriorityQueue

```java
// 创建PriorityQueue
Queue<String> queue = new PriorityQueue<>();

// 添加元素
queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

// 访问队首元素（按自然顺序）
String head = queue.peek();

// 删除队首元素
String removed = queue.poll();

// 遍历元素（按优先级）
while (!queue.isEmpty()) {
    System.out.println(queue.poll());
}

// 自定义比较器
Queue<String> customQueue = new PriorityQueue<>(new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
});
customQueue.offer("Java");
customQueue.offer("Python");
customQueue.offer("C++");
```

## Map接口

Map接口是用于存储键值对的集合。Map接口有三个主要的实现类：
- HashMap：基于哈希表的实现
- TreeMap：基于红黑树的实现
- LinkedHashMap：基于哈希表和链表的实现

### HashMap

```java
// 创建HashMap
Map<String, Integer> map = new HashMap<>();

// 添加键值对
map.put("Java", 1);
map.put("Python", 2);
map.put("C++", 3);

// 访问值
Integer value = map.get("Java");

// 修改值
map.put("Java", 4);

// 删除键值对
map.remove("Python");

// 遍历键值对
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 检查键是否存在
boolean containsKey = map.containsKey("C++");

// 检查值是否存在
boolean containsValue = map.containsValue(3);
```

### TreeMap

```java
// 创建TreeMap
Map<String, Integer> map = new TreeMap<>();

// 添加键值对
map.put("Java", 1);
map.put("Python", 2);
map.put("C++", 3);

// 访问值
Integer value = map.get("Java");

// 修改值
map.put("Java", 4);

// 删除键值对
map.remove("Python");

// 遍历键值对（按自然顺序）
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 检查键是否存在
boolean containsKey = map.containsKey("C++");

// 检查值是否存在
boolean containsValue = map.containsValue(3);

// 获取第一个和最后一个键
String firstKey = ((TreeMap<String, Integer>) map).firstKey();
String lastKey = ((TreeMap<String, Integer>) map).lastKey();
```

### LinkedHashMap

```java
// 创建LinkedHashMap
Map<String, Integer> map = new LinkedHashMap<>();

// 添加键值对
map.put("Java", 1);
map.put("Python", 2);
map.put("C++", 3);

// 访问值
Integer value = map.get("Java");

// 修改值
map.put("Java", 4);

// 删除键值对
map.remove("Python");

// 遍历键值对（按插入顺序）
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// 检查键是否存在
boolean containsKey = map.containsKey("C++");

// 检查值是否存在
boolean containsValue = map.containsValue(3);
```

## 集合工具类

Java提供了一些集合工具类，用于操作集合。

### Collections类

```java
// 排序
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Python");
list.add("C++");
Collections.sort(list);

// 反转
Collections.reverse(list);

// 随机洗牌
Collections.shuffle(list);

// 最大值和最小值
String max = Collections.max(list);
String min = Collections.min(list);

// 填充
Collections.fill(list, "Java");

// 替换
Collections.replaceAll(list, "Java", "Python");
```

### Arrays类

```java
// 数组转列表
String[] array = {"Java", "Python", "C++"};
List<String> list = Arrays.asList(array);

// 数组排序
Arrays.sort(array);

// 数组查找
int index = Arrays.binarySearch(array, "Python");

// 数组填充
Arrays.fill(array, "Java");

// 数组复制
String[] copy = Arrays.copyOf(array, array.length);

// 数组比较
boolean equals = Arrays.equals(array, copy);
```

## 总结

Java集合框架是Java提供的一组用于存储和操作对象的类和接口。它提供了各种数据结构，如列表、集合、映射、队列和栈，以及用于操作这些数据结构的方法。本文介绍了Java集合框架的基本概念，包括Collection接口、List接口、Set接口、Queue接口、Map接口和集合工具类等内容，希望对大家有所帮助。