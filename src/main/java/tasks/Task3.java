package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {
  /**
   *Метод для сортировки коллекции с объектами типа Person по:
   * фамилии, имени, дате создания
   * @param persons - коллекция с объектами типа Person
   * @return отсортированный список объектов типа Person
   */
  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream()
        .sorted(
            Comparator.nullsFirst(
                Comparator.comparing(Person::secondName,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(Person::firstName,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(Person::createdAt)
            )
        )
        .toList();
  }
}
