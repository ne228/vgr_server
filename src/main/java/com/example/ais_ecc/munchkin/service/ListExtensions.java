package com.example.ais_ecc.munchkin.service;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListExtensions {

    private static final Random random = new Random();

    // Метод расширения для извлечения случайного элемента из списка List<Card> или его наследников
    public static <T extends Card> T extractRandom(List<T> list) {
        if (list.isEmpty()) {
            return null; // Возвращаем null, если список пуст
        }
        int randomIndex = random.nextInt(list.size()); // Генерируем случайный индекс
        return list.remove(randomIndex); // Удаляем и возвращаем элемент по этому индексу
    }
    public static <T extends Card> List<T> extractCardsByType(List<Card> cards, Class<T> type) {
        List<T> filteredCards = new ArrayList<>();
        for (Card card : cards) {
            if (type.isInstance(card)) {
                filteredCards.add(type.cast(card));
            }
        }
        return filteredCards;
    }

    // Метод расширения для извлечения элемента по индексу из списка List<Card> или его наследников
    public static <T extends Card> T extractByIndex(List<T> list, int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы списка");
        }
        return list.remove(index); // Удаляем и возвращаем элемент по указанному индексу
    }

    // Метод расширения для извлечения элемента по идентификатору из списка List<Card> или его наследников
    public static <T extends Card> T extractById(List<T> list, String id) {
        for (T card : list) {
            if (card.getId().equalsIgnoreCase(id)) {
                list.remove(card); // Удаляем элемент из списка
                return card; // Возвращаем найденный элемент
            }
        }
        return null; // Если элемент с заданным id не найден, возвращаем null
    }


}
