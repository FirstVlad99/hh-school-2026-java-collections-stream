package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны - personConverter.convert()
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 {

  private final PersonConverter personConverter;

  public Task4(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  /**
   *Метод для получения списка, элементами которого являются
   * ApiPersonDto, по списку с объектами типа Person
   * @param persons - коллекция с объектами типа Person
   * @return  список объектов типа ApiPersonDto
   */
  public List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
        .map(personConverter::convert)
        .toList();
  }
}
