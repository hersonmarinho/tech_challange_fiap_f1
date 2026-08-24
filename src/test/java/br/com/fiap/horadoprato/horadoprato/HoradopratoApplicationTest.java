package br.com.fiap.horadoprato.horadoprato;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class HoradopratoApplicationTest {

    @Test
    void deveExecutarMainChamandoSpringApplicationRun() {
        ConfigurableApplicationContext context = Mockito.mock(ConfigurableApplicationContext.class);
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(HoradopratoApplication.class, new String[]{"arg1"}))
                    .thenReturn(context);

            HoradopratoApplication.main(new String[]{"arg1"});

            mocked.verify(() -> SpringApplication.run(HoradopratoApplication.class, new String[]{"arg1"}));
        }
    }
}
