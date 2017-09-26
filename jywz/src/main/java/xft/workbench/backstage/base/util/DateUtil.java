package xft.workbench.backstage.base.util;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 计算两个日期之间相差的天数
     *
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String bdate, String edate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(bdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(edate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 计算两个日期之间相差的月数不足保留到小数点二位。
     *
     * @return 相差天数
     * @throws ParseException
     */
    public static BigDecimal monthsBetween(String date1, String date2) throws ParseException {
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result1 = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        result2 = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        result3 = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
        int days_of_month = c2.getActualMaximum(Calendar.DAY_OF_MONTH);

        return new BigDecimal(result1 * 12 + result2)
                .add(new BigDecimal(result3).divide(new BigDecimal(days_of_month), 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 计算一个日期增加month个月后的日期
     *
     * @param date  时间 yyyyMMdd
     * @param month 月数
     * @return 增加一个月后的日期
     * @throws ParseException
     */
    public static String dateAddMoney(String date, int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        cal.add(Calendar.MONTH, month);
        return sdf.format(cal.getTime());
    }

    /**
     * 计算两个日期之间相差的月份,不足一个月按一个月计算
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result1 = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        result2 = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        result3 = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
        if (result3 > 0) {
            return result1 * 12 + result2 + 1;
        } else {
            return result1 * 12 + result2;
        }
    }

    /**
     * 查询日期的当月的最后一天
     *
     * @param date
     * @return
     */
    public static String getActualMaximum(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return date.substring(0, 6) + maximum;

    }

    /**
     * 得到系统日期
     *
     * @return yyyyMMhh
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 测试第一个日期是否在第二个日期之前。
     *
     * @param first  第一个日期，需满足yyyyMMdd格式
     * @param second 第二个日期，需满足yyyyMMdd格式
     * @return 第一个日期在第二个日期前面
     * @throws ParseException 如果参数日期不满足yyyyMMdd格式
     */
    public static boolean before(String first, String second) throws ParseException {
        Date d1 = FORMAT.parse(first);
        Date d2 = FORMAT.parse(second);
        return d1.before(d2);
    }

    /**
     * 测试第一个日期是否在第二个日期之前。
     *
     * @param first  第一个日期，需满足yyyyMMdd格式
     * @param second 第二个日期，需满足yyyyMMdd格式
     * @return 第一个日期在第二个日期前面
     * @throws ParseException 如果参数日期不满足yyyyMMdd格式
     */
    public static boolean before(Date first, String second) throws ParseException {
        Date d2 = FORMAT.parse(second);
        return first.before(d2);
    }


    /**
     * 测试第一个日期是否在第二个日期之前。
     *
     * @param first  第一个日期，需满足yyyyMMdd格式
     * @param second 第二个日期，需满足yyyyMMdd格式
     * @return 第一个日期在第二个日期前面
     * @throws ParseException 如果参数日期不满足yyyyMMdd格式
     */
    public static boolean before(String first, Date second) throws ParseException {
        Date d1 = FORMAT.parse(first);
        return d1.before(second);
    }

    /**
     * 获取两个日期中更早的一个
     *
     * @param first  第一个日期，需满足yyyyMMdd格式
     * @param second 第二个日期，需满足yyyyMMdd格式
     * @return 更早的时间
     * @throws ParseException 如果参数日期不满足yyyyMMdd格式
     */
    public static String earlier(String first, String second) throws ParseException {
        if (before(first, second)) {
            return first;
        } else {
            return second;
        }
    }

    /**
     * 获取两个日期中更晚的一个
     *
     * @param first  第一个日期，需满足yyyyMMdd格式
     * @param second 第二个日期，需满足yyyyMMdd格式
     * @return 更晚的时间
     * @throws ParseException 如果参数日期不满足yyyyMMdd格式
     */
    public static String latter(String first, String second) throws ParseException {

        if (before(first, second)) {
            return second;
        } else {
            return first;
        }
    }



}

