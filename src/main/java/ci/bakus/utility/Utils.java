package ci.bakus.utility;

/*
 * Created on 2023-08-05 ( Time 13:32:46 )
 * Copyright 2023 Younous Bakayoko. All Rights Reserved.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Utilities
 *
 * @author Smile Backend generator
 */
public class Utils {

    static final String alphaNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String hexaAlphabet = "0123456789abcdef";
    static SecureRandom rnd = new SecureRandom();

    private static SimpleDateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat _dateFormatMois = new SimpleDateFormat("MM/yyyy");
    private static SimpleDateFormat _dateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static Logger slf4jLogger = LoggerFactory.getLogger(Utils.class);

    private static List<String> listeBase = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9");

    private static List<String> listeIntegerBase = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final String[] IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    public static List<String> URI_AS_IGNORE = Arrays.asList("user/loginWithCode", "user/forgotPassword",
            "user/forgotPasswordValidation", "user/resetPassword");
    public static List<String> PROFILES_TO_IGNORE = Arrays.asList("local", "unencript_prod", "unencript_staging");

    public static Date getCurrentDate() {
        return new Date();
    }

    public static boolean IsImage(String image) {
        String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(png|PNG|jpg|JPG|jpeg|JPEG|bmp))$)";
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    public static boolean isDateToOperation(String date) {
        try {
            String simpleDateFormat = "dd/MM/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(simpleDateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isDateRdv(String date) {
        try {
            String simpleDateFormat = "dd/MM/yyyy HH:mm";
            DateFormat df = new SimpleDateFormat(simpleDateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public static String codeUnderNdigits(int nombre, final int nbdigits) {
        // final int nbdigits = 4;
        NumberFormat nbf = NumberFormat.getNumberInstance();
        // Oblige à compléter les nombre de moins de 4 digits
        nbf.setMinimumIntegerDigits(nbdigits);
        // pas de séparateur par groupe de 3 digits
        nbf.setGroupingUsed(false);
        // redémarre la séquence à 0 si plus de 4 digits
        nbf.setMaximumIntegerDigits(nbdigits);
        String sformatee = nbf.format(nombre);
        return sformatee;
    }

    public static boolean areEquals(Object obj1, Object obj2) {
        return (Objects.equals(obj1, obj2));
    }

    public static <T extends Comparable<T>, Object> boolean areEquals(T obj1, T obj2) {
        return (obj1 == null ? obj2 == null : obj1.equals(obj2));
    }

    public static boolean areNotEquals(Object obj1, Object obj2) {
        return !areEquals(obj1, obj2);
    }

    public static <T extends Comparable<T>, Object> boolean areNotEquals(T obj1, T obj2) {
        return !(areEquals(obj1, obj2));
    }

    public static BigDecimal calculRO(long value1, long value2) {
        double variation = 0;
        BigDecimal result = new BigDecimal(0);
        if (value1 != 0L && value2 != 0L) {
            variation = ((double) (value1 * 100)) / value2;
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static Calendar anneePrecedente(Calendar calendar) throws ParseException {
        Calendar result = calendar;
        result.add(Calendar.YEAR, -1);
        return result;
    }

    public static Calendar moisPrecedent(Calendar calendar) throws ParseException {
        Calendar result = calendar;
        result.add(Calendar.MONTH, -1);
        return result;
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static BigDecimal calculEvoluation(Double value1, Double value2) {
        double variation = 0;

        BigDecimal bigDecimal1 = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = ((value1 / value2) - 1) * 100;
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculEvoluation(Long value1, Long value2) {
        double variation = 0;

        BigDecimal bigDecimal1 = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = (((value1 / (double) value2)) - 1) * 100;
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculDivision(Long value1, Long value2) {
        double variation = 0;

        BigDecimal bigDecimal1 = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = (value1 / value2);
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculDivision(double value1, double value2) {
        double variation = 0;

        BigDecimal bigDecimal1 = new BigDecimal(value1);
        BigDecimal roundedWithScale1 = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal roundedWithScale2 = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal result = new BigDecimal(0);
        if (roundedWithScale1.doubleValue() != 0.00 && roundedWithScale2.doubleValue() != 0.00) {
            variation = (value1 / value2);
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (roundedWithScale2.doubleValue() == 0.00) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static String getQuatreCaractAnnee(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getQuatreCaractAnneeNew(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateAnneecFormat(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormafinale = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormat.parse(date));
        result = dateFormafinale.format(calendar.getTime());

        return result;

    }

    public static String combinaisonString() {
        String lettres = "";
        try {
            Random random;
            for (int i = 0; i < 10; i++) {
                random = new Random();
                int rn = random.nextInt(35 - 0 + 1) + 0;
                lettres += listeBase.get(rn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lettres;
    }

    public static String combinaisonStringLight(Integer nbre) {
        String lettres = "";
        try {
            Random random;
            for (int i = 0; i < nbre; i++) {
                random = new Random();
                int rn = random.nextInt(9 - 0 + 1) + 0;
                lettres += listeIntegerBase.get(rn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lettres;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int duration(Date startDate, Date endDate) {
        long duration = ChronoUnit.DAYS.between(asLocalDate(startDate), asLocalDate(endDate));
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    public static int duration(LocalDate startLocalDate, LocalDate endLocalDate) {
        long duration = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        return Integer.parseInt(String.valueOf(duration + 1));
    }

    /**
     * Check if a String given is an Integer.
     *
     * @param s
     * @return isValidInteger
     */
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }

        return isValidInteger;
    }

    public static String generateCode() {
        String formatted = null;
        SecureRandom secureRandom = new SecureRandom();
        int num = secureRandom.nextInt(100000000);
        formatted = String.format("%05d", num);
        return formatted;
    }

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean isFalse(Boolean b) {
        return !isTrue(b);
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Check if a Integer given is an String.
     *
     * @param i
     * @return isValidString
     */
    public static boolean isString(Integer i) {
        boolean isValidString = true;
        try {
            Integer.parseInt(i + "");

            // i is a valid integer

            isValidString = false;
        } catch (NumberFormatException ex) {
            // i is not an integer
        }

        return isValidString;
    }

    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static String encrypt(String str) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));
        return convertByteArrayToHexString(hashedBytes);
    }

    public static String sha256(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean isDateValid(String date) {
        try {
            String simpleDateFormat = "dd/MM/yyyy";

            if (date.contains("-"))
                simpleDateFormat = "dd-MM-yyyy";
            else if (date.contains("/"))
                simpleDateFormat = "dd/MM/yyyy";
            else
                return false;

            DateFormat df = new SimpleDateFormat(simpleDateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Integer getAge(Date dateNaissance) throws ParseException, Exception {
        Integer annee = 0;

        if (dateNaissance == null) {
            annee = 0;
        }
        Calendar birth = new GregorianCalendar();
        birth.setTime(dateNaissance);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        annee = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
        return annee;
    }

    public static String normalizeFileName(String fileName) {
        String fileNormalize = null;
        fileNormalize = fileName.trim().replaceAll("\\s+", "_");
        fileNormalize = fileNormalize.replace("'", "");
        fileNormalize = Normalizer.normalize(fileNormalize, Normalizer.Form.NFD);
        fileNormalize = fileNormalize.replaceAll("[^\\p{ASCII}]", "");

        return fileNormalize;
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static SimpleDateFormat findDateFormat(String date) {
        SimpleDateFormat simpleDateFormat = null;
        String regex_dd_MM_yyyy = "\\A0?(?:3[01]|[12][0-9]|[1-9])[/.-]0?(?:1[0-2]|[1-9])[/.-][0-9]{4}\\z";

        if (date.matches(regex_dd_MM_yyyy))
            if (date.contains("-"))
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            else if (date.contains("/"))
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDateFormat;
    }

    /**
     * @return Permet de retourner la date courante du système
     */
    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * @param l liste de vérification de doublons
     * @return retourne le nombre de doublon trouvé
     */
    public static int getDupCount(List<String> l) {
        int cnt = 0;
        HashSet<String> h = new HashSet<>(l);

        for (String token : h) {
            if (Collections.frequency(l, token) > 1)
                cnt++;
        }

        return cnt;
    }

    public static boolean saveImage(String base64String, String nomCompletImage, String extension) throws Exception {

        BufferedImage image = decodeToImage(base64String);

        if (image == null) {

            return false;

        }

        File f = new File(nomCompletImage);

        // write the image

        ImageIO.write(image, extension, f);

        return true;

    }

    public static boolean saveVideo(String base64String, String nomCompletVideo) throws Exception {

        try {

            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            File file2 = new File(nomCompletVideo);
            FileOutputStream os = new FileOutputStream(file2, true);
            os.write(decodedBytes);
            os.close();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;

    }

    public static BufferedImage decodeToImage(String imageString) throws Exception {

        BufferedImage image = null;

        byte[] imageByte;

        imageByte = Base64.getDecoder().decode(imageString);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {

            image = ImageIO.read(bis);

        }

        return image;

    }

    public static String encodeToString(BufferedImage image, String type) {

        String imageString = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            ImageIO.write(image, type, bos);

            byte[] imageBytes = bos.toByteArray();

            imageString = new String(Base64.getEncoder().encode(imageBytes));

            bos.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return imageString;

    }

    public static String convertFileToBase64(String pathFichier) {
        File originalFile = new File(pathFichier);
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encodeToString((bytes)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedBase64;
    }

    public static String getImageExtension(String str) {
        String extension = "";
        int i = str.lastIndexOf('.');
        if (i >= 0) {
            extension = str.substring(i + 1);
            return extension;
        }
        return null;
    }

    public static boolean fileIsImage(String image) {

        String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)";
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(image);

        return matcher.matches();

    }

    public static boolean fileIsVideo(String video) {

        String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(mp4|avi|camv|dvx|mpeg|mpg|wmv|3gp|mkv))$)";
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(video);

        return matcher.matches();

    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.isEmpty()) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /*
     * public static boolean notEmpty(List<String> lst) { return lst != null &&
     * !lst.isEmpty() && lst.stream().noneMatch(s -> s.equals("\n")) &&
     * lst.stream().noneMatch(s -> s.equals(null)); }
     */

    public static boolean notEmpty(List<String> lst) {
        return lst != null && !lst.isEmpty() && lst.stream().noneMatch(s -> s.equals("\n"))
                && lst.stream().noneMatch(s -> isBlank(s));
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return (list != null && !list.isEmpty());
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return (array != null && array.length > 0);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return (map == null || map.isEmpty());
    }

    static public String GetCode(String Value, Map<String, String> Table) {

        for (Entry<String, String> entry : Table.entrySet()) {
            if (entry.getValue().equals(Value)) {
                return entry.getKey();
            }
        }
        return Value;
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate, SimpleDateFormat dateFormat)
            throws ParseException {
        List<Date> dates = new ArrayList<Date>();

        startdate = dateFormat.parse(dateFormat.format(startdate));
        enddate = dateFormat.parse(dateFormat.format(enddate));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(getCalendarField(dateFormat.toPattern()), 1);
        }
        return dates;
    }

    public static List<String> getIndexBetweenDates(Date startdate, Date enddate, String root) {
        List<Date> dates = getDaysBetweenDates(startdate, enddate);
        List<String> str_dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter_new = new SimpleDateFormat("yyyyMM");

        dates.forEach(d -> {
            if (!root.contains("omer_vente_kit_") && !root.contains("omer_ro_kits_vendus_")
                    && !root.contains("airtime_commission_") && !root.contains("objectifs_")) {
                str_dates.add(root + formatter.format(d));
                // slf4jLogger.info("index -> " + root + formatter.format(d));
            } else {
                str_dates.add(root + formatter_new.format(d));
                // slf4jLogger.info("index -> " + root + formatter_new.format(d));
            }
        });

        return str_dates;
    }

    public static List<String> getIndexBetweenDates(Date startdate, Date enddate, String root,
                                                    SimpleDateFormat formatter) throws ParseException {
        List<Date> dates = getDaysBetweenDates(startdate, enddate, formatter);
        List<String> str_dates = new ArrayList<String>();
        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });
        return str_dates;
    }

    public static List<String> getIndexBetweenDatesV2(Date startdate, Date enddate, String root) {
        List<Date> dates = getDaysBetweenDates(startdate, enddate);
        List<String> str_dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });

        return str_dates;
    }

    public static List<String> getIndexBetweenDatesMois(Date startdate, Date enddate, String root) {
        List<Date> dates = getDaysBetweenDates(startdate, enddate);
        List<String> str_dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

        dates.forEach(d -> {
            str_dates.add(root + formatter.format(d));
        });

        return str_dates;
    }

    public static String[] getIndexesArray(String start, String end, SimpleDateFormat dateFormat, String root)
            throws ParseException {

        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes = getIndexBetweenDates(d1, d2, root);
        String[] indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static String[] getIndexesArrayV2(String start, String end, SimpleDateFormat dateFormat, String root)
            throws ParseException {
        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes = getIndexBetweenDatesV2(d1, d2, root);
        String[] indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static String[] getIndexesArrayMois(String start, String end, SimpleDateFormat dateFormat, String root)
            throws ParseException {
        if (dateFormat == null) {
            dateFormat = _dateFormat;
        }

        Date d1 = dateFormat.parse(start);
        Date d2 = dateFormat.parse(end);

        List<String> indexes = getIndexBetweenDatesMois(d1, d2, root);
        String[] indexesArr = new String[indexes.size()];
        return indexes.toArray(indexesArr);
    }

    public static int compareToDate(String first_date, String second_date, SimpleDateFormat dateFormat)
            throws ParseException {
        Date first = dateFormat.parse(first_date);
        Date second = dateFormat.parse(second_date);
        return first.compareTo(second);
    }

    public static String getDateJour(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getMoisFormatFinale(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));
        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;

    }

    public static String getDernierJourMoisFormatFinale(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        // SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMoisNew.parse(date));

        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        result = String.valueOf(lastDay);

        return result;

    }

    public static String getAnneeJour(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static BigDecimal calculTOT(long value1, long value2) {
        double variation = 0;
        BigDecimal result = new BigDecimal(0);
        if (value1 != 0L && value2 != 0L) {
            // variation = ((double) (value1 * 100)) / value2;
            double v1 = 0.0;
            double v2 = 0.0;
            v1 = value1;
            v2 = value2;
            variation = ((v1 / v2) - 1) * 100;
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static String getDateMoisPrec(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormat.parse(date));

        calendar.add(Calendar.MONTH, -1);
        result = dateFormat.format(calendar.getTime()).toLowerCase();

        return result;

    }

    public static String getDateSemaineNew(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("ww-yyyy");
        // SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateSemaine(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyy-ww");
        // SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("yyyy-ww");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("ww-yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();

        return result;
    }

    public static String getDateMois(String date) throws ParseException {
        String result = "";

        // TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        SimpleDateFormat dateFormatMois = new SimpleDateFormat("yyyy-MM");
        // SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MMMMMM yyyy");
        SimpleDateFormat dateFormatMoisNew = new SimpleDateFormat("MM/yyyy");
        calendar.setTime(dateFormatMois.parse(date));

        result = dateFormatMoisNew.format(calendar.getTime()).toLowerCase();
        slf4jLogger.info("mois  envoyé : " + date);
        slf4jLogger.info("resultat  : " + result);

        return result;
    }

    public static String retrieVeMonth(Integer monthIndice) {
        String value = "";
        switch (monthIndice) {
            case 1:
                value = "Janvier";
                break;
            case 2:
                value = "Février";
                break;
            case 3:
                value = "Mars";
                break;
            case 4:
                value = "Avril";
                break;
            case 5:
                value = "Mai";
                break;
            case 6:
                value = "Juin";
                break;
            case 7:
                value = "Juillet";
                break;
            case 8:
                value = "Août";
                break;
            case 9:
                value = "Septembre";
                break;
            case 10:
                value = "Octobre";
                break;
            case 11:
                value = "Novembre";
                break;
            case 12:
                value = "Décembre";
                break;
            default:
                break;
        }
        return value;
    }

    public static String getDateAnneePrec(String date) throws ParseException {
        String result = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(dateFormat.parse(date));

        calendar.add(Calendar.YEAR, -1);

        result = dateFormat.format(calendar.getTime());

        return result;

    }

    public static int getCalendarField(String datePattern) {
        if (isBlank(datePattern)) {
            return Calendar.DATE;
        }

        if (Arrays.asList("yyyyMM", "yyyy.MM", "yyyy-MM", "MMyyyy", "MM.yyyy", "MM-yyyy").contains(datePattern)) {
            return Calendar.MONTH;
        }
        if (Arrays.asList("yyyy").contains(datePattern)) {
            return Calendar.YEAR;
        }

        return Calendar.DATE;
    }

    public static boolean fileIsTexteDocument(String textDocument) {

        String TEXT_DOCUMENT_PATTERN = "([^\\s]+(\\.(?i)(doc|docx|txt|odt|ods|pdf|xls|xlsx|csv))$)";
        Pattern pattern = Pattern.compile(TEXT_DOCUMENT_PATTERN);
        Matcher matcher = pattern.matcher(textDocument);
        return matcher.matches();
    }

    public static Boolean verifierEmail(String email) {
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(alphaNum.charAt(rnd.nextInt(alphaNum.length())));
        return sb.toString();
    }

    public static String randomHexaString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(hexaAlphabet.charAt(rnd.nextInt(hexaAlphabet.length())));
        return sb.toString();
    }

    public static String getFilePath(String pathFichier) {
        // slf4jLogger.info("--pathFichier--" + pathFichier);
        File file = null;
        try {
            file = new ClassPathResource(pathFichier).getFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static boolean saveFile(String base64String, String nomCompletVideo) throws Exception {

        try {

            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            File file2 = new File(nomCompletVideo);
            FileOutputStream os = new FileOutputStream(file2, true);
            os.write(decodedBytes);
            os.close();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;

    }

    public static BigDecimal calculTaux(Double value1, Double value2, int newScale, int roundingMode) {
        double variation = 0;
        BigDecimal result = new BigDecimal(0);
        if (value1 != 0d && value2 != 0d) {
            variation = (value1 * 100) / value2;
            BigDecimal bigDecimal = new BigDecimal(variation);
            BigDecimal roundedWithScale = bigDecimal.setScale(newScale, roundingMode);
            result = roundedWithScale;
        }
        if (value2 == 0L) {
            result = new BigDecimal(0);
        }

        return result;
    }

    public static BigDecimal calculTaux(Double value1, Double value2, int newScale) {
        return calculTaux(value1, value2, newScale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculTaux(Double value1, Double value2) {
        return calculTaux(value1, value2, 2);
    }

    public static Double caluclerVariation(Double valueAtH, Double valueAtHPlusX) {
        if (valueAtH.equals(0d)) {
            return Double.NaN;
        }
        return 100.0 * ((valueAtHPlusX / valueAtH) - 1);
    }

    public static boolean isValidateIvorianPhoneNumber(String phoneNumber) {
        String regex = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?(\\s)?[0-9]{2}([ .-]?[0-9]{2}){3}$";
        return (phoneNumber != null && phoneNumber.matches(regex));
    }

    public static String ivorianPhoneNumberToStandardFormat(String phoneNumber) {
        String beginRegex = "^(\\(\\+225\\)|\\+225|\\(00225\\)|00225)?";
        String specialCharRegex = "[ .-]?";
        String simplePhoneNumber;

        if (phoneNumber == null)
            return null;

        simplePhoneNumber = phoneNumber.replaceAll(beginRegex, "");
        return simplePhoneNumber.replaceAll(specialCharRegex, "");
    }

//	public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam) {
//		return fieldParam != null && isNotBlank(fieldParam.getOperator()) && (fieldParam.getStart() != null || fieldParam.getEnd() != null || isNotEmpty(fieldParam.getDatas()) );
//	}

    public static String millierSeparator(Double data) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        String s = formatter.format(data);
        return s;
    }


    public static <T> List<T> paginner(List<T> allItems, Integer index, Integer size) {
        if (isEmpty(allItems)) {
            return null;
        }

        List<T> items = new ArrayList<T>();
        // si une pagination est pécisée, ne prendre que le nombre d'éléments demandés
        if (index != null && index >= 0 && size != null && size >= 0) {
            Integer fromIndex = index * size;
            if (fromIndex < allItems.size()) {
                Integer toIndex = fromIndex + size;
                if (toIndex > allItems.size()) {
                    toIndex = allItems.size();
                }
                items.addAll(allItems.subList(fromIndex, toIndex));
            }
        } else {
            items.addAll(allItems);
        }

        return items;
    }

    public static String convertSecondToTime(Long seconds) {
        Long heure = seconds / 3600L;
        Long minute = (seconds - heure * 3600L) / 60L;
        Long second = seconds % 60L;

        String HH = (heure < 10) ? "000" + heure
                : (heure < 100) ? "00" + heure : (heure < 1000) ? "0" + heure : heure + "";
        String mm = (minute < 10) ? "0" + minute : minute + "";
        String ss = (second < 10) ? "0" + second : second + "";

        return HH + ":" + mm + ":" + ss;
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByKey());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static String readJsonDefn(String url) throws Exception {
        // implement it the way you like
        StringBuffer bufferJSON = new StringBuffer();

        FileInputStream input = new FileInputStream(new File(url).getAbsolutePath());
        DataInputStream inputStream = new DataInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = br.readLine()) != null) {
            bufferJSON.append(line);
        }
        br.close();
        return bufferJSON.toString();
    }

    public static boolean isValidID(Integer id) {
        return id != null && id > 0;
    }

    public static File getNewFile(String path) {
        return new File(getAbsoluePath(path));
    }

    public static boolean existFile(String path) {
        File file = getNewFile(path);
        return file.exists();
    }

    public static String getAbsoluePath(String path) {
        String fullPath = path;

        if (Thread.currentThread() != null && Thread.currentThread().getContextClassLoader() != null) {
            Object object = Thread.currentThread().getContextClassLoader().getResource(path);
            if (object != null) {
                fullPath = Thread.currentThread().getContextClassLoader().getResource(path).getFile();
            }
        }
        return fullPath;
    }

    public static String getStringFromFile(File file) throws Exception {
        StringBuffer buffer = new StringBuffer();

        FileInputStream input = new FileInputStream(file.getAbsolutePath());
        DataInputStream inputStream = new DataInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        return buffer.toString();
    }

    public static String removeTrailingZeros(double d, boolean thousandSeparator) {
        String result = "";
        if (thousandSeparator) {
            result = String.format(Locale.FRANCE, "%,f", d);
        } else {
            result = String.valueOf(d);
        }
        return result.replaceAll("[0]*$", "").replaceAll(".$", "");
    }

    public static String currencyFormat(Double amount, String countryCode) {
        // CIV
        countryCode = (countryCode != null && !countryCode.isEmpty()) ? countryCode : "CIV";
        Map<String, String> languagesMap = new TreeMap<String, String>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale obj : locales) {
            if ((obj.getDisplayCountry() != null) && (!"".equals(obj.getDisplayCountry()))) {
                // System.out.println(obj.getCountry()+" "+ obj.getLanguage());
                languagesMap.put(obj.getCountry(), obj.getLanguage());
            }
        }
        Locale obj = null;
        if (languagesMap.get(countryCode) == null) {
            obj = new Locale("", countryCode);
        } else {
            // create a Locale with own country's languages
            obj = new Locale(languagesMap.get(countryCode), countryCode);

        }
        NumberFormat currency = NumberFormat.getCurrencyInstance(obj);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currency).getDecimalFormatSymbols();
        String currencySymbol = decimalFormatSymbols.getCurrencySymbol();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) currency).setDecimalFormatSymbols(decimalFormatSymbols);
        String display = (countryCode.toUpperCase().equals("CIV")) ? currency.format(amount).trim().replaceAll(",", " ")
                : currency.format(amount).trim();
        return display.split("\\.")[0];
        // return display;
    }
}
