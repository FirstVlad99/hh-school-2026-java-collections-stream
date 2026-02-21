package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  /**
   *Метод для получения из коллекции компаний
   * всевозможных уникальных названий вакансий
   * @param companies - коллекция с объектами типа Company
   * @return  множество названий вакансий
   */
  public static Set<String> vacancyNames(Collection<Company> companies) {
    return companies.stream()
        .map(Company::getVacancies)
        .flatMap(Collection::stream)
        .map(Vacancy::getTitle)
        .collect(Collectors.toSet());
  }

}
