# jisho-java-api-wrapper
 A simple API wrapper written in Java for the JP/ENG dictionary Jisho.org implemented using Google's GSON library

 ## Usage
 By constructing a Jisho object one can perfrom searches and return all necessary data
 in the form of Java strings by doing the following:
 
 * First perform a search, various search methods can be found on Jisho.org
 ```java
Jisho var = new Jisho();
jisho.search(searchWord); 
```
* Then get the desired information by accessing the various lists
```java
var.getSlug(i);
```

Many of the methods provided have related methods which return the size of a specific list, i.e:
```java
var.getJlpt(i,j);
//i < var.getNumEntries()
//j < var.getJlptSize()
```

##Other
This project was an attempt at expanding my skillset, actual usability is questionable, however, if you do end
up using this library, please let me know how it goes.