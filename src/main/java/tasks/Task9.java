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
    // проверка isEmpty не нужна, потому что Stream API безопасно все обработает
    // (раньше она была нужна из-за удаления первого элемента)
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
    // обновил до версии с функцией мержа, чтобы код не упал при попытке
    // добавить 2 элемента с одинаковым id
    return persons.stream()
        .collect(Collectors.toMap(
            Person::id,
            Person::firstName,
            (oldId,newId) -> oldId
        ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // использование StreamApi для обработки коллекций приоритетней
    // перевел одну коллекцию в Set (для проверки на вхождение)
    // за счет чего с O(n*m), где n,m - количество элементов в 1, 2 коллекции соответственно
    // ушел на O(n+m)

    Set<Person> person2Set = new HashSet<>(persons2);
    return persons1.stream()
        .anyMatch(person2Set::contains);
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
    // я посмотрел в Integer.java : hashCode от Integer равен самому числу.
    // при создании: new HashSet<>(integers), множество должно содержать элементы integers,
    // емкость внутреннего backets должна быть как минимум равна длине integers,
    // т.к. длина backets - равна степени двойки, то емкость в нашем случае будет равна 2^14,
    // итого наши числа распределятся в ячейки backets с теми же индексами, что и сами числа,
    // поэтому при выводе получим числа в отсортированном порядке
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
