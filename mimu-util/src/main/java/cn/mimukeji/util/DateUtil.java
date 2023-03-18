package cn.mimukeji.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    private Date date;

    public DateUtil() {
        date = new Date();
    }

    public DateUtil(Date date) {
        this.date = date;
    }

    public static String getDateByYear(int year) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        // 过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, year);
        Date y = c.getTime();
        return format.format(y);

    }

    /**
     * 取得过去几天的日期，不包括今天
     * 
     * @param computedays
     * @return
     */
    public Set<String> getComputeDates(int computedays, String format) {
        return getComputeDates(computedays, new Date(), false, format);
    }

    /**
     * 根据给定的起止日期和终止日期，取得范围内的日期的字符串集合yyyyMMdd格式
     * 
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static Set<String> getDateRangeSet(String start, String end) throws ParseException {
        return getDateRangeSet(start, end, "yyyyMMdd", "yyyyMMdd");
    }

    /**
     * 根据给定的起止日期和终止日期，取得范围内的日期的字符串集合yyyyMMdd格式
     * 
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static Set<String> getDateRangeSet(String start, String end, String sourceFormatStr, String destFormatStr)
            throws ParseException {

        SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceFormatStr);
        SimpleDateFormat destFormat = new SimpleDateFormat(destFormatStr);

        Set<String> dates = new TreeSet<String>();

        Calendar c = Calendar.getInstance();

        c.setTime(sourceFormat.parse(end));
        String endDay = destFormat.format(c.getTime());

        c.setTime(sourceFormat.parse(start));
        // String tmpDay = start;
        String tmpDay = destFormat.format(c.getTime());

        // dates.add(start);

        while (tmpDay.compareTo(endDay) <= 0) {

            dates.add(tmpDay);
            c.add(Calendar.DAY_OF_MONTH, 1);
            tmpDay = destFormat.format(c.getTime());

        }

        return dates;
    }

    /**
     * 取得过去几天的日期，是否包含lastDay
     * 
     * @param computedays
     * @param lastDay
     * @param include
     * @return
     */
    public Set<String> getComputeDates(int computedays, Date lastDay, boolean include, String format) {
        Set<String> dates = new TreeSet<String>();
        // 拿到日历对象
        Calendar c = Calendar.getInstance();
        // 将lastDay格式化，并同步到日历对象中
        String lastDayStr = new SimpleDateFormat("yyyy-MM-dd").format(lastDay);
        c.set(new Integer(lastDayStr.split("-")[0]), new Integer(lastDayStr.split("-")[1]) - 1,
                new Integer(lastDayStr.split("-")[2]), 0, 0, 0);

        // 如果不包含lastDay
        if (!include) {
            // 循环天数，
            for (int i = 0; i < computedays; i++) {
                // 将日历对象日期-1
                c.add(Calendar.DAY_OF_MONTH, -1);
                // 按传入参数格式化日期为字符串，存入日期集合中
                dates.add(toString(format, c.getTime()));
            }
        } else {
            // 如果包含lastDay，先将最近一天格式化成字符串存入日期集合中
            dates.add(new SimpleDateFormat(format).format(lastDay));
            // 循环 计算天数-1（已经存入了一天了）
            for (int i = 0; i < computedays - 1; i++) {
                // 将日历对象日期-1
                c.add(Calendar.DAY_OF_MONTH, -1);
                // 按传入参数格式化日期为字符串，存入日期集合中
                dates.add(toString(format, c.getTime()));
            }

        }

        return dates;
    }

    /**
     * 取得过去几天的日期，是否包含lastDay
     * 
     * @param computedays
     * @param lastDay
     * @param include
     * @return
     */
    public Set<String> getComputeDates(int computedays, LocalDate lastDay, boolean include, String format) {
        Set<String> dates = new TreeSet<String>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        for (int i = include ? 0 : 1; i < computedays + (include ? 0 : 1); i++) {
            dates.add(lastDay.minusDays(i).format(dtf));
        }
        return dates;
    }

    public static DateUtil now() {
        return new DateUtil();
    }

    public String toString() {
        return toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按传入格式，将日期转换为字符串
     * 
     * @param format
     * @return
     */
    public String toString(String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public String toString(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 取得后一天-前一天的毫秒数
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static String timeSpan(Date time1, Date time2) {
        long t1 = time1.getTime();
        long t2 = time2.getTime();

        return DateUtil.toTimeString(t2 - t1);
    }

    private static String toTimeString(long t) {
        String s = "";

        long md = 1000 * 60 * 60 * 24;
        long mh = 1000 * 60 * 60;
        long mm = 1000 * 60;
        long ms = 1000;

        long d = t / md;
        long c = t % md;

        if (d > 0) {
            s += d + " days ";
        }
        if (c > 0) {
            d = c / mh;
            c = c % mh;

            if (d < 10) {
                s += "0";
            }
            s += d;
            s += ":";
            if (c > 0) {
                d = c / mm;
                c = c % mm;

                if (d < 10) {
                    s += "0";
                }
                s += d;
                s += ":";
                if (c > 0) {
                    d = c / ms;

                    if (d < 10) {
                        s += "0";
                    }
                    s += d;
                } else {
                    s += "00";
                }
            } else {
                s += "00:00";
            }
        }

        return s;
    }

    /**
     * 取得此日期的星期数
     * 
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 这个日期是否为当月第一天
     * 
     * @param date
     * @return
     */
    public static boolean ifTheFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (1 == cal.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取得过去n天的第一天和最后一天，以日期数组返回
     * 
     * @param computedays
     * @param lastDay
     * @param include
     * @return
     */
    public static Date[] getStartDateAndEndDate(int computedays, Date lastDay, boolean include) {
        Date[] dates = new Date[2];
        // 拿到日历对象
        Calendar c = Calendar.getInstance();
        // 将lastDay格式化，并同步到日历对象中
        String lastDayStr = new SimpleDateFormat("yyyy-MM-dd").format(lastDay);
        c.set(new Integer(lastDayStr.split("-")[0]), new Integer(lastDayStr.split("-")[1]) - 1,
                new Integer(lastDayStr.split("-")[2]), 0, 0, 0);

        // 如果不包含lastDay
        if (!include) {

            // 将日历对象日期-1
            c.add(Calendar.DAY_OF_MONTH, -1);
            // 按传入参数格式化日期为字符串，存入日期集合中
            dates[1] = c.getTime();
            // 循环天数，
            for (int i = 0; i < computedays - 1; i++) {
                // 将日历对象日期-1
                c.add(Calendar.DAY_OF_MONTH, -1);
                // 按传入参数格式化日期为字符串，存入日期集合中
                dates[0] = c.getTime();
            }
        } else {
            // 如果包含lastDay，先将最近一天格式化成字符串存入日期集合中
            dates[1] = lastDay;
            // 循环 计算天数-1（已经存入了一天了）
            for (int i = 0; i < computedays - 1; i++) {
                // 将日历对象日期-1
                c.add(Calendar.DAY_OF_MONTH, -1);
                // 按传入参数格式化日期为字符串，存入日期集合中
                dates[0] = c.getTime();
            }

        }

        return dates;
    }

    /**
     * 取得上个月有多少天
     * 
     * @param date
     * @return
     */
    public static int getDaysOfLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 月份减一
        cal.add(Calendar.MONTH, -1);
        // 最后一天的日期数即为此月的天数
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得两个日期之间的所有日期
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static Set<Date> getDates(Date startDay, Date endDay) {
        Set<Date> dates = new TreeSet<Date>();
        // 拿到日历对象
        Calendar tmpC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();
        tmpC.setTime(startDay);
        endC.setTime(endDay);
        {
            dates.add(endDay);
            while (tmpC.before(endC)) {
                dates.add(tmpC.getTime());
                // 将日历对象日期+1
                tmpC.add(Calendar.DAY_OF_MONTH, +1);
            }
        }
        return dates;
    }

    /**
     * 取得两个日期之间的天数
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static int getDays(Date startDay, Date endDay) {
        Set<String> dates = new HashSet<String>();
        // 拿到日历对象
        Calendar tmpC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        tmpC.setTime(startDay);
        endC.setTime(endDay);
        {
            dates.add(sdf.format(endDay));
            while (tmpC.before(endC)) {
                dates.add(sdf.format(tmpC.getTime()));
                // 将日历对象日期+1
                tmpC.add(Calendar.DAY_OF_MONTH, +1);
            }
        }
        return dates.size();
    }

    /**
     * 按指定星期 分割给定的时间范围
     * 
     * @param startDay
     * @param endDay
     * @param week
     * @return
     */
    public static Set<Date[]> splitDatesByWeek(Date startDay, Date endDay, String week) {
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        for (String weekDay : weekDays) {
            if (weekDay.equalsIgnoreCase(week)) {
                return splitDates(startDay, endDay, week);
            }
        }
        return null;
    }

    /**
     * 按月分割给定的时间范围
     * 
     * @param startDay
     * @param endDay
     * @return
     */
    public static Set<Date[]> splitDatesByMonth(Date startDay, Date endDay) {
        return splitDates(startDay, endDay, "month");
    }

    /**
     * 按特定类别（月，周等）分割给定的时间范围
     * 
     * @param startDay
     * @param endDay
     * @param period
     * @return
     */
    public static Set<Date[]> splitDates(Date startDay, Date endDay, String period) {
        Set<Date[]> dateArrays = new HashSet<Date[]>();

        // 拿到日历对象
        Calendar tmpC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();
        tmpC.setTime(startDay);
        endC.setTime(endDay);

        Date[] dateArray = null;
        // 新建日期数组，用于存放起止日期
        dateArray = new Date[2];
        dateArray[0] = startDay;
        while (tmpC.compareTo(endC) <= 0) {
            // 如果是特定日期（月末，指定的星期数等），确定结束日期。从新建立日期数组，开始求下一个时间段
            if (ifSpecificDate(tmpC, period)) {
                if (dateArray[1] != null) {
                    dateArrays.add(dateArray);
                    dateArray = new Date[2];
                    dateArray[0] = tmpC.getTime();
                }
            } else {
                dateArray[1] = tmpC.getTime();
            }
            // 将日历对象日期+1
            tmpC.add(Calendar.DAY_OF_MONTH, +1);
        }
        // 填最后一个数组
        dateArray[1] = endDay;
        dateArrays.add(dateArray);
        return dateArrays;
    }

    /**
     * 查看此日期是否为特定日期
     */
    public static boolean ifSpecificDate(Calendar c, String period) {

        // 如果日期为本月最后一天，返回true
        if ("month".equalsIgnoreCase(period)) {
            if (c.getActualMinimum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        } else if ("SUNDAY".equalsIgnoreCase(period)) {
            if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        } else if ("MONDAY".equalsIgnoreCase(period)) {
            if ((Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK))) {
                return true;
            }
        } else if ("TUESDAY".equalsIgnoreCase(period)) {
            if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        } else if ("WEDNESDAY".equalsIgnoreCase(period)) {
            if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        } else if ("THURSDAY".equalsIgnoreCase(period)) {
            if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        } else if ("FRIDAY".equalsIgnoreCase(period)) {
            if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        } else if ("SATURDAY".equalsIgnoreCase(period)) {
            if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回指定时间点所在的时间范围
     * 
     * @param time
     * @return
     * @throws Exception
     */
    public static String getTimeRange(String time) throws Exception {
        // 00：00-2:00/2:00-4:00/4:00-6:00/6:00-8:00/8:00-10:00/10:00-12:00/12:00-14:00/14:00-16:00/16:00-18:00/18:00-20:00/20:00-22:00/22:00-00:00
        // 00:31:50
        String timeRang;
        if (time.compareTo("02:00:00") < 0) {
            timeRang = "00:00:00";
        } else if (time.compareTo("04:00:00") < 0) {
            timeRang = "02:00:00";
        } else if (time.compareTo("06:00:00") < 0) {
            timeRang = "04:00:00";
        } else if (time.compareTo("08:00:00") < 0) {
            timeRang = "06:00:00";
        } else if (time.compareTo("10:00:00") < 0) {
            timeRang = "08:00:00";
        } else if (time.compareTo("12:00:00") < 0) {
            timeRang = "10:00:00";
        } else if (time.compareTo("14:00:00") < 0) {
            timeRang = "12:00:00";
        } else if (time.compareTo("16:00:00") < 0) {
            timeRang = "14:00:00";
        } else if (time.compareTo("18:00:00") < 0) {
            timeRang = "16:00:00";
        } else if (time.compareTo("20:00:00") < 0) {
            timeRang = "18:00:00";
        } else if (time.compareTo("22:00:00") < 0) {
            timeRang = "20:00:00";
        } else if (time.compareTo("24:00:00") < 0) {
            timeRang = "22:00:00";
        } else {
            throw new Exception("input time invalid!");
        }
        return timeRang;
    }

    /**
     * 返回指定时间点的时间类型（工作，家庭）
     * 
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getTimeType(String dateTime) throws ParseException {
        // 1、 根据APP看客户坐标点位，在周一到周五早上10点到下午4点的，认定为工作地址；在晚上8点到次日凌晨6点的视为居住地址
        // 20点后，6点前，算居住
        String type = null, time;
        time = dateTime.split(" ")[1];
        if ("20:00:00".compareTo(time) < 0 || "06:00:00".compareTo(time) > 0) {
            type = "home";
        } else {
            String dayOfWeek = DateUtil.getDayOfWeek(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            String weekDays = "MondayTuesdayWednesdayThursdayFriday";
            if (weekDays.contains(dayOfWeek)) {
                if ("10:00:00".compareTo(time) < 0 && "16:00:00".compareTo(time) > 0) {
                    type = "work";
                }
            }
        }
        return type;
    }

    /**
     * 获取昨天的这个时刻
     * 
     * @return
     */
    public static Date getYestoday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }

    /**
     * 获取SimpleDateFormat
     * 
     * @param parttern
     *            日期格式
     * @return SimpleDateFormat对象 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * 
     * @param date
     *            日期
     * @param parttern
     *            日期格式
     * @return 日期字符串
     */

    public static String dateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * 
     * @param dateString
     *            日期
     * @param parttern
     *            日期格式
     * @return 日期字符串
     */
    public static Date stringToDate(String dateString, String parttern) {
        Date date = null;
        try {
            date = getDateFormat(parttern).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateString
     *            日期
     * @param parttern
     *            日期格式
     * @return 日期字符串
     */
    public static Date stringToDate(String dateString, String parttern, Locale locale) {
        Date date = null;
        try {
            date = new SimpleDateFormat(parttern, locale).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * n天后的日期
     * 
     * @param baseDate 给定日期
     * @param n 天后的日期，负值则为-n天之前的日期
     * @return
     */
    public static Date getComputeDate(Date baseDate ,int n) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        return calendar.getTime();
    }

    /**
     * 把日期字符串转换成 Date
     * @param dateParam 可能是各种类型的日期字符串
     * @return
     */
    public static Date stringToDate(String dateParam) throws ParseException {
        Date date = null;
        String dateString = dateParam;

        String dateStringFront = "";
        String dateStringBack = "";

        String dateTimezone = "";
        String pattern = "yyyyMMddHHmmssZ";
        Locale locale = Locale.CHINA;
        String defaultTimezone = "+0800";


        if(!StringUtils.isEmpty(dateString) && dateString.length() >= 6){

            //民國111年07月20日
            //中华民國111年07月20日
            //111年07月20日
            //111/07/20
            //111-07-20
            dateString = dateString.replaceAll("民國|中华民國|日","")
                    .replaceAll("年|月","-");

            if(dateString.startsWith("[")){
                // [2022-06-07]
                dateString = dateString.replaceAll("\\[|\\]","");
            }

            // T
            dateString = dateString.replace("T"," ");

            // 最前边、最后边的空格
            dateString = dateString.trim();
            dateString = dateString.replaceAll("[　]","");

            if(dateString.matches(".*[a-zA-Z].*")){
                // July 05, 2022
                // June 27, 2022
                pattern = "MMM dd, yyyy";
                locale = Locale.US;
            } else{

                // 去掉冒号
                dateString = dateString.replaceAll("[:]","");

                // 处理时区
                String patternDateTimezone = "((\\+|\\-)\\d{4}$)";
                dateTimezone = getMatcher(patternDateTimezone,dateString);
                if(!StringUtils.isEmpty(dateTimezone)){
                    dateString = dateString.replaceAll(patternDateTimezone,"");
                }else{
                    dateTimezone=defaultTimezone;
                }

                // 去掉毫秒
                dateString = dateString.replaceAll("(?<=\\d{2})(\\.\\d*$)","");

                // 年月日
                String[] arr = dateString.split("[ ]");
                dateStringFront = arr[0];
                dateStringFront = dateStringFront.replaceAll("\\/|\\.","-");
                String[] dateStringFrontArray = dateStringFront.split("[-]");

                // 年
                String year = dateStringFrontArray[0];
                if(year.length() == 3){
                    year = (Integer.parseInt(year) + 1911) + "";
                }

                // 月
                String month = dateStringFrontArray[1];
                if(month.length() == 1){
                    month = "0" + month;
                }

                // 日
                String day = dateStringFrontArray[2];
                if(day.length() == 1){
                    day = "0" + day;
                }

                dateStringFront = year + month + day;

                // 时分秒
                if(null != arr && arr.length == 2){
                    dateStringBack = dateString.split("[ ]")[1];
                }else{
                    dateStringBack = "";
                }

                // 重新组合
                dateString = dateStringFront + dateStringBack;

                // 去掉多余的符号
                dateString = dateString.replaceAll("[　]|[ ]|[\\/]|[:]|[-]|[+]|[.]","");

                // 补0
                int length = dateString.length();
                if(length < 14){
                    for(int i = 0;i < 14 - length;i++){
                        dateString += "0";
                    }
                }
            }

            date = stringToDate(dateString+dateTimezone,pattern,locale);
        }
        return date;
    }

    public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            int num = matcher.groupCount();
            result = matcher.group();
        }
        return result;
    }

    public static void main(String[] args) {
        test2();
//        String dateString = "";
//        //dateString = "[2017-09-20 20:09.4434-08:00]";
//        //dateString = "June 27, 2022";
//        dateString = "中華民國111年07月15日";
//        Date date= null;
//        try {
//            date = stringToDate(dateString);
//            System.out.println(DateUtil.dateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    public static void test() {
        List<String> list = FileUtil.readFileToList("D:\\workspace-company\\mimu-doc\\doc\\date-data-input.txt","utf-8");
        list.forEach(item->{
            try {
//                FileUtil.writeFile("D:\\data\\doc\\date-data-output.txt",
//                        item + "|" + DateUtil.dateToString(stringToDate(item), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())
//                        + "\r\n",
//                        true);
                FileUtil.writeFile("D:\\workspace-company\\mimu-doc\\doc\\date-data-output.txt",
                        DateUtil.dateToString(stringToDate(item), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())
                                + "\r\n",
                        true);
            } catch (ParseException e) {
                System.out.println("========================================"+item);
                e.printStackTrace();
            }
        });
    }

    public static void test2() {
        List<String> list = FileUtil.readFileToList("D:\\workspace-company\\mimu-doc\\doc\\date-data-input.txt","utf-8");
        list.forEach(item->{
            try {
                FileUtil.writeFile("D:\\workspace-company\\mimu-doc\\doc\\date-data-output.txt",
                        item + "|" + DateUtil.dateToString(stringToDate(item), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())
                        + "\r\n",
                        true);
            } catch (ParseException e) {
                System.out.println("========================================"+item);
                e.printStackTrace();
            }
        });
    }
}