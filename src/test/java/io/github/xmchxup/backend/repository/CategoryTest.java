package io.github.xmchxup.backend.repository;

import com.google.gson.Gson;
import io.github.xmchxup.backend.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@SpringBootTest
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test() {
        String json = "[\n" +
                "  {\n" +
                "    name: \"汽车\",\n" +
                "    key: \"car\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/750x/eb/47/44/eb4744eaa3b3ccd89749fa3470e2b0de.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"健身\",\n" +
                "    key: \"fitness\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/25/14/29/251429345940a47490cc3d47dfe0a8eb.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"壁紙\",\n" +
                "    key: \"wallpaper\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/03/48/b6/0348b65919fcbe1e4f559dc4feb0ee13.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"极客\",\n" +
                "    key: \"geek\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/750x/66/b1/29/66b1296d36598122e6a4c5452b5a7149.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"美图\",\n" +
                "    key: \"photo\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/72/8c/b4/728cb43f48ca762a75da645c121e5c57.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"食物\",\n" +
                "    key: \"food\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/7d/ef/15/7def15ac734837346dac01fad598fc87.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"大自然\",\n" +
                "    key: \"nature\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/b9/82/d4/b982d49a1edd984c4faef745fd1f8479.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"艺术\",\n" +
                "    key: \"art\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/736x/f4/e5/ba/f4e5ba22311039662dd253be33bf5f0e.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"旅行\",\n" +
                "    key: \"travel\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/fa/95/98/fa95986f2c408098531ca7cc78aee3a4.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"名言\",\n" +
                "    key: \"quotes\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/46/7c/17/467c17277badb00b638f8ec4da89a358.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"猫\",\n" +
                "    key: \"cat\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/6c/3c/52/6c3c529e8dadc7cffc4fddedd4caabe1.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"狗\",\n" +
                "    key: \"dog\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/1b/c8/30/1bc83077e363db1a394bf6a64b071e9f.jpg\"\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"其它\",\n" +
                "    key: \"other\",\n" +
                "    image:\n" +
                "      \"https://i.pinimg.com/236x/2e/63/c8/2e63c82dfd49aca8dccf9de3f57e8588.jpg\"\n" +
                "  }\n" +
                "]";
        System.out.println(json);
        Gson gson = new Gson();
        Category[] categories = gson.fromJson(json, Category[].class);
        for (Category category : categories) {
            System.out.println(category);
        }
        categoryRepository.saveAll(Arrays.asList(categories));
    }
}
