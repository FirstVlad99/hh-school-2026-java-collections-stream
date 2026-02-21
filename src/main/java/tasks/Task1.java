package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  /**
   *Метод для получения по списку ID соответствующих объектов типа Person
   * Время : O(m) (O(1) вставка в Map и поиск в Map)
   * Память: O(n)
   * Где n - количество элементов в personIds,
   * m - количество элементов в множестве persons
   * @param personIds - список ID пользователей
   * @return список объектов типа Person
   */
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    // Создаю отображение ID - Person
    // Для дальнейшего получения за O(1) из Map объекта типа Person
    // и составления итогового списка на основе входящего (с учетом возможных повторений)

    Map<Integer,Person> personMap = persons.stream()
            .collect(Collectors.toMap(
                    Person::id,
                    person -> person
            ));
      return personIds.stream()
              .map(personMap::get)
              .toList();

  }
}