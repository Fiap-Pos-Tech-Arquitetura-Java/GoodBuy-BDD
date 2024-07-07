package br.com.fiap.postech.goodbuy.helper;

import br.com.fiap.postech.goodbuy.model.Item;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ItemHelper {

    private final static String uniqueRandom = RandomStringUtils.randomAlphanumeric(30, 50);
    private final static AtomicLong countItem = new AtomicLong(1);

    public static Item getItem(boolean geraId) {
        UUID id = null;
        if (geraId) {
            id = UUID.randomUUID();
        }
        return new Item(
                id,
                uniqueRandom + countItem.getAndIncrement(),
                1000000.01,
                "muito mais que divertido",
                "carros",
                "urlDeUmGolfGTI",
                1L
        );
    }
}
