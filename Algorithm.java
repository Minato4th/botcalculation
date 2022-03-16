package password;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Algorithm {

    enum Escape {
        SYMBOL_33('!'),
        SYMBOL_34('"'),
        SYMBOL_35('#'),
        SYMBOL_36('$'),
        SYMBOL_37('%'),
        SYMBOL_38('&'),
        SYMBOL_39('\''),
        SYMBOL_40('('),
        SYMBOL_41(')'),
        SYMBOL_42('*'),
        SYMBOL_43('+'),
        SYMBOL_44(','),
        SYMBOL_45('-'),
        SYMBOL_46('.'),
        SYMBOL_47('/'),
        SYMBOL_58(':'),
        SYMBOL_59(';'),
        SYMBOL_60('<'),
        SYMBOL_61('='),
        SYMBOL_62('>'),
        SYMBOL_63('?'),
        SYMBOL_64('@'),

        SYMBOL_91(']'),
        SYMBOL_92('\\'),
        SYMBOL_93('['),
        SYMBOL_94('^'),
        SYMBOL_95('_'),
        SYMBOL_96('`'),

        SYMBOL_123('}'),
        SYMBOL_124('|'),
        SYMBOL_125('{'),
        SYMBOL_126('~');

        char c;

        Escape(char c) {
            this.c = c;
        }

        public char getC() {
            return c;
        }
    }

    public static final int[] FULL_ESCAPE = {
            33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 95, 96, 123, 124, 125, 126
    };

    public static void main(String[] args) {
        String pass = hashPassword2("1");
        System.out.println(pass);
        System.out.println(securePassword(pass));
        System.out.println(securePasswordFullEscapeChar(pass));
        System.out.println(securePasswordOnlyLetters(pass));
        System.out.println(securePasswordEscape(pass));
        System.out.println(securePasswordProgrammatic(pass));
    }

    public static String securePasswordProgrammatic(String hashPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 8; i < hashPassword.length(); i += 8, j += 8) {
            String letters = hashPassword.substring(i, j);

            int sum = letters.chars().sum();
            while (sum > 125) {
                sum -= 90;
            }

            result.append((char) sum);
        }
        return result.toString();
    }

    public static String securePasswordEscape(String hashPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 8; i < hashPassword.length(); i += 8, j += 8) {
            String letters = hashPassword.substring(i, j);

            int sum = letters.chars().sum();
            while (sum > 125) {
                sum -= 90;
            }

            switch (sum) {
                case 34 :
                    sum = 33;
                    break;
                case 39 :
                case 43 :
                case 44 :
                    sum = 35;
                    break;
                case 45 :
                case 46 :
                case 47 :
                    sum = 36;
                    break;
                case 58 :
                case 59 :
                case 60 :
                    sum = 37;
                    break;
                case 61 :
                case 62 :
                case 63 :
                    sum = 38;
                    break;
                case 91 :
                case 92 :
                    sum = 40;
                    break;
                case 93 :
                case 95 :
                    sum = 41;
                    break;
                case 96 :
                case 123 :
                    sum = 42;
                    break;
                case 124 :
                    sum = 64;
                    break;
                case 125 :
                    sum = 94;
                    break;
            }

//            if (sum == 34) {
////                sum = 33;
////            } else if (sum == 39) {
////                sum = 34;
////            } else if (sum >= 43 && sum <= 47) {
////                sum = sum / 10 + 36;
////            } else if (sum >= 58 && sum <= 63) {
////                sum = sum / 10 + 36;
////            } else if (sum == 91 || sum == 92 || sum == 93 || sum == 95 || sum == 96) {
////                sum = 64;
////            } else if (sum >= 123) {
////                sum = 94;
////            }

            result.append((char) sum);
        }
        return result.toString();
    }

    public static String securePasswordOnlyLetters(String hashPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 8; i < hashPassword.length(); i += 8, j += 8) {
            String letters = hashPassword.substring(i, j);

            int sum = letters.chars().sum();
            while (sum > 125) {
                sum -= 90;
            }

            if (sum >= 33 && sum <= 57) {
                sum += 32;
            } else if (sum >= 58 && sum <= 64) {
                sum += 21;
            } else if (sum >= 91 && sum <= 96) {
                sum += 8;
            } else if (sum >= 123) {
                sum -= 10;
            }

            result.append((char) sum);
        }
        return result.toString();
    }

    public static String securePasswordFullEscapeChar(String hashPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 8; i < hashPassword.length(); i += 8, j += 8) {
            String letters = hashPassword.substring(i, j);

            int sum = letters.chars().sum();
            while (sum > 125) {
                sum -= 90;
            }

            if (sum >= 33 && sum <= 47) {
                sum += 32;
            } else if (sum >= 58 && sum <= 64) {
                sum += 21;
            } else if (sum >= 91 && sum <= 96) {
                sum += 8;
            } else if (sum >= 123) {
                sum -= 10;
            }

            result.append((char) sum);
        }
        return result.toString();
    }

    public static String securePassword(String hashPassword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 8; i < hashPassword.length(); i += 8, j += 8) {
            String letters = hashPassword.substring(i, j);

            int sum = letters.chars().sum();
            while (sum > 125) {
                sum -= 90;
            }

            result.append((char) sum);
        }
        return result.toString();
    }

    public static String hashPassword2(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA3-512");
            byte[] message = messageDigest.digest(password.getBytes());
            BigInteger no = new BigInteger(1, message);

            String hashtext = no.toString(16);

            while (hashtext.length() < 128) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String hashPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA3-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytesToHex(messageDigest.digest(password.getBytes()));
    }

    private static String bytesToHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
