package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // isEmpty() более естественна
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    // удаление элемента из списка заменил на skip одной "фальшивой персоны" (удаление из начала списка - O(n-1),
    // где n- количество элементов в списке)
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // использование StreamApi для создания коллекции излишне
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // использовал StreamApi, дабы избежать нагромождения кода
    // также добавил middleName (до рефакторинга дважды был secondName)

    return Stream.of(person.secondName(),person.firstName(),person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // использование StreamApi для обработки коллекций приоритетней
    return persons.stream()
        .collect(Collectors.toMap(
            Person::id,
            Person::firstName
        ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // использование StreamApi для обработки коллекций приоритетней
    return persons1.stream()
        .anyMatch(persons2::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // проще отфильтровать коллекцию по четным числам, а затем посчитать их количество
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    // под капотом HashSet есть HashMap с фиктивными значениями (ключ - элемент множества),
    // скорее всего такое поведение связано с тем, что при хэшировании
    // числа попадают в бакеты примерно с теми же индексами, что и сами числа,
    // за счет чего при выводе числа появляются в отсортированном порядке,
    // но разработчиками языка это не гарантируется
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
