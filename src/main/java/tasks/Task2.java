package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  /**
   *Метод для объединения двух коллекций объектов Person,
   * сортировке по дате создания,
   * ограничению на вывод первых limit штук
   * @param persons1 - первая коллекция объектов типа Person
   * @param persons2 - вторая коллекция объектов типа Person
   * @param limit - ограничение на количество выводимых объектов типа Person
   * @return объединенный список объектов типа Person
   */
  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    return Stream.concat(persons1.stream(),persons2.stream())
        .sorted(Comparator.comparing(Person::createdAt))
        .limit(limit)
        .toList();


  }
}
