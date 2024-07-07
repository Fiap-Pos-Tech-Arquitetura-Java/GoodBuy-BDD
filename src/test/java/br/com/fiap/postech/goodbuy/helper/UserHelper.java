package br.com.fiap.postech.goodbuy.helper;

import br.com.fiap.postech.goodbuy.model.User;
import br.com.fiap.postech.goodbuy.security.JwtService;
import br.com.fiap.postech.goodbuy.security.enums.UserRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserHelper {

    private static class GeraCpfCnpj {

        private static int randomiza(int n) {
            int ranNum = (int) (Math.random() * n);
            return ranNum;
        }

        private static int mod(int dividendo, int divisor) {
            return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
        }

        public static String cpf() {
            int n = 9;
            int n1 = randomiza(n);
            int n2 = randomiza(n);
            int n3 = randomiza(n);
            int n4 = randomiza(n);
            int n5 = randomiza(n);
            int n6 = randomiza(n);
            int n7 = randomiza(n);
            int n8 = randomiza(n);
            int n9 = randomiza(n);
            int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

            d1 = 11 - (mod(d1, 11));

            if (d1 >= 10)
                d1 = 0;

            int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

            d2 = 11 - (mod(d2, 11));


            if (d2 >= 10)
                d2 = 0;
            return "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;
        }
    }

    private final static String uniqueRandom = RandomStringUtils.randomAlphanumeric(30, 50);
    private final static AtomicLong countUser = new AtomicLong(1);

    public static User getUser(boolean geraId, UserRole userRole) {
        UUID id = null;
        if (geraId) {
            id = UUID.randomUUID();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new User(
                id,
                "anderson.wagner" + uniqueRandom + countUser.getAndIncrement() +"@gmail.com",
                "Anderson Wagner",
                GeraCpfCnpj.cpf(),
                encoder.encode("123456"),
                userRole
        );
    }

    public static String getToken(User user) {
        br.com.fiap.postech.goodbuy.security.User userSecurity = getUserForSecurity(user);
        return "Bearer " + new JwtService().generateToken(userSecurity);
    }

    private static br.com.fiap.postech.goodbuy.security.User getUserForSecurity(User user) {
        br.com.fiap.postech.goodbuy.security.User userSecurity =
                new br.com.fiap.postech.goodbuy.security.User(user.login(), user.password(), user.role());
        return userSecurity;
    }
}
